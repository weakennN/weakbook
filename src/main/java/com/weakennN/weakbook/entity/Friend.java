package com.weakennN.weakbook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friends")
public class Friend extends BaseEntity {

    @Column(name = "is_blocked")
    private boolean isBlocked = false;
    @ManyToOne
    private User owner;
    @ManyToOne
    private User user;

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public User getOwner() {
        return this.owner;
    }

    public void setFirstUser(User owner) {
        this.owner = owner;
    }

    public User getUser() {
        return this.user;
    }

    public void setSecondUser(User user) {
        this.user = user;
    }
}
