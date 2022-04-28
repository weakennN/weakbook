package com.weakennN.weakbook.view;

import com.weakennN.weakbook.utils.hypermedia.Link;
import com.weakennN.weakbook.utils.hypermedia.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

public class PostView extends RepresentationModel {

    private Long id;
    private UserView user;
    private String content;
    private List<CommentView> comments = new ArrayList<>();
    private List<String> imagesUrls = new ArrayList<>();
    private int numberLikes;
    private int numberComments;
    private boolean isLiked = false;

    public Long getId() {
        return id;
    }

    public PostView setId(Long id) {
        this.id = id;
        return this;
    }

    public UserView getUser() {
        return user;
    }

    public PostView setUser(UserView user) {
        this.user = user;
        return this;
    }

    public String getContent() {
        return content;
    }

    public PostView setContent(String content) {
        this.content = content;
        return this;
    }

    public List<String> getImagesUrls() {
        return imagesUrls;
    }

    public PostView setImagesUrls(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
        return this;
    }

    public void addImageUrl(String url) {
        this.imagesUrls.add(url);
    }

    public int getNumberLikes() {
        return numberLikes;
    }

    public PostView setNumberLikes(int numberLikes) {
        this.numberLikes = numberLikes;
        return this;
    }

    public int getNumberComments() {
        return numberComments;
    }

    public PostView setNumberComments(int numberComments) {
        this.numberComments = numberComments;
        return this;
    }

    public void addComment(CommentView commentView) {
        this.comments.add(commentView);
    }

    public List<CommentView> getComments() {
        return comments;
    }

    public void setComments(List<CommentView> comments) {
        this.comments = comments;
    }

    public PostView setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
        return this;
    }

    public boolean isLiked() {
        return this.isLiked;
    }

    @Override
    public void initLinks() {
        super.addLink("self", new Link("/posts/" + this.id));
        super.addLink("likes", new Link("/posts/" + this.id + "/likes"));
        super.addLink("like", new Link("/posts/" + this.id + "/like"));
        super.addLink("user", new Link("/user/" + this.user.getId()));
        super.addLink("comments", new Link("/comments/getPostComments/" + this.id));
        super.addLink("comment", new Link("/comments/"));
    }
}
