package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", ""})
    public String getRegisterView(Model model) {
        return "register";
    }

    @PostMapping("/registerUser")
    public void registerUser() {
        this.userService.registerUser();
    }
}
