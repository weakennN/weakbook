package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.binding.CommentBinding;
import com.weakennN.weakbook.service.CommentService;
import com.weakennN.weakbook.view.CommentView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CommentView> comment(@RequestBody CommentBinding commentBinding) {
        return new ResponseEntity<>(this.commentService.comment(commentBinding), HttpStatus.OK);
    }
}
