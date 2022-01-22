package com.weakennN.weakbook.view;

import java.util.ArrayList;
import java.util.List;

public class PostView {

    private String content;
    private List<String> imagesUrls = new ArrayList<>();

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    public void addImageUrl(String url) {
        this.imagesUrls.add(url);
    }
}
