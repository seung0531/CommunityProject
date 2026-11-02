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

@WebServlet("/editPost")
public class EditPostServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // 수정 페이지 보여주기
    @Override
    protected void doGet(HttpServletRequest request,
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

        String id = request.getParameter("id");

        if (id == null || !id.matches("\\d+")) {
            response.sendRedirect("posts");
            return;
        }

        PostDAO postDAO = new PostDAO();
        Post post = postDAO.findById(Integer.parseInt(id));

        if (post == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 작성자 본인인지 확인
        if (post.getUserId() != loginUser.getId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        request.setAttribute("post", post);

        request.getRequestDispatcher("/edit.jsp")
               .forward(request, response);
    }

    // 게시글 수정 처리
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("loginUser") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User loginUser =
            (User) session.getAttribute("loginUser");

        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        if (id == null || !id.matches("\\d+") ||
            title == null || title.trim().isEmpty() ||
            content == null || content.trim().isEmpty()) {
            response.sendRedirect("posts");
            return;
        }

        Post post = new Post();

        post.setId(Integer.parseInt(id));
        post.setUserId(loginUser.getId());
        post.setTitle(title);
        post.setContent(content);

        PostDAO postDAO = new PostDAO();

        boolean result = postDAO.update(post);

        if (result) {
            response.sendRedirect("postDetail?id=" + id);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}

