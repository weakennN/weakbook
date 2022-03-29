package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.binding.CommentBinding;
import com.weakennN.weakbook.service.CommentService;
import com.weakennN.weakbook.view.CommentView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RequestMapping("/comments")
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

    @GetMapping(value = "/getReplies/{comment_id}")
    @ResponseBody
    public ResponseEntity<List<CommentView>> getReplies(@PathVariable("comment_id") Long commentId
            , @RequestParam("offset") int offset) {
        return new ResponseEntity<>(this.commentService.getReplies(commentId, offset), HttpStatus.OK);
    }

    @GetMapping(value = "/getPostComments/{post_id}")
    @ResponseBody
    public ResponseEntity<List<CommentView>> getPostComments(@PathVariable("post_id") Long postId
            , @RequestParam("offset") int offset) {
        return new ResponseEntity<>(this.commentService.getPostComments(postId, offset), HttpStatus.OK);
    }

    @PostMapping(value = "/like", consumes = MediaType.TEXT_HTML_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ResponseEntity<String> like(String commentId) {
        this.commentService.like(Long.parseLong(commentId));
        return new ResponseEntity<>("temp return", HttpStatus.OK);
    }
}
