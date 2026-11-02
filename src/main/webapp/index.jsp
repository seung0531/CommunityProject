<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.community.model.User" %>

<%
    User loginUser = (User) session.getAttribute("loginUser");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>커뮤니티</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

    <header class="home-header" id="mainHeader">

        <h2 class="home-logo">Community</h2>

        <div class="home-menu">

            <% if (loginUser != null) { %>

                <span class="welcome-message">
                    <%= loginUser.getUsername() %>님 환영합니다.
                </span>

                <a href="logout" class="menu-link">로그아웃</a>

            <% } else { %>

                <a href="login.jsp" class="menu-link">로그인</a>
                <a href="signup.jsp" class="menu-link">회원가입</a>

            <% } %>

        </div>

    </header>

    <main class="home-container" id="mainContent">

        <h1 class="home-title">Community Website</h1>

        <p class="home-description">
            자유롭게 소통할 수 있는 커뮤니티입니다.
        </p>

        <% if (loginUser != null) { %>

            <p class="home-message">
                로그인에 성공했습니다!
            </p>

        <% } else { %>

            <p class="home-message">
                로그인하고 커뮤니티를 이용해 보세요.
            </p>

        <% } %>

        <div class="home-board-menu">

            <a href="posts" class="home-button">
                게시글 목록
            </a>

            <% if (loginUser != null) { %>

                <a href="write.jsp" class="home-button">
                    게시글 작성
                </a>

            <% } %>

        </div>

    </main>

</body>
</html>