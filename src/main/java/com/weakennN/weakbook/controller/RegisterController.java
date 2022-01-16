package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.binding.UserRegisterBinding;
import com.weakennN.weakbook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", ""})
    public String getRegisterView(Model model) {
        if (!model.containsAttribute("userRegisterBinding")) {
            model.addAttribute("userRegisterBinding", new UserRegisterBinding());
        }

        return "register";
    }

    @PostMapping("/registerUser")
    public String registerUser(@Valid UserRegisterBinding userRegisterBinding
            , BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBinding", bindingResult);
            redirectAttributes.addFlashAttribute("userRegisterBinding", userRegisterBinding);
            return "redirect:/register/";
        }

        this.userService.registerUser(userRegisterBinding);

        return "redirect:/index";
    }
}
