package com.techelevator.dao;
import com.techelevator.model.Post;

public interface PostDao {
    Post getPostById(int postId);

    boolean createPost(int user_id, String s3_link, String description);
}
