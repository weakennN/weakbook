package com.weakennN.weakbook.service;

import com.weakennN.weakbook.binding.UserRegisterBinding;
import com.weakennN.weakbook.entity.User;
import com.weakennN.weakbook.repository.UserRepository;
import com.weakennN.weakbook.security.ApplicationUser;
import com.weakennN.weakbook.security.ApplicationUserService;
import com.weakennN.weakbook.utils.ViewMapper;
import com.weakennN.weakbook.view.UserView;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private DropBoxService dropBoxService;

    public UserService(UserRepository userRepository, DropBoxService dropBoxService) {
        this.userRepository = userRepository;
        this.dropBoxService = dropBoxService;
    }

    public void registerUser(UserRegisterBinding userRegisterBinding) {
        ModelMapper modelMapper = new ModelMapper();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = modelMapper.map(userRegisterBinding, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterBinding.getPassword()));
        user = this.userRepository.save(user);
        ApplicationUser applicationUser = ApplicationUserService.map(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(applicationUser, applicationUser.getAuthorities(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        this.dropBoxService.createFolder("/users/user-" + user.getId());
        this.dropBoxService.createFolder("/users/user-" + user.getId() + "/images");
    }

    public User getUser(Long userId) {
        return this.userRepository.findById(userId).get();
    }

    public List<UserView> getUserLike(String prefix, int limit) {
        if (prefix.length() == 0)
            return new ArrayList<>();
        prefix = "%" + prefix + "%";
        return this.userRepository.getUserBySearch(prefix, limit, AuthService.getUser().getId())
                .stream().map(ViewMapper::mapUser).collect(Collectors.toList());
    }
}
