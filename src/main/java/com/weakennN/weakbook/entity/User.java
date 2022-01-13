package com.weakennN.weakbook.entity;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
    @Column(name = "profile_picture")
    private String profilePicture = "";
    @Column(name = "banner_picture")
    private String bannerPicture = "";

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
