package com.weakennN.weakbook.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"", "/", "/index"})
    public String getIndexView() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getClass().getSimpleName());
        return "index";
    }
}
