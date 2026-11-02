<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.community.model.Post" %>

<%
    Post post = (Post) request.getAttribute("post");

    if (post == null) {
        response.sendRedirect("posts");
        return;
    }
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 수정</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

    <div class="container edit-container">

        <h1 class="page-title">게시글 수정</h1>

        <form action="editPost" method="post" id="editPostForm">

            <input type="hidden" name="id"
                   value="<%= post.getId() %>">

            <div class="form-group">
                <label for="postTitle">제목</label>
                <input type="text"
                       id="postTitle"
                       name="title"
                       value="<%= post.getTitle() %>"
                       class="form-input"
                       required>
            </div>

            <div class="form-group">
                <label for="postContent">내용</label>
                <textarea id="postContent"
                          name="content"
                          class="form-textarea"
                          required><%= post.getContent() %></textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    수정 완료
                </button>

                <a href="postDetail?id=<%= post.getId() %>"
                   class="btn btn-secondary">
                    취소
                </a>
            </div>

        </form>

    </div>

</body>
</html>
```