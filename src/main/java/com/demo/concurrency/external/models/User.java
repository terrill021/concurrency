package com.demo.concurrency.external.models;

public class User {

    private int id;
    private String name;
    private String username;
    private String email;

    public User() {
    }

    public User(int id, String name, String username, String email, String address) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }


}
