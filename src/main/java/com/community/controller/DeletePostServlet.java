package com.community.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.community.dao.PostDAO;
import com.community.model.User;

@WebServlet("/deletePost")
public class DeletePostServlet extends HttpServlet {

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

        User loginUser = (User) session.getAttribute("loginUser");

        int postId;

        try {
            postId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        PostDAO postDAO = new PostDAO();

        boolean result = postDAO.delete(
            postId, loginUser.getId()
        );

        if (result) {
            response.sendRedirect("posts");
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}

