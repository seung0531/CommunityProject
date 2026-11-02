<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.community.model.Post" %>
<%@ page import="com.community.model.User" %>

<%
    List<Post> postList =
        (List<Post>) request.getAttribute("postList");

    User loginUser =
        (User) session.getAttribute("loginUser");

    Integer currentPageObj =
        (Integer) request.getAttribute("currentPage");

    Integer totalPagesObj =
        (Integer) request.getAttribute("totalPages");

    int currentPage =
        currentPageObj != null ? currentPageObj : 1;

    int totalPages =
        totalPagesObj != null ? totalPagesObj : 0;

    String searchKeyword =
        (String) request.getAttribute("keyword");

    String encodedKeyword = searchKeyword != null
        ? java.net.URLEncoder.encode(searchKeyword, "UTF-8")
        : "";
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 목록</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

    <main class="posts-container" id="postsContainer">

        <div class="posts-header">
            <h1 class="posts-title">커뮤니티 게시판</h1>
            <a href="index.jsp" class="btn btn-secondary">홈으로</a>
        </div>

        <div class="posts-user-area">

            <% if (loginUser != null) { %>

                <p class="posts-welcome">
                    <%= loginUser.getUsername() %>님 환영합니다.
                </p>

                <a href="write.jsp" class="btn btn-primary">
                    게시글 작성
                </a>

            <% } else { %>

                <p class="posts-login-message">
                    게시글을 작성하려면 로그인이 필요합니다.
                </p>

                <a href="login.jsp" class="btn btn-primary">
                    로그인
                </a>

            <% } %>

        </div>

        <section class="posts-section">

            <h2 class="posts-section-title">게시글 목록</h2>

            <!-- 검색 영역 -->
            <form action="posts" method="get" class="search-form"
                  id="searchForm">

                <input type="text"
                       name="keyword"
                       id="searchKeyword"
                       class="search-input"
                       placeholder="제목 또는 내용을 검색하세요."
                       value="<%= searchKeyword != null ? searchKeyword : "" %>">

                <button type="submit" class="btn btn-primary">
                    검색
                </button>

                <a href="posts" class="btn btn-secondary">
                    전체 목록
                </a>

            </form>

            <!-- 게시글 테이블 -->
            <% if (postList == null || postList.isEmpty()) { %>

                <p class="posts-empty">
                    등록된 게시글이 없습니다.
                </p>

            <% } else { %>

                <div class="table-wrapper">

                    <table class="posts-table" id="postsTable">

                        <thead>
                            <tr>
                                <th>번호</th>
                                <th>제목</th>
                                <th>작성자</th>
                                <th>작성일</th>
                            </tr>
                        </thead>

                        <tbody>

                            <% for (Post post : postList) { %>

                                <tr>
                                    <td><%= post.getId() %></td>

                                    <td class="post-title-cell">
                                        <a href="postDetail?id=<%= post.getId() %>"
                                           class="post-title-link">
                                            <%= post.getTitle() %>
                                        </a>
                                    </td>

                                    <td><%= post.getUsername() %></td>

                                    <td><%= post.getCreatedAt() %></td>
                                </tr>

                            <% } %>

                        </tbody>

                    </table>

                </div>

                <!-- 페이지네이션 -->
                <div class="pagination" id="pagination">

                    <% if (currentPage > 1) { %>

                        <a href="posts?page=<%= currentPage - 1 %>&keyword=<%= encodedKeyword %>"
                           class="page-button">
                            이전
                        </a>

                    <% } %>

                    <% for (int i = 1; i <= totalPages; i++) { %>

                        <a href="posts?page=<%= i %>&keyword=<%= encodedKeyword %>"
                           class="page-button <%= currentPage == i ? "active" : "" %>">
                            <%= i %>
                        </a>

                    <% } %>

                    <% if (currentPage < totalPages) { %>

                        <a href="posts?page=<%= currentPage + 1 %>&keyword=<%= encodedKeyword %>"
                           class="page-button">
                            다음
                        </a>

                    <% } %>

                </div>

            <% } %>

        </section>

    </main>

</body>
</html>
