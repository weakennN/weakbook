package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.service.UserService;
import com.weakennN.weakbook.view.UserView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private UserService userService;

    public SearchController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserView>> search(@RequestParam("query") String query, @RequestParam("limit") int limit) {
        return new ResponseEntity<>(this.userService.getUserLike(query, limit), HttpStatus.OK);
    }
}
