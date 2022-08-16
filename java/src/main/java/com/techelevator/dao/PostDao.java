package com.techelevator.dao;
import com.techelevator.model.Post;

import java.util.List;

public interface PostDao {
    Post getPostById(int postId);

    Post createPost(int user_id, String s3_link, String description);

    boolean likePost(int userId, int postId);

    List<Post> getAllPostsByUserId(int userId);

    List<Post> getAllFolloweePostsByFollowerId(int followerId);

    public boolean deletePostCollection(int[] postIds);

    public boolean userLikedPost(int userId, int postId);

    public int likesCount(int postId);

    public List<Post> findAllPosts();

    public boolean favoritePost(int userId, int postId);

    public List<Post> listAllFavorites(int userId);
}
