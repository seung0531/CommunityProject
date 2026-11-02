package com.community.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.community.model.User;
import com.community.util.DBConnection;

import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

    // 회원가입

	// 회원가입
	public boolean join(User user) {

	    String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";

	    // 비밀번호 BCrypt 암호화
	    String hashedPassword = BCrypt.hashpw(
	        user.getPassword(),
	        BCrypt.gensalt(12)
	    );

	    try (
	        Connection conn = DBConnection.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql)
	    ) {

	        pstmt.setString(1, user.getUsername());
	        pstmt.setString(2, hashedPassword);
	        pstmt.setString(3, user.getEmail());

	        int result = pstmt.executeUpdate();

	        return result == 1;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}


    // 아이디 중복 확인
    public boolean isUsernameExists(String username) {

        String sql = "SELECT id FROM users WHERE username = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

    // 로그인
    public User login(String username, String password) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    String hashedPassword = rs.getString("password");

                    // 입력한 비밀번호와 DB의 암호화된 비밀번호 비교
                    if (BCrypt.checkpw(password, hashedPassword)) {

                        User user = new User();

                        user.setId(rs.getInt("id"));
                        user.setUsername(rs.getString("username"));
                        user.setEmail(rs.getString("email"));

                        return user;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
