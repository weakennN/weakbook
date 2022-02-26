package com.weakennN.weakbook.utils.hypermedia;

import java.util.HashMap;

public class RepresentationModel {

    private HashMap<String, Link> links = new HashMap<>();

    public void addLink(String key, Link link) {
        this.links.put(key, link);
    }

    public Link removeLink(String key) {
        return this.links.remove(key);
    }

    public HashMap<String, Link> getLinks() {
        return links;
    }

    public void setLinks(HashMap<String, Link> links) {
        this.links = links;
    }
}
