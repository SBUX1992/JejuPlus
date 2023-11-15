<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/header.jsp"%>

<link rel="stylesheet" href="/css/user/user.css" />
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>
<!-- header.jsp  -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


<main>
	<div class="d-flex justify-content-center">
		<form id="registrationForm"
			action="/user/userUpdate/${userDetailDto.userId}" method="post">

			<h2 style="text-align: center; margin-top: 5%;">내 정보</h2>

			<div class="userUpdate-body">
				<input type="hidden" id="userId" class="form-control" name="userId"
					value="${userDetailDto.userId}"> <label for="username">Id:</label>
				<div class="form-inline">
					<input type="text" id="username" class="form-control"
						name="username" value="${userDetailDto.username}">
				</div>

				<label for="fullname">Name :</label>
				<div class="form-inline">
					<input type="text" id="fullname" class="form-control"
						name="fullname" value="${userDetailDto.fullname}">
				</div>

				<label for="phoneNumber">PhoneNumber :</label>
				<div class="form-inline">
					<input type="text" id="phoneNumber" class="form-control"
						name="phoneNumber" value="${userDetailDto.phoneNumber}">
				</div>

				<label for="orgemail">Email:</label>
				<div class="form-inline">
					<input type="text" id="email" class="form-control mb-2 mr-sm-2"
						name="email" readonly value="${userDetailDto.email}">
				</div>

				<div class="update-btn">
					<button type="button" id="updateEmailButton"
						class="btn user-btn mb-2" >이메일 변경</button>
				</div>

				<div id="verificationCodeSection" style="display: none;">
					<label for="newemail">Email:</label>

					<div class="form-inline">
						<input type="text" id="newEmail" class="form-control mb-2 mr-sm-2"
							name="newEmail" value="${userDetailDto.email}">
					</div>
					<div class="update-btn">
						<button type="button" id="sendCodeButton"
							class="btn user-btn  mb-2">코드 전송</button>
					</div>

					<label>인증 코드: </label>

					<div class="form-inline">
						<input type="text" name="code" class="form-control mb-2 mr-sm-2">
					</div>

					<div class="update-btn">
						<button type="button" id="verifyCodeButton"
							class="btn  user-btn mb-2">인증하기</button>
					</div>
				</div>

				<label>비밀번호: </label>
				<div class="form-inline">
					<input type="text" id="password-text"
						class="form-control mb-2 mr-sm-2" name="password-text" readonly
						value="***********">
				</div>

				<div class="update-btn">
					<button id="password-update-btn" class="btn user-btn mb-2">비밀번호
						변경</button>
				</div>

				<div class="form-inline">
					<div id="password-update-section" style="display: none;">
						<input type="password" id="current-password" placeholder="현재 비밀번호"
							class="form-control mb-2 mr-sm-2"> <input type="password"
							id="new-password" placeholder="새로운 비밀번호"
							class="form-control mb-2 mr-sm-2">
						<div id="confirm-password-update-btn" class="btn  user-btn mb-2">비밀번호
							변경</div>
					</div>
				</div>

				<button id="update-user-Button" class="btn  mb-2">수정하기</button>

				<div>
					<div onclick="window.location.href='/user/userDelete'"
						class="btn btn-red mb-2" id="user-delete-location-btn">회원 탈퇴</div>
				</div>
			</div>
		</form>
	</div>
</main>


<script src='/js/user/update.js'></script>

<!-- footer.jsp  -->
<%@ include file="/WEB-INF/view/footer.jsp"%>