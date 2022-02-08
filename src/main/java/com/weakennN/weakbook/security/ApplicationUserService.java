package com.weakennN.weakbook.security;

import com.weakennN.weakbook.entity.User;
import com.weakennN.weakbook.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationUserService implements UserDetailsService {

    private UserRepository userRepository;

    public ApplicationUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User userEntity = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("The user doesnt exist!"));
        return map(userEntity);
    }

    public static ApplicationUser map(User user) {
        return new ApplicationUser(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        // TODO remove hardcoded authorities
    }
}
