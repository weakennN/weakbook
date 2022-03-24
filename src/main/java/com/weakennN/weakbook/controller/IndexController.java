package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @GetMapping({"", "/", "/index"})
    public String getIndexView(Model model) {
        model.addAttribute("user", AuthService.getUserView());
        return "index";
    }
}
