<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/header.jsp"%>
<link rel="stylesheet" href="/css/user/user.css" />
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>
<!-- header.jsp  -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- start main.jsp -->

<main>
<div class="d-flex justify-content-center">

   <form id="reset-password-form">
   
   <h2 style = "text-align: center; margin-top:5%;">비밀번호 찾기</h2>
		
	<div class="find-password-body">
   
    <div class="form-group">
    <label for="username">아이디:</label>
        <input type="text" id="username" class="form-control" placeholder="아이디 입력" name="username"><br>
    </div>
        
        <div class="form-group">

        <label for="email">이메일 주소:</label>
        <input type="text" id="reset-password-email" class="form-control mb-2 mr-sm-2" placeholder="이메일 주소 입력" name="email">

        <button type="button" id="sendCodeButton" class="btn  mb-2">코드 전송</button>
		</div>
		
		<br>
		<br>
	<div class="form-group"> 
	   
    <label>인증 코드: </label>
    <input type="text" name="code" class="form-control mb-2 mr-sm-2" placeholder="인증 코드 입력">
    	
    <div class="find-passord-btn" >
    <button type="button" id="verifyCodeButton" class="btn  mb-2">인증하기</button>
    <div class="find-password-space"> </div>
    <button type="submit" class="btn  mb-2" id="reset-password-button">임시 비밀번호 발급</button>
	</div>
    </div>
    
   	<div class="form-group"> 


    </div>
    
    </div>
    </form>

    <div id="reset-password-message"></div>
</div>
</main>

<script src='/js/user/findPassword.js'></script>
<!-- footer.jsp  -->
<%@ include file="/WEB-INF/view/footer.jsp"%>