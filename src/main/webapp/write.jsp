<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>게시글 작성</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

    <main class="write-container" id="writeContainer">

        <h1 class="page-title">게시글 작성</h1>

        <!-- 오류 메시지 -->
        <% if ("1".equals(request.getParameter("error"))) { %>

            <p class="form-error">
                제목과 내용을 모두 입력해주세요.
            </p>

        <% } else if ("2".equals(request.getParameter("error"))) { %>

            <p class="form-error">
                게시글 작성에 실패했습니다.
            </p>

        <% } %>

        <!-- 게시글 작성 폼 -->
        <form action="writePost"
              method="post"
              id="writePostForm">

            <div class="form-group">

                <label for="postTitle">제목</label>

                <input type="text"
                       id="postTitle"
                       name="title"
                       class="form-input"
                       placeholder="게시글 제목을 입력하세요."
                       required>

            </div>

            <div class="form-group">

                <label for="postContent">내용</label>

                <textarea id="postContent"
                          name="content"
                          class="form-textarea write-textarea"
                          placeholder="게시글 내용을 입력하세요."
                          required></textarea>

            </div>

            <div class="form-actions">

                <button type="submit"
                        class="btn btn-primary">
                    작성하기
                </button>

                <a href="index.jsp"
                   class="btn btn-secondary">
                    취소
                </a>

            </div>

        </form>

    </main>

</body>
</html>
