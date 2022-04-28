package com.weakennN.weakbook.service;

import com.weakennN.weakbook.binding.PostBinding;
import com.weakennN.weakbook.entity.Post;
import com.weakennN.weakbook.entity.PostLike;
import com.weakennN.weakbook.entity.PostPicture;
import com.weakennN.weakbook.entity.User;
import com.weakennN.weakbook.repository.*;
import com.weakennN.weakbook.security.ApplicationUser;
import com.weakennN.weakbook.utils.ViewMapper;
import com.weakennN.weakbook.view.PostLikeView;
import com.weakennN.weakbook.view.PostView;
import com.weakennN.weakbook.view.UserView;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private DropBoxService dropBoxService;
    private ImageService imageService;
    private CommentRepository commentRepository;
    private PostLikeRepository postLikeRepository;
    private NotificationService notificationService;

    public PostService(PostRepository postRepository, UserRepository userRepository
            , DropBoxService dropBoxService, ImageService imageService
            , CommentRepository commentRepository, PostLikeRepository postLikeRepository
            , NotificationService notificationService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.dropBoxService = dropBoxService;
        this.imageService = imageService;
        this.commentRepository = commentRepository;
        this.postLikeRepository = postLikeRepository;
        this.notificationService = notificationService;
    }

    public PostView savePost(PostBinding postBinding) {
        ModelMapper mapper = new ModelMapper();
        Post post = mapper.map(postBinding, Post.class);
        ApplicationUser applicationUser = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(applicationUser.getEmail()).get();
        post.setUser(user);
        this.addPicturesToPost(postBinding, post);
        this.postRepository.save(post);

        return ViewMapper.mapToPostView(post, this.commentRepository, this.postLikeRepository, user, this.dropBoxService);
    }

    private void addPicturesToPost(PostBinding postBinding, Post post) {
        for (int i = 0; i < postBinding.getBase64Images().size(); i++) {
            String path = "/users/user-" + AuthService.getUser().getId() + "/images" + this.imageService.generateRandomUrl(30);
            this.dropBoxService.upload(path, Base64.getDecoder().decode(postBinding.getBase64Images().get(i)));
            PostPicture postPicture = new PostPicture(post, path);
            post.addPicture(postPicture);
        }
    }

    public List<PostView> getPosts(int passedPosts, int limit) {
        User user = this.userRepository.findByEmail(AuthService.getUser().getEmail()).get();
        List<Post> posts = this.postRepository.findAllByUser(user.getId(), passedPosts, limit);
        List<PostView> result = new ArrayList<>();

        for (Post post : posts) {
            result.add(ViewMapper.mapToPostView(post, this.commentRepository, this.postLikeRepository,
                    this.userRepository.findById(post.getUser().getId()).get(), this.dropBoxService));
        }

        return result;
    }

    public PostLikeView like(Long postId) {
        Long userId = AuthService.getUser().getId();
        PostLike postLike = this.postLikeRepository.findByPostAndUser(postId, userId);
        if (postLike != null) {
            this.postLikeRepository.delete(postLike);
            return new PostLikeView(false);
        } else {
            PostLike newLike = new PostLike(this.postRepository.findById(postId).get()
                    , this.userRepository.findById(userId).get());
            this.postLikeRepository.save(newLike);
            if (!AuthService.getUser().getId().equals(this.postRepository.findById(postId).get().getUser().getId())) {
                this.notificationService.sendNotification(AuthService.getUser().getFirstName() + " " + AuthService.getUser().getLastName() + "liked your post."
                        , this.postRepository.findById(postId).get().getUser().getEmail(), "/post/" + postId);
            }
            return new PostLikeView(true);
        }
    }

    public PostView getPost(Long postId) {
        Post post = this.postRepository.findById(postId).get();
        return ViewMapper.mapToPostView(post, this.commentRepository,
                this.postLikeRepository, post.getUser(), this.dropBoxService);
    }

    public List<PostView> getUserPosts(Long userId, int passedPosts) {
        List<Post> posts = this.postRepository.getUserPosts(userId, passedPosts);
        List<PostView> result = new ArrayList<>();
        for (Post post : posts) {
            result.add(ViewMapper.mapToPostView(post, this.commentRepository, this.postLikeRepository,
                    this.userRepository.findById(AuthService.getUser().getId()).get(), this.dropBoxService));
        }
        return result;
    }

    public List<UserView> getPostLikes(Long postId, int passedLikes) {
        return this.postLikeRepository.getPostLikes(postId, passedLikes).stream().map(pl -> ViewMapper.mapUser(pl.getUser())).collect(Collectors.toList());
    }
}
