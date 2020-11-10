package com.demo.concurrency.external.models;

import java.util.List;

/**
 * Inmmutable.
 */
public class UserStory {

    private User user;
    private List<Post> posts;
    private List<Album> albums;

    public UserStory() {
    }

    public UserStory(User user, List<Post> posts, List<Album> albums) {
        this.user = user;
        this.posts = posts;
        this.albums = albums;
    }

    public User getUser() {
        return user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Album> getAlbums() {
        return albums;
    }
}
