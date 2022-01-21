package com.weakennN.weakbook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PostPicture extends BaseEntity {

    @ManyToOne
    private Post post;
    @Column(name = "path")
    private String path;

    public PostPicture() {

    }

    public PostPicture(Post post, String path) {
        this.post = post;
        this.path = path;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
