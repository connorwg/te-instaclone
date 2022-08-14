package com.techelevator.dao;

import com.techelevator.exceptions.CommentNotFoundException;
import com.techelevator.exceptions.PostNotFoundException;
import com.techelevator.model.Comment;
import com.techelevator.model.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JdbcCommentDao implements CommentDao{

    private final JdbcPostDao jdbcPostDao;


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

    public List<Comment> getCommentsByPostId(int postId) {

        List<Comment> comments = new ArrayList<>();
        List<Integer> postIds = jdbcPostDao.findAllPosts().stream().map(Post::getPost_id).collect(Collectors.toList());

        String sql = "SELECT comment_id, comment, post_id, author_id " +
                "FROM comments " +
                "WHERE post_id = ?;";

        if (!postIds.contains(postId)) throw new PostNotFoundException();
        SqlRowSet returned = jdbcTemplate.queryForRowSet(sql, postId);

        while (returned.next()) {
            comments.add(mapRowToComment(returned));
        }
        if (comments.size() < 1) {
            throw new CommentNotFoundException();
        }
        return comments;
    }

    public int createComment(String comment,  int postId, int author_id)  {


        List<Integer> postIds = jdbcPostDao.findAllPosts().stream().map(Post::getPost_id).collect(Collectors.toList());

        String sql = "" +
                "INSERT INTO comments (comment, post_id, author_id) " +
                "VALUES (?, ?, ?) " +
                "RETURNING comment_id;";

        Integer commentId = jdbcTemplate.queryForObject(sql, Integer.class,
                comment,
                postId,
                author_id);

        if (!postIds.contains(postId)) {
            throw new PostNotFoundException();
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
