package com.techelevator.dao;

import com.techelevator.dao.CommentDao;
import com.techelevator.model.Comment;
import com.techelevator.model.CommentNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcCommentDao implements CommentDao {

    private final JdbcTemplate jdbcTemplate;
    private final PostDao postDao;

    public JdbcCommentDao(JdbcTemplate jdbcTemplate, PostDao postDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.postDao = postDao;
    }

    public Comment getCommentByCommentId(int commentId) {

        String sql = "SELECT comment_id, comment, post_id, author_id " +
                "FROM comments" +
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

                return getCommentByCommentId(commentId);
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
