package com.techelevator.dao;

import com.techelevator.model.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcCommentDao {

    private final JdbcTemplate jdbcTemplate;
    private final PostDao postDao;

    public JdbcCommentDao(JdbcTemplate jdbcTemplate, PostDao postDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.postDao = postDao;
    }

    public Comment getCommentById(int commentId) {

        String sql = "SELECT comment_id, comment, post_id, author_id " +
                "FROM comments" +
                "WHERE comment_id = ?;";
        SqlRowSet returned = jdbcTemplate.queryForRowSet(sql, commentId);

        return mapRowToComment(returned);
//        if (returned.next()) {
//            return mapRowToComment(returned);
//        } else {
//            throw new CommentNotFoundException();
//        }

    }

    public Comment getCommentByPostId(int postId) {

        String sql = "SELECT comment_id, comment, post_id, author_id " +
                "FROM comments " +
                "WHERE post_id = ?;";
        SqlRowSet returned = jdbcTemplate.queryForRowSet(sql, postId);

        return mapRowToComment(returned);
    }

    public Comment createComment(Comment comment)  {
        postDao.getPostById(comment.getPost_id());

        String sql = "" +
                "INSERT INTO comments (comment, post_id, author_id) " +
                "VALUES (?, ?, ?) " +
                "RETURNING comment_id;";

        Integer commentId = jdbcTemplate.queryForObject(sql, Integer.class,
                comment.getComment(),
                comment.getPost_id(),
                comment.getAuthor_id());

                return getCommentById(commentId);

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
