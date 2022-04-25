package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.binding.PostBinding;
import com.weakennN.weakbook.service.AuthService;
import com.weakennN.weakbook.service.PostService;
import com.weakennN.weakbook.view.PostLikeView;
import com.weakennN.weakbook.view.PostView;
import com.weakennN.weakbook.view.UserView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public String getPostView(Model model) {
        model.addAttribute("user", AuthService.getUserView());
        return "post";
    }

    @PostMapping(value = "/post/{postId}/like")
    @ResponseBody
    public ResponseEntity<PostLikeView> like(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(this.postService.like(postId), HttpStatus.OK);
    }

    @GetMapping(value = "/getPosts/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<PostView>> getOwnPosts(@PathVariable String userId, @RequestParam("passedPosts") int passedPosts) {
        return new ResponseEntity<>(this.postService.getUserPosts(Long.parseLong(userId), passedPosts), HttpStatus.OK);
    }

    @GetMapping(value = "/post/{postId}/likes")
    @ResponseBody
    public ResponseEntity<List<UserView>> getPostLikes(@PathVariable String postId, @RequestParam("passedLikes") int passedLikes) {
        return new ResponseEntity<>(this.postService.getPostLikes(Long.parseLong(postId), passedLikes), HttpStatus.OK);
    }
}
