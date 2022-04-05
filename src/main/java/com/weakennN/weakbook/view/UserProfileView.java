package com.weakennN.weakbook.view;

public class UserProfileView extends UserView{

    private String bannerPicture;
    private boolean isOwner;

    public String getBannerPicture() {
        return bannerPicture;
    }

    public void setBannerPicture(String bannerPicture) {
        this.bannerPicture = bannerPicture;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }
}
