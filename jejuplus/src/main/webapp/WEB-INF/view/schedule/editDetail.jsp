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
<link rel="stylesheet" href="/css/schedule/scheduleDetailEdit.css" />
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>
<!-- ------------------------------------------------------------- -->


<div class="container">
	<div class="row">
		<div class="sche-left col-8">
			<div class="row">
				<div class="col-5">
					<h4>${schedule.title }</h4>
					<h5 style="color: #767676;">${schedule.startDate }~
						${schedule.endDate }</h5>
				</div>
				<div class="col-2">
					<button id="edit-compl" class="btn btn-orange">편집완료</button>
				</div>
				<div class="col-5">
					<img src='/images/schedule/calendar.png' id='calendar'><input
						type="text" id="date" name="schedule-date"
						value="${schedule.startDate}" readonly>
				</div>
			</div>
			<hr>
			<div id="map" class="map" style="width: 100%; height: 600px;"></div>
			<hr>
			<div class="label-tabs">
				<ul class="nav justify-content-between">
					<li class="nav-item active"><a class="nav-link" data-tab="all"
						onclick="scheduleDetailEdit.printContentsList('all')">전체</a></li>
					<li class="nav-item"><a class="nav-link" data-tab="attraction"
						onclick="scheduleDetailEdit.printContentsList('attraction')">관광지</a></li>
					<li class="nav-item"><a class="nav-link" data-tab="accomodation"
						onclick="scheduleDetailEdit.printContentsList('accomodation')">숙박</a></li>
					<li class="nav-item"><a class="nav-link" data-tab="shopping"
						onclick="scheduleDetailEdit.printContentsList('shopping')">쇼핑</a></li>
					<li class="nav-item"><a class="nav-link" data-tab="restaurant"
						onclick="scheduleDetailEdit.printContentsList('restaurant')">음식점</a></li>
					<li class="nav-item"><a class="nav-link" data-tab="favorite"
						onclick="scheduleDetailEdit.printContentsList('favorite')">찜한
							곳</a></li>
					<li class="nav-item"><a class="nav-link" data-tab="search"
						onclick="scheduleDetailEdit.printSearchWindow()">검색</a></li>
				</ul>
			</div>
			<!-- 컨텐츠 리스트가 표시될 div -->
			<div id="tab-output"></div>


		</div>
		<div class="sche-right col-4">
			<div class="list-header row">
				<div id="day" style="height: 40px;" class="col-6">
					<span class="h3">DAY&nbsp;</span><span id="schedule-day" class="h3">1</span>
				</div>
				<div class="col-6" style="height: 40px;">
					<button id="sorting-btn" class="btn btn-orange"
						style="width: 58px; height: 38px;">정렬</button>
					<button id="saving-btn" class="btn btn-primary">저장</button>
				</div>
			</div>
	
			<div id="list-output" class="float-right"></div>
		</div>
	</div>
	<!-- 모달 창 -->
	<div id="myModal" class="modal">
		<div class="modal-content">
			<span class="close" id="closeModalButton" onclick="closeModal()">&times;</span>
			<h3 id="contents-title"></h3>
			<textarea id="contents-memo" placeholder="메모할 내용을 적어주세요."></textarea>
			<input type="hidden" id="contents-index" value="">
		</div>
	</div>
</div>


<!-- 날짜 관련 값들 -->
<input type="hidden" id="start-date" value="${schedule.startDate }">
<input type="hidden" id="end-date" value="${schedule.endDate }">
<input type="hidden" id="schedule-id" value="${schedule.scheduleId }">



<script src='/js/schedule/scheduleDetailEdit.js'></script>
<script src='/js/schedule/scheduleDetailEditCalendar.js'></script>



<!-- ------------------------------------------------------------- -->
<%@ include file="/WEB-INF/view/footer.jsp"%>