package com.community.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.community.dao.PostDAO;
import com.community.model.Post;
import com.community.model.User;

@WebServlet("/writePost")
public class WritePostServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 로그인 여부 확인
        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("loginUser") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        User loginUser =
            (User) session.getAttribute("loginUser");

        // 작성한 게시글 정보 가져오기
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        // 입력값 확인
        if (title == null || title.trim().isEmpty() ||
            content == null || content.trim().isEmpty()) {

            response.sendRedirect("write.jsp?error=1");
            return;
        }

        // 게시글 객체 생성
        Post post = new Post(
            loginUser.getId(),
            title,
            content
        );

        // DB 저장
        PostDAO postDAO = new PostDAO();

        boolean result = postDAO.write(post);

        if (result) {
            response.sendRedirect("posts");
        } else {
            response.sendRedirect("write.jsp?error=2");
        }
    }
}
