package com.weakennN.weakbook.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    @Column(name = "content")
    private String content;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLike> likes = new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PostPicture> pictures = new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addLike(PostLike like) {
        this.likes.add(like);
    }

    public void addPicture(PostPicture postPicture) {
        this.pictures.add(postPicture);
    }

    public List<PostPicture> getPictures() {
        return this.pictures;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
