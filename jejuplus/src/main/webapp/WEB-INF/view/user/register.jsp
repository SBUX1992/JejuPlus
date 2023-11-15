<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/header.jsp"%>

<link rel="stylesheet" href="/css/user/user.css" />
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<main>
<div class="d-flex justify-content-center">
	<form action="/user/register" method="post" id="registrationForm">
	<h2 style = "text-align: center; margin-top:5%;">회원가입</h2>
	
	<div class="register-body">
	
		<div class="form-inline register-textButton">
		
		<label class="label-fixed-width">아이디:</label>	
	
			
    <input type="text" name="username" class="register-textField" id="usernameInput" placeholder="사용할 아이디 입력">
    <button type="button" id="checkUsernameButton" class="btn register-button mb-2" style="color: white;">중복확인</button>
</div>
<input type="hidden" name="firstUsername" id="firstUsernameInput" value="">
<div id="usernameResult"></div>


		
		
		
		<br>
		
		<div id="usernameResult"></div>
				 
		<div class="form-inline register-textButton">
		<label class="label-fixed-width">비밀번호:</label>
		<input type="password" name="password" id="passwordInput" class="register-textField " placeholder="사용할 비밀번호 입력">
		<button type="button" id="checkUserPasswordButton" class="btn register-button mb-2" style="color: white;">확인</button>
		</div>
		<input type="hidden" name="firstPassword" id="firstPasswordInput" value="">
	

		
		<br>
		
		<div class="form-inline register-textButton">
		<label class="label-fixed-width">이메일:</label>
		<input type="text" name="email" id="emailInput" class="register-textField" placeholder="사용할 이메일 주소 입력">
		<button type="button" id="sendCodeButton" class="btn  register-button mb-2" style="color: white;">코드전송</button>
		</div>
				
		<br>
		<div id="emailResult"></div>
		
		<div class="form-inline register-textButton">
		<label class="label-fixed-width">인증 코드:</label> 
		<input type="text" name="code" class="register-textField" placeholder="인증 코드 입력" id="codeInput">
		<button type="button" id="verifyCodeButton" class="btn register-button mb-2" style="color: white;">인증하기</button>
		</div>
		
	

		 
		 	<br>
		<div class="form-inline register-textButton">
		<label class="label-fixed-width">이름:</label>
		<input type="text" name="fullname" class="register-textField" placeholder="성함 입력" id="fullnameInput">
		</div>
		
			<br>
			
		<div class="form-inline register-textButton">
		<label class="label-fixed-width">전화번호:</label>
		<input type="text" name="phoneNumber" class="register-textField" placeholder="핸드폰 번호 입력" id="phoneNumberInput">
		</div>
		<br>
		
		<hr>
		
		<div id="register-final">
		<input type="submit" value="가입하기" class="btn " id="register-final-button">
		</div>		
		</div>
	</form>
</div>
	<div id="result"></div>
</main>


<script src='/js/user/register.js'></script>
<%@ include file="/WEB-INF/view/footer.jsp" %>