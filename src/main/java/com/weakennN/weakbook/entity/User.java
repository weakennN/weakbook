package com.weakennN.weakbook.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NonNull
    @Column(name = "first_name", length = 40)
    private String firstName;
    @NonNull
    @Column(name = "last_name", length = 40)
    private String lastName;
    @NonNull
    @Column(name = "email", unique = true, length = 128)
    private String email;
    @NonNull
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Friend> friends = new ArrayList<>();
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<FriendRequest> friendRequests = new ArrayList<>();
    @Column(name = "profile_picture")
    private String profilePicture = "/users/default/default_profile.jpg";
    @Column(name = "banner_picture")
    private String bannerPicture = "/users/default/default_banner.jpg";

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBannerPicture() {
        return bannerPicture;
    }

    public void setBannerPicture(String bannerPicture) {
        this.bannerPicture = bannerPicture;
    }
}
