package com.community.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.community.dao.UserDAO;
import com.community.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();

        User user = userDAO.login(username, password);

        if (user != null) {

            // 로그인 성공: 세션 생성
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user);

            // 메인 페이지로 이동
            response.sendRedirect("index.jsp");

        } else {

            // 로그인 실패
            response.sendRedirect("login.jsp?error=1");
        }
    }
}

