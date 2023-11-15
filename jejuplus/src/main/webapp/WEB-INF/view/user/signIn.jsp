<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/header.jsp"%>
<!-- header.jsp  -->
<link rel="stylesheet" href="/css/user/user.css" />
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>

<!-- start main.jsp -->
<main>

	<div class="d-flex justify-content-center">
		<form action="/user/sign-in" method="post">
			<h2 style="text-align: center; margin-top: 5%;">로그인</h2>

			<div class="signin-body">
				<div class="form-group" >
					<input type="text"
						id="username" class="form-control signin-text"
						placeholder="Enter username" name="username">
				</div>

				<div class="form-group" >
					 <input type="password"
						 id="pwd" class="form-control signin-text" 
						placeholder="Enter password" name="password">
				</div>


				<div class="signin-location-btn">

					<div class="siginin-findusername-btn">
						<div onclick="window.location.href='/user/find-username'">아이디
							찾기</div>
					</div>

					<div class="siginin-findpassword-btn">
						<div onclick="window.location.href='/user/find-password'">비밀번호
							찾기</div>
					</div>

				</div>

				<hr class="my-2">

				<button type="submit" class="btn btn-warning btn-lg">로그인</button>
				<br> <br> <a
					href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=1c9a0248b81dbbc743e8918bc64a86e5&redirect_uri=http://localhost:80/user/kakao/callback">
					<img src="/images/kakao/kakao_login_medium_wide.png"
					style="width: 60%; margin-left: 20%; margin-bottom: 10%;">
				</a>

			</div>
		</form>
	</div>
</main>
<!-- footer.jsp  -->
<%@ include file="/WEB-INF/view/footer.jsp"%>