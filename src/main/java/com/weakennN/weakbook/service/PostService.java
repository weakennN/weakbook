package com.weakennN.weakbook.service;

import com.weakennN.weakbook.binding.PostBinding;
import com.weakennN.weakbook.entity.Post;
import com.weakennN.weakbook.entity.PostPicture;
import com.weakennN.weakbook.entity.User;
import com.weakennN.weakbook.repository.*;
import com.weakennN.weakbook.security.ApplicationUser;
import com.weakennN.weakbook.view.PostView;
import com.weakennN.weakbook.view.UserView;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private DropBoxService dropBoxService;
    private ImageService imageService;
    private CommentRepository commentRepository;
    private PostLikeRepository postLikeRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository
            , DropBoxService dropBoxService, ImageService imageService
            , CommentRepository commentRepository, PostLikeRepository postLikeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.dropBoxService = dropBoxService;
        this.imageService = imageService;
        this.commentRepository = commentRepository;
        this.postLikeRepository = postLikeRepository;
    }

    public PostView savePost(PostBinding postBinding) {
        ModelMapper mapper = new ModelMapper();
        Post post = mapper.map(postBinding, Post.class);
        ApplicationUser applicationUser = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(applicationUser.getEmail()).get();
        post.setUser(user);
        this.addPicturesToPost(postBinding, post);
        this.postRepository.save(post);

        return this.mapPost(post, user);
    }

    // TODO: improve the mapping
    private PostView mapPost(Post post, User user) {
        ModelMapper mapper = new ModelMapper();
        PostView postView = new PostView();
        postView
                .setId(post.getId())
                .setContent(post.getContent())
                .setNumberComments(this.commentRepository.countCommentByPost(post))
                .setNumberLikes(this.postLikeRepository.countPostLikeByPost(post))
                .setUser(mapper.map(user, UserView.class));
        postView.getUser().setProfilePictureUrl(user.getProfilePicture());
        for (PostPicture postPicture : post.getPictures()) {
            // System.out.println(this.dropBoxService.getImageUrl(postPicture.getPath()));
            postView.addImageUrl(this.dropBoxService.getImageUrl(postPicture.getPath()));
        }

        return postView;
    }

    private void addPicturesToPost(PostBinding postBinding, Post post) {
        for (int i = 0; i < postBinding.getBase64Images().size(); i++) {
            String path = this.imageService.generateRandomUrl(30);
            this.dropBoxService.upload(path, Base64.getDecoder().decode(postBinding.getBase64Images().get(i)));
            PostPicture postPicture = new PostPicture(post, path);
            post.addPicture(postPicture);
        }
    }

    // TODO: maybe cache views
    public List<PostView> getPosts() {
        ApplicationUser applicationUser = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(applicationUser.getEmail()).get();
        List<Post> posts = this.postRepository.findAllByUser(user.getId());
        List<PostView> result = new ArrayList<>();

        for (Post post : posts) {
            result.add(this.mapPost(post, this.userRepository.findById(post.getUser().getId()).get()));
        }

        return result;
    }
}
