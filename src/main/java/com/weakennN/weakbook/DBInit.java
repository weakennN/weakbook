package com.weakennN.weakbook;

import com.weakennN.weakbook.repository.PostRepository;
import com.weakennN.weakbook.service.CommentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {

    private CommentService commentService;
    private PostRepository postRepository;

    public DBInit(CommentService commentService, PostRepository postRepository) {
        this.commentService = commentService;
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... args) {
      //  System.out.println(this.commentService.getCountComments(this.postRepository.findAllByContent("content123").get(0)));
    }
}
