package com.community.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.community.dao.CommentDAO;
import com.community.model.Comment;
import com.community.model.User;

@WebServlet("/writeComment")
public class CommentWriteServlet extends HttpServlet {

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

        // 게시글 번호와 댓글 내용 가져오기
        int postId;

        try {
            postId = Integer.parseInt(
                request.getParameter("postId")
            );
        } catch (NumberFormatException e) {
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST
            );
            return;
        }

        String content = request.getParameter("content");

        if (content == null || content.trim().isEmpty()) {
            response.sendRedirect("postDetail?id=" + postId);
            return;
        }

        // 댓글 객체 생성
        Comment comment = new Comment(
            postId,
            loginUser.getId(),
            content.trim()
        );

        // DB에 댓글 저장
        CommentDAO commentDAO = new CommentDAO();

        boolean result = commentDAO.write(comment);

        if (result) {
            response.sendRedirect("postDetail?id=" + postId);
        } else {
            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );
        }
    }
}

