<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/header.jsp"%>
<!-- header.jsp  -->
<link rel="stylesheet" href="/css/user/user.css" />
<style>
.signin-body{
width:600px;
margin-top:5%;
}


.signin-body > input[type='text'],input[type='password'] {
  width:100%;
}

.signin-body > .signin-location-btn {
display:flex;
justify-content: center;
 gap: 2em;
 margin-top:10%;
 margin-bottom:5%;

}

.signin-body > .signin-location-btn > .siginin-findusername-btn:hover {
text-decoration: underline;
cursor : pointer;
font-weight:bold;
}

.signin-body > .signin-location-btn > .siginin-findpassword-btn:hover {
text-decoration: underline;
cursor : pointer;
font-weight:bold;
}

.signin-body > button {
width:60%;
height:auto;
margin-left:20%;
margin-top:5%;
}





</style>
<!-- start main.jsp -->
<main>
	
<div class="d-flex justify-content-center">
			<form action="/user/sign-in" method="post">
			<h2 style = "text-align: center; margin-top:5%;">로그인</h2>
			
			<div class="signin-body">
			
			
				<div class="form-group">
					<label for="username">아이디 :</label> 
					<input type="text" id="username" class="form-control mb-2 mr-sm-2" placeholder="Enter username" name="username">
				</div>
		
				
				<div class="form-group">
					<label for="pwd">비밀번호 :</label> 
					<input type="password" class="form-control" id="pwd"  class="form-control"placeholder="Enter password" name="password">
				</div>
				
				<div class="signin-location-btn">			
					
				<div class="siginin-findusername-btn">
				<div onclick="window.location.href='/user/find-username'">아이디 찾기</div>
				</div>
				
				<div class="siginin-findpassword-btn">		
				<div onclick="window.location.href='/user/find-password'">비밀번호 찾기</div>
				</div>	
				
				</div>
				
				 <hr class="my-2">
				
				<button type="submit" class="btn btn-warning btn-lg" >로그인</button><br>
				<br>
				
				<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=1c9a0248b81dbbc743e8918bc64a86e5&redirect_uri=http://localhost:80/user/kakao/callback">
				<img src="/images/kakao/kakao_login_medium_wide.png"  style="width:60%; margin-left:20%; margin-bottom:10%;">
				</a>
					
					</div>
			</form>
			
		
	</div>	

	




</main>
<!-- footer.jsp  -->
<%@ include file="/WEB-INF/view/footer.jsp"%>