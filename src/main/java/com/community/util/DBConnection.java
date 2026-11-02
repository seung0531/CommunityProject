package com.community.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3306/community_db?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";

    private static final String USER = "root";

    private static final String PASSWORD =
        System.getenv("COMMUNITY_DB_PASSWORD");

    public static Connection getConnection() throws Exception {

        if (PASSWORD == null || PASSWORD.isEmpty()) {
            throw new IllegalStateException(
                "COMMUNITY_DB_PASSWORD 환경 변수가 설정되지 않았습니다."
            );
        }

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(
            URL,
            USER,
            PASSWORD
        );
    }
}
