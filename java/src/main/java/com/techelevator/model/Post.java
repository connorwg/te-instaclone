package com.techelevator.model;

import java.sql.Date;

public class Post {

    private int post_id;
    private int user_id;
    private String pictureLink;
    private String description;
    private Date timestamp;

    public Post(int post_id, int user_id, String pictureLink, String description, Date timestamp) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.pictureLink = pictureLink;
        this.description = description;
        this.timestamp = timestamp;
    }
    public Post(){}

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
