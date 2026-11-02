package com.community.controller;

import java.io.IOException;

import com.community.dao.UserDAO;
import com.community.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        User user = new User(username, password, email);

        UserDAO userDAO = new UserDAO();

        if (userDAO.isUsernameExists(username)) {
            response.getWriter().println("이미 존재하는 아이디입니다.");
            return;
        }

        boolean result = userDAO.join(user);

        if (result) {
            response.sendRedirect("login.jsp");
        } else {
            response.getWriter().println("회원가입에 실패했습니다.");
        }
    }
}
