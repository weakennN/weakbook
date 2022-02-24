package com.weakennN.weakbook.service;

import com.weakennN.weakbook.security.ApplicationUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthService {

    public static ApplicationUser getCurrentUser() {
        return (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
