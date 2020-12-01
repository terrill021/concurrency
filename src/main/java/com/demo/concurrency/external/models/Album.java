package com.demo.concurrency.external.models;

import java.util.List;

public class Album {

    private String userId;
    private String id;
    private String tittle;
    private List<Photo> photos;

    public Album() {
    }

    public Album(String userId, String id, String tittle, List<Photo> photos) {
        this.userId = userId;
        this.id = id;
        this.tittle = tittle;
        this.photos = photos;
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getTittle() {
        return tittle;
    }

    public List<Photo> getPhotos() {
        return photos;
    }
}
