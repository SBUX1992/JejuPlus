<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/header.jsp"%>
<!-- header.jsp  -->
<link rel="stylesheet" href="/css/user/user.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>

<!-- start main.jsp -->
<main>
<div class="d-flex justify-content-center">
		

		<form action="/user/find-username" method="post">
		<h2 style = "text-align: center; margin-top:5%;">아이디 찾기</h2>
		
		<div class="find-username-body">
			<div class="form-group">
				<label for="email">이메일 주소:</label> 
				<input type="text" id="email" class="form-control mb-2 mr-sm-2" placeholder="이메일 주소 입력" name="email">
			</div>
			
			<button type="submit" class="btn btn-lg" id="find-user-Button">아이디 찾기</button>
			</div>
		</form>


	</div>

	<!-- end of main.jsp -->

</main>
<script src='/js/user/findUsername.js'></script>
<!-- footer.jsp  -->
<%@ include file="/WEB-INF/view/footer.jsp"%>