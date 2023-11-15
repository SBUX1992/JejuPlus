<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script type="text/javascript"
	src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d5568461ac8305d5d4737b9523509aed"></script>
<link rel="stylesheet" href="/css/schedule/scheduleList.css" />
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>

<!-- ------------------------------------------------------------- -->
<div class="schedule-promotion-img-text">
	<div id="img-box">
		<img src="/images/시험해보자.jpg"
			onerror="this.src='/images/NoImage.jpg'" id="up-img">
	</div>
	
	<div class="schedule-promotion-uptext">
		<b style="color: white;">My schedule</b>
		</div>
		</div>
<div class="container">
	<div class="list-header row">
		<div class="col-8">
			<h3>${principal.fullname} 님의 제주 여행</h3>
		</div>
		<div class="col-4 float-right">
			<button class="btn btn-orange open-add-modal">일정 등록</button>
		</div>
	</div>
	<c:choose>
		<c:when test="${scheduleList.size()==0}">
			<div class="non-list">
				<img class="error-logo" src="/images/logo_dark.png">
				<p class="notice">아직 등록 된 일정이 없어요. 첫 일정을 등록하시겠어요?</p>
				<button class="btn btn-orange open-add-modal">일정 등록</button>
			</div>
		</c:when>
		<c:otherwise>
			<div class="list-body">
				<c:forEach var="item" items="${scheduleList }">
					<div class="card">
						<div class="card-header">
						<c:choose>
							<c:when test="${item.status==0 }">
							<span class="status">여행 중</span>
							</c:when>
							<c:when test="${item.status==1 }">
							<span class="status">여행 전</span>
							</c:when>
							<c:otherwise>
							<span class="status">여행 종료</span>
							</c:otherwise>
						</c:choose>
						</div>
						<div class="card-body">
							<h5 class="card-title">
								<a href="/schedule/detail/show/${ item.scheduleId}">${ item.title }</a>
							</h5>
							<p class="card-text">
								<img src="/images/schedule/list-calendar.png">
								${item.startDate } ~ ${item.endDate }
							</p>
							<button id="open-modify-modal" class="btn btn-warning"
								onclick="openModifyModal(${item.scheduleId},'${item.title}','${item.startDate }','${item.endDate }')">수정</button>
							<button id="sche-delete" class="btn btn-secondary"
								onclick="scheduleList.deleteSche(${item.scheduleId})">삭제</button>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:otherwise>
	</c:choose>
	<!-- 모달 창 -->
	<div id="add-modal" class="modal">
		<div class="modal-content">
			<span class="close" id="close-add-modal">&times;</span>
			<h5>새 여행 일정 등록</h5>
			<input type="text" id="schedule-title"
				placeholder="이번 스케쥴의 이름을 입력해주세요."
				style="width: 457px; height: 50px; margin-bottom: 30px;" />
			<p>
				<img src='/images/schedule/calendar.png' id='sche-calendar'> <input
					type="text" id="sche-start-date" placeholder="시작일"
					style="width: 180px;" readonly> <input type="text"
					id="sche-end-date" placeholder="종료일"
					style="width: 180px; margin-left: 20px;" readonly>
			</p>
			<div class="float-right">
				<button id="sche-add" class="btn btn-primary" style="width: 100px;">일정
					추가</button>
			</div>
		</div>
	</div>
	<div id="modify-modal" class="modal">
		<div class="modal-content">
			<span class="close" id="close-modify-modal">&times;</span>
			<h5>스케쥴 일정 수정</h5>
			<input type="text" id="modify-sche-title"
				style="width: 457px; height: 50px; margin-bottom: 30px;" />
			<p>
				<img src='/images/schedule/calendar.png' id='sche-modify-calendar'>
				<input type="text" id="sche-start-modify" placeholder="시작일"
					style="width: 180px;" readonly> <input type="text"
					id="sche-end-modify" placeholder="종료일"
					style="width: 180px; margin-left: 20px;" readonly>
			</p>
			<div class="float-right">
				<button id="sche-modify" class="btn btn-primary"
					style="width: 100px;">일정 수정</button>
			</div>
		</div>
	</div>
	<!-- 일정 추가 시 같이 서버에 넘길 로그인 된 회원 식별자값 -->
	<input type="hidden" id="user-id" value="${principal.userId }">
</div>

<script src="/js/schedule/scheduleList.js"></script>
<script src="/js/schedule/scheduleListCalendar.js"></script>
<!-- ------------------------------------------------------------- -->
<%@ include file="/WEB-INF/view/footer.jsp"%>