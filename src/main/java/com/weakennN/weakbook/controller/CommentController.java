package com.weakennN.weakbook.controller;

import com.weakennN.weakbook.binding.CommentBinding;
import com.weakennN.weakbook.service.CommentService;
import com.weakennN.weakbook.view.CommentView;
import com.weakennN.weakbook.view.LikeView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller()
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CommentView> comment(@RequestBody CommentBinding commentBinding) {
        System.out.println(commentBinding.getComment());
        return new ResponseEntity<>(this.commentService.comment(commentBinding), HttpStatus.OK);
    }

    @GetMapping(value = "/replies/{comment_id}")
    @ResponseBody
    public ResponseEntity<List<CommentView>> getReplies(@PathVariable("comment_id") Long commentId
            , @RequestParam("offset") int offset) {
        return new ResponseEntity<>(this.commentService.getReplies(commentId, offset), HttpStatus.OK);
    }

    @GetMapping(value = "/{post_id}")
    @ResponseBody
    public ResponseEntity<List<CommentView>> getPostComments(@PathVariable("post_id") Long postId
            , @RequestParam("offset") int offset) {
        return new ResponseEntity<>(this.commentService.getPostComments(postId, offset), HttpStatus.OK);
    }

    @PostMapping(value = "/like/{commentId}", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<LikeView> like(@PathVariable("commentId") Long commentId) {
        return new ResponseEntity<>(this.commentService.like(commentId), HttpStatus.OK);
    }
}
