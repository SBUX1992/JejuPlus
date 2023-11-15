<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/header.jsp"%>
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>
<!-- header.jsp  -->
<link rel="stylesheet" href="/css/user/user.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<body>
<div class=detail-promotion-img-text>
	<div id="img-box">
		<img src="/images/제주도바다이쁘네.jpg"
			onerror="this.src='/images/NoImage.jpg'" id="up-img">
	</div>
	
	<div class="detail-promotion-uptext">
		<b style="color: white;">Jeju travel</b>
		</div>
		</div>
	<div class="promotionDetail-page">

		<label class="promotionDetail-label">소개할 곳</label> <input type="text"
			name="promotiontitle" class="promotionDetail-form-control" readonly
			value="${promotion.title}">
			 <label
			class="promotionDetail-label">소개글</label> 
			<input type="text"
			name="promotionIntroduce" class="promotionDetail-form-control"
			readonly value="${promotion.introduce}"> 
			
			<label
			class="promotionDetail-label" style="margin-bottom:3rem; margin-left: 44%;">상세소개</label>
		
		<div class="promotionDetail-image-container">
			<c:forEach var="image" items="${images}">
				<div class="promotionDetail-image-wrapper">
					<img src="${image.imgPath}"
						onerror="this.src='/images/NoImage.jpg'">
				</div>
			</c:forEach>
		</div>
		

		<div style="margin-top: 20px;"></div>

		<label class="promotionDetail-label"></label> 
		<textarea name="promotionContent" class="promotionDetail-form-control promotion-content" readonly>${promotion.content}</textarea>
	</div>

</body>
<%@ include file="/WEB-INF/view/footer.jsp"%>