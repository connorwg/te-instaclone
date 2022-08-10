package com.techelevator.dao;
import com.techelevator.model.Post;

public interface PostDao {
    Post getPostById(int postId);

    Post createPost(int user_id, String s3_link, String description);

    int likePost(int userId, int postId);
}
