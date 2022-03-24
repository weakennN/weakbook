package com.weakennN.weakbook.service;

import com.weakennN.weakbook.repository.UserRepository;
import com.weakennN.weakbook.security.ApplicationUser;
import com.weakennN.weakbook.utils.ViewMapper;
import com.weakennN.weakbook.view.UserView;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        AuthService.userRepository = userRepository;
    }

    public static ApplicationUser getUser() {
        return (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static UserView getUserView() {
        return ViewMapper.mapUser(userRepository.findByEmail(getUser().getEmail()).get());
    }
}
