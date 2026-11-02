<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>로그인</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

    <main class="auth-container" id="loginContainer">

        <h2 class="auth-title">로그인</h2>

        <%
            String error = request.getParameter("error");

            if ("1".equals(error)) {
        %>

            <p class="auth-error" id="loginError">
                아이디 또는 비밀번호가 올바르지 않습니다.
            </p>

        <%
            }
        %>

        <form action="login"
              method="post"
              class="auth-form"
              id="loginForm">

            <div class="form-group">

                <label for="loginUsername">아이디</label>

                <input type="text"
                       id="loginUsername"
                       name="username"
                       class="form-input"
                       placeholder="아이디를 입력하세요."
                       required>

            </div>

            <div class="form-group">

                <label for="loginPassword">비밀번호</label>

                <input type="password"
                       id="loginPassword"
                       name="password"
                       class="form-input"
                       placeholder="비밀번호를 입력하세요."
                       required>

            </div>

            <button type="submit"
                    class="btn btn-primary auth-submit">
                로그인
            </button>

        </form>

        <div class="auth-links">
            <a href="signup.jsp">회원가입</a>
            <a href="index.jsp">홈으로</a>
        </div>

    </main>

</body>
</html>
