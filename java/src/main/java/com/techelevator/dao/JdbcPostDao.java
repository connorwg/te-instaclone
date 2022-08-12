package com.techelevator.dao;

import com.techelevator.exceptions.PostNotFoundException;
import com.techelevator.exceptions.UserNotFoundException;
import com.techelevator.model.Post;
import com.techelevator.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JdbcPostDao implements PostDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;

    public JdbcPostDao(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    public Post getPostById(int postId) {

        String sql = "SELECT * FROM posts WHERE post_id = ?;";
        SqlRowSet returned = jdbcTemplate.queryForRowSet(sql, postId);

        if(returned.next()) {
            return mapRowToPost(returned);
        } else {
            throw new PostNotFoundException();
        }
    }

    //getAllPostsByUserId
    public List<Post> getAllPostsByUserId(int userId) {
        List<Post> posts = new ArrayList<>();
        List<Integer> userIds = userDao.findAll().stream().map(User::getId).collect(Collectors.toList());
        if (!userIds.contains(userId)) {
            throw new UserNotFoundException();
        }

        String sql = "" +
                "SELECT post_id, user_id, s3_link, description, time " +
                "FROM  posts " +
                "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        while (results.next()) {
            posts.add(mapRowToPost(results));
        }
        if (posts.size() < 1) {
            throw new PostNotFoundException();
        }
        return posts;
    }



    public Post createPost(int user_id, String s3_link, String description) {

        String sql = "" +
                "INSERT INTO posts (user_id, s3_link, description, time) " +
                "VALUES (?, ?, ?, NOW()) " +
                "RETURNING post_id";

        int newPostId = jdbcTemplate.queryForObject(sql, int.class, user_id, s3_link, description);
        return getPostById(newPostId);

    }

    public boolean likePost(int userId, int postId) {

        String likePost = "INSERT INTO likes (user_id, post_id) VALUES (?,?);";
        String unlikePost = "DELETE FROM likes WHERE (user_id = ? AND post_id = ?);";

        List<Integer> postIds = findAllPosts().stream().map(Post::getPost_id).collect(Collectors.toList());

        if (!postIds.contains(postId)) {
            throw new PostNotFoundException();
        }
        if (userLikedPost(userId, postId)) {
            return jdbcTemplate.update(unlikePost, userId, postId) == 0;
        }

        return jdbcTemplate.update(likePost, userId, postId) == 1;
    }

    public int likesCount(int postId) {
        String likesCount = "SELECT COUNT(DISTINCT likes.user_id) FROM likes WHERE post_id = ?;";
        return jdbcTemplate.queryForObject(likesCount, int.class, postId);
    }

    public boolean userLikedPost(int userId, int postId) {

        String checkIfUserAlreadyLikes =
                "SELECT ? IN" +
                        " ( " +
                        "SELECT likes.user_id FROM POSTS " +
                        "INNER JOIN likes USING (post_id) " +
                        "WHERE likes.post_id = ?" +
                        " ) " +
                        "as user_liked_post;";

        return jdbcTemplate.queryForObject(checkIfUserAlreadyLikes,
                boolean.class, userId, postId);
    }


    public List<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT post_id, user_id, s3_link, description, time " +
                "FROM posts;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            posts.add(mapRowToPost(results));
        }
        return posts;
    }
    private Post mapRowToPost(SqlRowSet rowSet){
        Post post = new Post();

        post.setPost_id(rowSet.getInt("post_id"));
        post.setUser_id(rowSet.getInt("user_id"));
        post.setPictureLink(rowSet.getString("s3_link"));
        post.setDescription(rowSet.getString("description"));
        post.setTimestamp(rowSet.getDate("time"));

        return post;
    }







}
