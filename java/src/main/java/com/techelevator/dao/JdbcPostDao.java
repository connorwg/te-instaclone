package com.techelevator.dao;

import com.techelevator.model.Post;
import com.techelevator.model.PostNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

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

    public Post createPost(Post post)  throws PostNotFoundException {
        userDao.getUserById(post.getUser_id());

        String sql = "" +
                "INSERT INTO posts (user_id, s3_link, description, time) " +
                "VALUES (?, ?, ?, ?) " +
                "RETURNING post_id;";

        Integer postId = jdbcTemplate.queryForObject(sql, Integer.class,
                post.getUser_id(),
                post.getPictureLink(),
                post.getDescription(),
                post.getTimestamp());

                return getPostById(postId);
    };
//    public List<Post> listAllPosts() {
//
//        String sql = "SELECT * FROM posts " +
//                "INNER JOIN comments USING (post_id) " +
//                "";
//
//
//    }
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
