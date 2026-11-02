<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.community.model.Post" %>
<%@ page import="com.community.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.community.model.Comment" %>

<%
    Post post = (Post) request.getAttribute("post");

    User loginUser = (User) session.getAttribute("loginUser");

    List<Comment> commentList =
        (List<Comment>) request.getAttribute("commentList");
%>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>게시글 상세보기</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

    <main class="detail-container" id="detailContainer">

        <!-- 게시글 영역 -->
        <section class="post-detail">

            <h1 class="detail-title">
                <%= post.getTitle() %>
            </h1>

            <div class="post-info">

                <span>
                    작성자: <%= post.getUsername() %>
                </span>

                <span class="post-info-divider">|</span>

                <span>
                    작성일: <%= post.getCreatedAt() %>
                </span>

            </div>

            <div class="post-content">
                <%= post.getContent() %>
            </div>

            <!-- 게시글 버튼 -->
            <div class="post-actions">

                <a href="posts" class="btn btn-secondary">
                    목록으로
                </a>

                <% if (loginUser != null &&
                       loginUser.getId() == post.getUserId()) { %>

                    <a href="editPost?id=<%= post.getId() %>"
                       class="btn btn-primary">
                        수정
                    </a>

                    <form action="deletePost"
                          method="post"
                          class="delete-form"
                          onsubmit="return confirm('정말 삭제하시겠습니까?');">

                        <input type="hidden"
                               name="id"
                               value="<%= post.getId() %>">

                        <button type="submit"
                                class="btn btn-danger">
                            삭제
                        </button>

                    </form>

                <% } %>

            </div>

        </section>

        <!-- 댓글 영역 -->
        <section class="comment-section" id="commentSection">

            <h2 class="comment-title">댓글</h2>

            <!-- 댓글 작성 -->
            <% if (loginUser != null) { %>

                <form action="writeComment"
                      method="post"
                      class="comment-form"
                      id="commentForm">

                    <input type="hidden"
                           name="postId"
                           value="<%= post.getId() %>">

                    <textarea name="content"
                              id="commentContent"
                              class="comment-input"
                              placeholder="댓글을 입력하세요."
                              required></textarea>

                    <div class="comment-submit-area">

                        <button type="submit"
                                class="btn btn-primary">
                            댓글 작성
                        </button>

                    </div>

                </form>

            <% } else { %>

                <div class="comment-login-message">

                    <p>댓글을 작성하려면 로그인이 필요합니다.</p>

                    <a href="login.jsp" class="btn btn-primary">
                        로그인하기
                    </a>

                </div>

            <% } %>

            <!-- 댓글 목록 -->
            <div class="comment-list" id="commentList">

                <% if (commentList != null &&
                       !commentList.isEmpty()) { %>

                    <% for (Comment comment : commentList) { %>

                        <div class="comment-item">

                            <div class="comment-header">

                                <strong class="comment-author">
                                    <%= comment.getUsername() %>
                                </strong>

                                <span class="comment-date">
                                    <%= comment.getCreatedAt() %>
                                </span>

                            </div>

                            <p class="comment-content">
                                <%= comment.getContent() %>
                            </p>

                            <% if (loginUser != null &&
                                   loginUser.getId() == comment.getUserId()) { %>

                                <form action="deleteComment"
                                      method="post"
                                      class="comment-delete-form"
                                      onsubmit="return confirm('댓글을 삭제하시겠습니까?');">

                                    <input type="hidden"
                                           name="commentId"
                                           value="<%= comment.getId() %>">

                                    <input type="hidden"
                                           name="postId"
                                           value="<%= post.getId() %>">

                                    <button type="submit"
                                            class="btn btn-danger btn-small">
                                        댓글 삭제
                                    </button>

                                </form>

                            <% } %>

                        </div>

                    <% } %>

                <% } else { %>

                    <p class="comment-empty">
                        아직 작성된 댓글이 없습니다.
                    </p>

                <% } %>

            </div>

        </section>

    </main>

</body>
</html>
