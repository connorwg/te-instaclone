package com.techelevator.dao;

import com.techelevator.exceptions.CommentNotFoundException;
import com.techelevator.model.Comment;
import com.techelevator.model.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
<<<<<<< HEAD

@Component
public class JdbcCommentDao implements CommentDao{
=======

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCommentDao implements CommentDao{

    private final JdbcPostDao jdbcPostDao;

>>>>>>> 43aa0b88ab378d3b2618e57bb7c385173d8646b3

    private final JdbcTemplate jdbcTemplate;
    private final PostDao postDao;

    public JdbcCommentDao(JdbcPostDao jdbcPostDao, JdbcTemplate jdbcTemplate, PostDao postDao) {
        this.jdbcPostDao = jdbcPostDao;
        this.jdbcTemplate = jdbcTemplate;
        this.postDao = postDao;
    }

    public Comment getCommentByCommentId(int commentId) {

        String sql = "SELECT comment_id, comment, post_id, author_id " +
                "FROM comments " +
                "WHERE comment_id = ?;";
        SqlRowSet returned = jdbcTemplate.queryForRowSet(sql, commentId);

        if (returned.next()) {
            return mapRowToComment(returned);
        } else {
            throw new CommentNotFoundException();
        }

    }

    public Comment getCommentsByPostId(int postId) {

        String sql = "SELECT comment_id, comment, post_id, author_id " +
                "FROM comments " +
                "WHERE post_id = ?;";
        SqlRowSet returned = jdbcTemplate.queryForRowSet(sql, postId);

        if (returned.next()) {
            return mapRowToComment(returned);
        } else {
            throw new CommentNotFoundException();
        }
    }

    public int createComment(String comment,  int postId, int author_id)  {
// check if post exists

        List<Post> posts = jdbcPostDao.findAllPosts();
        List<Integer> postIds  = new ArrayList<>();

        for(Post post : posts) {
            postIds.add(post.getPost_id());
        }

        String sql = "" +
                "INSERT INTO comments (comment, post_id, author_id) " +
                "VALUES (?, ?, ?) " +
                "RETURNING comment_id;";

        Integer commentId = jdbcTemplate.queryForObject(sql, Integer.class,
                comment,
                postId,
                author_id);

        if (!postIds.contains(postId)) {
            return -1;
        }

        return 1;

    }




    private Comment  mapRowToComment(SqlRowSet rowSet)  {
        Comment comment = new Comment();

        comment.setComment_id(rowSet.getInt("comment_id"));
        comment.setComment(rowSet.getString("comment"));
        comment.setPost_id(rowSet.getInt("post_id"));
        comment.setAuthor_id(rowSet.getInt("author_id"));

        return comment;
    }
}
