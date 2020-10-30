package com.demo.concurrency.external.models;

public class User {

    private final int id;
    private final String name;
    private final String username;
    private final String email;
    private final String address;

    public User(int id, String name, String username, String email, String address) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
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

    public String getAddress() {
        return address;
    }
}
