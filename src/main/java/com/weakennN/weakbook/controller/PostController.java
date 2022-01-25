package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.binding.PostBinding;
import com.weakennN.weakbook.service.PostService;
import com.weakennN.weakbook.view.PostView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/savePost", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PostView> savePost(@RequestBody PostBinding postBinding) {
        return new ResponseEntity<>(this.postService.savePost(postBinding), HttpStatus.OK);
    }

    @GetMapping(value = "/getPosts", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<PostView>> getPosts() {
        return new ResponseEntity<>(this.postService.getPosts(), HttpStatus.OK);
    }
}
