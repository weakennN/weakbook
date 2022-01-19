package com.weakennN.weakbook.service;

import com.weakennN.weakbook.binding.PostBinding;
import com.weakennN.weakbook.entity.Post;
import com.weakennN.weakbook.repository.PostRepository;
import com.weakennN.weakbook.repository.UserRepository;
import com.weakennN.weakbook.security.ApplicationUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public void savePost(PostBinding postBinding) {
        ModelMapper mapper = new ModelMapper();
        Post post = mapper.map(postBinding, Post.class);
        ApplicationUser applicationUser = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(this.userRepository.findByEmail(applicationUser.getEmail()).get());
        this.postRepository.save(post);
    }
}
