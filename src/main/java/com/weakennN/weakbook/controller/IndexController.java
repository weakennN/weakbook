package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.binding.PostBinding;
import com.weakennN.weakbook.service.PostService;
import com.weakennN.weakbook.view.PostView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping(value = "/savePost", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PostView> savePost(@RequestBody PostBinding postBinding) {
        return new ResponseEntity<>(this.postService.savePost(postBinding), HttpStatus.OK);
    }

    @PostMapping("/saveFile")
    public ResponseEntity<?> saveFile(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getSize());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
