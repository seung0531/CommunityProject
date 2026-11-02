package com.community.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.community.model.Comment;
import com.community.util.DBConnection;

public class CommentDAO {

    // 댓글 작성
    public boolean write(Comment comment) {

        String sql = "INSERT INTO comments (post_id, user_id, content) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, comment.getPostId());
            pstmt.setInt(2, comment.getUserId());
            pstmt.setString(3, comment.getContent());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 특정 게시글의 댓글 목록 조회
    public List<Comment> findByPostId(int postId) {

        List<Comment> commentList = new ArrayList<>();

        String sql =
            "SELECT c.*, u.username " +
            "FROM comments c " +
            "JOIN users u ON c.user_id = u.id " +
            "WHERE c.post_id = ? " +
            "ORDER BY c.id ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    Comment comment = new Comment();

                    comment.setId(rs.getInt("id"));
                    comment.setPostId(rs.getInt("post_id"));
                    comment.setUserId(rs.getInt("user_id"));
                    comment.setUsername(rs.getString("username"));
                    comment.setContent(rs.getString("content"));
                    comment.setCreatedAt(rs.getTimestamp("created_at"));

                    commentList.add(comment);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return commentList;
    }

    // 댓글 삭제 (작성자 본인만 가능)
    public boolean delete(int id, int userId) {

        String sql =
            "DELETE FROM comments WHERE id = ? AND user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
