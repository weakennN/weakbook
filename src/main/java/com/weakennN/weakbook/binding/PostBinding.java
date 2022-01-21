package com.weakennN.weakbook.binding;

import java.util.List;

public class PostBinding {

    private String content;
    private List<String> base64Images;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getBase64Images() {
        return base64Images;
    }

    public void setBase64Images(List<String> base64Images) {
        this.base64Images = base64Images;
    }
}
