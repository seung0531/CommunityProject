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

@WebServlet("/posts")
public class PostListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;



	@Override
	protected void doGet(HttpServletRequest request,
	        HttpServletResponse response)
	        throws ServletException, IOException {
	
	    request.setCharacterEncoding("UTF-8");
	
	    // 현재 페이지 번호
	    int currentPage = 1;
	
	    try {
	        String pageParam = request.getParameter("page");
	
	        if (pageParam != null) {
	            currentPage = Integer.parseInt(pageParam);
	        }
	
	        if (currentPage < 1) {
	            currentPage = 1;
	        }
	
	    } catch (NumberFormatException e) {
	        currentPage = 1;
	    }
	
	    // 한 페이지에 표시할 게시글 수
	    int pageSize = 10;
	
	    // 게시글 시작 위치
	    int offset = (currentPage - 1) * pageSize;
	
	    String keyword = request.getParameter("keyword");
	
	    PostDAO postDAO = new PostDAO();
	
	    List<Post> postList;
	    int totalPosts;
	
	    // 검색어가 있는 경우
	    if (keyword != null && !keyword.trim().isEmpty()) {
	
	        keyword = keyword.trim();
	
	        postList = postDAO.search(keyword);
	        totalPosts = postList.size();
	
	        // 검색 결과도 10개씩 표시
	        int fromIndex = Math.min(offset, postList.size());
	        int toIndex = Math.min(offset + pageSize, postList.size());
	
	        postList = new java.util.ArrayList<>(
	            postList.subList(fromIndex, toIndex)
	        );
	
	    } else {
	
	        // 전체 게시글 조회
	        totalPosts = postDAO.countAll();
	
	        postList = postDAO.findByPage(offset, pageSize);
	    }
	
	    // 전체 페이지 수
	    int totalPages = (int) Math.ceil(
	        (double) totalPosts / pageSize
	    );
	
	    request.setAttribute("postList", postList);
	    request.setAttribute("keyword", keyword);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("totalPages", totalPages);
	
	    request.getRequestDispatcher("/posts.jsp")
	           .forward(request, response);
	}


}

