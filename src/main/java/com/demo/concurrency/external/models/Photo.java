package com.demo.concurrency.external.models;

/**
 * "albumId": 1, "id": 1, "title": "accusamus beatae ad facilis cum similique qui sunt", "url":
 * "https://via.placeholder.com/600/92c952", "thumbnailUrl":
 * "https://via.placeholder.com/150/92c952"
 */
public class Photo {

    private String id;
    private String albumId;
    private String url;
    private String thumbnailUrl;


    public String getId() {
        return id;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
