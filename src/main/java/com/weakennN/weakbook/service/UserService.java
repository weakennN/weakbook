package com.weakennN.weakbook.service;

import com.weakennN.weakbook.binding.UserRegisterBinding;
import com.weakennN.weakbook.entity.User;
import com.weakennN.weakbook.repository.UserRepository;
import com.weakennN.weakbook.security.ApplicationUser;
import com.weakennN.weakbook.security.ApplicationUserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserRegisterBinding userRegisterBinding) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userRegisterBinding, User.class);
        this.userRepository.save(user);
        ApplicationUser applicationUser = ApplicationUserService.map(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(applicationUser, applicationUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
