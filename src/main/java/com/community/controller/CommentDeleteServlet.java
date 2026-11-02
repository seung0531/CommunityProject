package com.community.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.community.dao.CommentDAO;
import com.community.model.User;

@WebServlet("/deleteComment")
public class CommentDeleteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("loginUser") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        User loginUser =
            (User) session.getAttribute("loginUser");

        int commentId;
        int postId;

        try {
            commentId = Integer.parseInt(
                request.getParameter("commentId")
            );

            postId = Integer.parseInt(
                request.getParameter("postId")
            );

        } catch (NumberFormatException e) {
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST
            );
            return;
        }

        CommentDAO commentDAO = new CommentDAO();

        boolean result = commentDAO.delete(
            commentId, loginUser.getId()
        );

        if (result) {
            response.sendRedirect("postDetail?id=" + postId);
        } else {
            response.sendError(
                HttpServletResponse.SC_FORBIDDEN
            );
        }
    }
}
