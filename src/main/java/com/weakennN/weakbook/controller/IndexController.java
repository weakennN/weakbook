package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.binding.PostBinding;
import com.weakennN.weakbook.service.PostService;
import org.hibernate.EntityMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    private PostService postService;

    public IndexController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping({"", "/", "/index"})
    public String getIndexView() {
        return "index";
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public PostBinding test() {
        return new PostBinding();
    }

    // can be moved to PostController class
    @PostMapping(value = "/savePost", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<PostBinding> savePost(@RequestBody PostBinding post) {
        this.postService.savePost(post);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
