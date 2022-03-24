package com.weakennN.weakbook.view;

import com.weakennN.weakbook.utils.hypermedia.Link;
import com.weakennN.weakbook.utils.hypermedia.RepresentationModel;

public class UserView extends RepresentationModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String profilePicture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public void initSelfLinks() {
        super.addLink("self", new Link("/user/" + this.id));
    }
}
