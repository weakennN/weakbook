package com.weakennN.weakbook.service;

import com.weakennN.weakbook.binding.PostBinding;
import com.weakennN.weakbook.entity.Post;
import com.weakennN.weakbook.entity.PostPicture;
import com.weakennN.weakbook.repository.PostPictureRepository;
import com.weakennN.weakbook.repository.PostRepository;
import com.weakennN.weakbook.repository.UserRepository;
import com.weakennN.weakbook.security.ApplicationUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private DropBoxService dropBoxService;
    private ImageService imageService;
    private PostPictureRepository postPictureRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository
            , DropBoxService dropBoxService, ImageService imageService, PostPictureRepository postPictureRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.dropBoxService = dropBoxService;
        this.imageService = imageService;
        this.postPictureRepository = postPictureRepository;
    }

    public void savePost(PostBinding postBinding) {
        ModelMapper mapper = new ModelMapper();
        Post post = mapper.map(postBinding, Post.class);
        ApplicationUser applicationUser = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(this.userRepository.findByEmail(applicationUser.getEmail()).get());
        this.postRepository.save(post);

        for (int i = 0; i < postBinding.getBase64Images().size(); i++) {
            String path = this.imageService.generateRandomUrl(30);
            this.dropBoxService.upload(path, Base64.getDecoder().decode(postBinding.getBase64Images().get(i)));
            this.postPictureRepository.save(new PostPicture(post, path));
        }
    }
}
