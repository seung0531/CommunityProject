package com.community.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.community.dao.PostDAO;
import com.community.model.Post;
import com.community.dao.CommentDAO;
import com.community.model.Comment;

@WebServlet("/postDetail")
public class PostDetailServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 게시글 ID 가져오기
        String id = request.getParameter("id");

        if (id == null || !id.matches("\\d+")) {
            response.sendRedirect("posts");
            return;
        }

        // 게시글 조회
        PostDAO postDAO = new PostDAO();
        Post post = postDAO.findById(Integer.parseInt(id));

        // 게시글이 존재하지 않는 경우
        if (post == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // JSP에 게시글 전달
        request.setAttribute("post", post);
        
        // 댓글 목록 조회
        CommentDAO commentDAO = new CommentDAO();

        List<Comment> commentList =
                commentDAO.findByPostId(post.getId());

        // 댓글 목록 JSP에 전달
        request.setAttribute("commentList", commentList);

        request.getRequestDispatcher("/postDetail.jsp")
               .forward(request, response);
    }
}

