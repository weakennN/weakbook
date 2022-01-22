package com.weakennN.weakbook.view;

import java.util.ArrayList;
import java.util.List;

public class PostView {

    private Long id;
    private UserView user;
    private String content;
    private List<String> imagesUrls = new ArrayList<>();
    private int numberLikes;
    private int numberComments;

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
}
