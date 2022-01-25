package com.weakennN.weakbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @GetMapping({"", "/", "/index"})
    public String getIndexView() {
        return "index";
    }
}
