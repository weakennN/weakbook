package com.weakennN.weakbook.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column(name = "comment")
    private String comment;
    @OneToOne
    private User user;
    @ManyToOne
    private Post post;
    @JoinTable(
            name = "replies",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "reply_id"))
    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> replies = new ArrayList<>();

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void addReply(Comment comment) {
        this.replies.add(comment);
    }

    public List<Comment> getReplies() {
        return this.replies;
    }
}
