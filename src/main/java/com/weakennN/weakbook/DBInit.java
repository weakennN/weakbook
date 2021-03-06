package com.weakennN.weakbook;

import com.weakennN.weakbook.repository.ChatParticipantRepository;
import com.weakennN.weakbook.repository.PostRepository;
import com.weakennN.weakbook.service.CommentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {

    private CommentService commentService;
    private PostRepository postRepository;
    private ChatParticipantRepository chatParticipantRepository;

    public DBInit(CommentService commentService, PostRepository postRepository
            , ChatParticipantRepository chatParticipantRepository) {
        this.commentService = commentService;
        this.postRepository = postRepository;
        this.chatParticipantRepository = chatParticipantRepository;
    }

    @Override
    public void run(String... args) {
       /* List<PostManager> posts = this.postRepository.findAllByUser(5L,5);
        System.out.println();
        System.out.println(this.commentService.getCountComments(this.postRepository.findAllByContent("content123").get(0)));
        */
    }
}
