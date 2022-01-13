package com.weakennN.weakbook.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ApplicationUser extends User {

    private String firstName;
    private String lastName;
    private String email;

    public ApplicationUser(String firstName, String lastName, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
