package com.mi7.accounts_keeper.entity;

// Учетная запись

public class DataRecord {
    private long id;
    private String name;
    private String username;
    private String password;
    private String comment;
    private String www;
    
    public DataRecord() {
    }

    public DataRecord(Long id, String name, String username, String password, String comment, String www) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.comment = comment;
        this.www = www;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getComment() {
        return comment;
    }

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    

    @Override
    public String toString() {
        return "DataRecord{" + "id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", comment=" + comment + '}';
    }
    
}
