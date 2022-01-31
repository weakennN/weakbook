package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.binding.PostBinding;
import com.weakennN.weakbook.service.PostService;
import com.weakennN.weakbook.view.PostView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<PostView>> getPosts(@RequestParam("passedPosts") int passedPosts) {
        return new ResponseEntity<>(this.postService.getPosts(passedPosts), HttpStatus.OK);
    }

    @GetMapping(value = "/post/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PostView> getPostView(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(this.postService.getPost(postId), HttpStatus.OK);
    }

    @GetMapping(value = "/post/{postId}")
    public String getPostView() {
        return "post";
    }
}
