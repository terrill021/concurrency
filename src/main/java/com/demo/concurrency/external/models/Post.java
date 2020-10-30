package com.demo.concurrency.external.models;

import java.util.List;

public class Post {

    private String id;
    private String title;
    private String body;
    private List<Comment> comments;

    public Post(String id, String title, String body, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
