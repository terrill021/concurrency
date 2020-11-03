package com.demo.concurrency.external.models;

import java.util.List;

public class UserStory {

    private User user;
    private List<Post> posts;

    public UserStory() {
    }

    public UserStory(User user, List<Post> posts) {
        this.user = user;
        this.posts = posts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
