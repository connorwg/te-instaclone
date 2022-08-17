package com.techelevator.model;

import org.jetbrains.annotations.NotNull;

public class Comment {


    private int comment_id;
    private String comment;
    @NotNull
    private int post_id;
    private int author_id;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Comment(int comment_id, String comment, int post_id, int author_id,String username) {
        this.comment_id = comment_id;
        this.comment = comment;
        this.post_id = post_id;
        this.author_id = author_id;
        this.username = username;
    }

    public Comment() {

    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }
}
