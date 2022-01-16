package com.weakennN.weakbook.config;

import com.weakennN.weakbook.security.ApplicationUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private ApplicationUserService applicationUserService;
    private PasswordEncoder passwordEncoder;

    public SecurityConfiguration(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/register", "/login").anonymous()
                .antMatchers("/").authenticated()
                .and()
                .rememberMe().alwaysRemember(true)
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/login/loginUser")
                .loginPage("/login")
                .defaultSuccessUrl("/", true);
        // TODO logout
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.applicationUserService)
                .passwordEncoder(this.passwordEncoder);

    }
}
