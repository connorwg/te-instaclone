package com.techelevator.dao;

import com.techelevator.exceptions.PostNotFoundException;
import com.techelevator.model.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    //separate for getting comments based on post id

    public Post createPost(int user_id, String s3_link, String description) {

        String sql = "" +
                "INSERT INTO posts (user_id, s3_link, description, time) " +
                "VALUES (?, ?, ?, NOW()) " +
                "RETURNING post_id";

        int newPostId = jdbcTemplate.queryForObject(sql, int.class, user_id, s3_link, description);
        return getPostById(newPostId);

    }

    ;

    public int likePost(int userId, int postId) {

        String likePost = "INSERT INTO likes (user_id, post_id) VALUES (?,?);";

        String checkIfUserAlreadyLikes =
                "SELECT ? IN" +
                        " ( " +
                        "SELECT likes.user_id FROM POSTS " +
                        "INNER JOIN likes USING (post_id) " +
                        "WHERE likes.post_id = 1" +
                        " ) " +
                        "as user_liked_post;";

        boolean alreadyLiked = Boolean.TRUE.equals(jdbcTemplate.queryForObject(checkIfUserAlreadyLikes, boolean.class, userId, postId));

        if (alreadyLiked) {
            return 1;
        }
        return 1;
    }


    //grabs current users following pictures, sort by timestamp

//    public List<Post> listAllPosts() {
//
//        String sql = "SELECT * FROM posts " +
//                "INNER JOIN comments USING (post_id) " +
//                "";
//
//
//    }

    public List<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT post_id, user_id, s3_link, description, time " +
                "FROM posts;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Post post = mapRowToPost(results);
            posts.add(post);
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
