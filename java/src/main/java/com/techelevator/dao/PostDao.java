package com.techelevator.dao;
import com.techelevator.model.Post;

import java.util.List;

public interface PostDao {
    Post getPostById(int postId);

    Post createPost(int user_id, String s3_link, String description);

    int likePost(int userId, int postId);

    List<Post> getAllPostsByUserId(int userId);

    List<Post> getAllFolloweePostsByFollowerId(int followerId);

    public boolean deletePostCollection(int[] postIds);
}
