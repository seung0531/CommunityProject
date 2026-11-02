<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title>회원가입</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

    <main class="auth-container signup-container" id="signupContainer">

        <h2 class="auth-title">회원가입</h2>

        <form action="signup"
              method="post"
              class="auth-form"
              id="signupForm">

            <div class="form-group">
                <label for="signupUsername">아이디</label>

                <input type="text"
                       id="signupUsername"
                       name="username"
                       class="form-input"
                       placeholder="아이디를 입력하세요."
                       required>
            </div>

            <div class="form-group">
                <label for="signupPassword">비밀번호</label>

                <input type="password"
                       id="signupPassword"
                       name="password"
                       class="form-input"
                       placeholder="비밀번호를 입력하세요."
                       required>
            </div>

            <div class="form-group">
                <label for="signupEmail">이메일</label>

                <input type="email"
                       id="signupEmail"
                       name="email"
                       class="form-input"
                       placeholder="이메일을 입력하세요."
                       required>
            </div>

            <button type="submit"
                    class="btn btn-primary auth-submit">
                회원가입
            </button>

        </form>

        <div class="signup-login-link">
            이미 계정이 있으신가요?
            <a href="login.jsp">로그인</a>
        </div>

    </main>

</body>
</html>
