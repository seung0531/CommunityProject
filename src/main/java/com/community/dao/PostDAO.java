package com.community.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.community.model.Post;
import com.community.util.DBConnection;

public class PostDAO {

    // 게시글 작성
    public boolean write(Post post) {

        String sql = "INSERT INTO posts (user_id, title, content) VALUES (?, ?, ?)";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, post.getUserId());
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getContent());

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 게시글 전체 조회
    public List<Post> findAll() {

        List<Post> posts = new ArrayList<>();

        String sql =
            "SELECT p.*, u.username " +
            "FROM posts p " +
            "JOIN users u ON p.user_id = u.id " +
            "ORDER BY p.id DESC";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {

                Post post = new Post();

                post.setId(rs.getInt("id"));
                post.setUserId(rs.getInt("user_id"));
                post.setUsername(rs.getString("username"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                post.setUpdatedAt(rs.getTimestamp("updated_at"));

                posts.add(post);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }

    // 게시글 상세 조회
    public Post findById(int id) {

        String sql =
            "SELECT p.*, u.username " +
            "FROM posts p " +
            "JOIN users u ON p.user_id = u.id " +
            "WHERE p.id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    Post post = new Post();

                    post.setId(rs.getInt("id"));
                    post.setUserId(rs.getInt("user_id"));
                    post.setUsername(rs.getString("username"));
                    post.setTitle(rs.getString("title"));
                    post.setContent(rs.getString("content"));
                    post.setCreatedAt(rs.getTimestamp("created_at"));
                    post.setUpdatedAt(rs.getTimestamp("updated_at"));

                    return post;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    

	public boolean update(Post post) {
	
	    String sql =
	        "UPDATE posts SET title = ?, content = ? " +
	        "WHERE id = ? AND user_id = ?";
	
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	
	        pstmt.setString(1, post.getTitle());
	        pstmt.setString(2, post.getContent());
	        pstmt.setInt(3, post.getId());
	        pstmt.setInt(4, post.getUserId());
	
	        return pstmt.executeUpdate() > 0;
	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
	    return false;
	}
	

	public boolean delete(int id, int userId) {

		String sql = "DELETE FROM posts WHERE id = ? AND user_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);
			pstmt.setInt(2, userId);

			return pstmt.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

    // 게시글 검색
    public List<Post> search(String keyword) {

        List<Post> postList = new ArrayList<>();

        String sql =
            "SELECT p.*, u.username " +
            "FROM posts p " +
            "JOIN users u ON p.user_id = u.id " +
            "WHERE p.title LIKE ? OR p.content LIKE ? " +
            "ORDER BY p.id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";

            pstmt.setString(1, searchKeyword);
            pstmt.setString(2, searchKeyword);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    Post post = new Post();

                    post.setId(rs.getInt("id"));
                    post.setUserId(rs.getInt("user_id"));
                    post.setUsername(rs.getString("username"));
                    post.setTitle(rs.getString("title"));
                    post.setContent(rs.getString("content"));
                    post.setCreatedAt(rs.getTimestamp("created_at"));
                    post.setUpdatedAt(rs.getTimestamp("updated_at"));

                    postList.add(post);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return postList;
    }
    

    // 페이지별 게시글 조회
    public List<Post> findByPage(int offset, int pageSize) {

        List<Post> postList = new ArrayList<>();

        String sql =
            "SELECT p.*, u.username " +
            "FROM posts p " +
            "JOIN users u ON p.user_id = u.id " +
            "ORDER BY p.id DESC " +
            "LIMIT ? OFFSET ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pageSize);
            pstmt.setInt(2, offset);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    Post post = new Post();

                    post.setId(rs.getInt("id"));
                    post.setUserId(rs.getInt("user_id"));
                    post.setUsername(rs.getString("username"));
                    post.setTitle(rs.getString("title"));
                    post.setContent(rs.getString("content"));
                    post.setCreatedAt(rs.getTimestamp("created_at"));
                    post.setUpdatedAt(rs.getTimestamp("updated_at"));

                    postList.add(post);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return postList;
    }

    // 전체 게시글 개수 조회
    public int countAll() {

        String sql = "SELECT COUNT(*) FROM posts";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

}
