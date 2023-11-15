<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>제주 플러스</title>




<!-- bootstrap -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
<script type="text/javascript"
	src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>
<!-- google font -->

<!-- fontawesome -->
<script defer
	src="https://use.fontawesome.com/releases/v5.15.4/js/all.js"
	integrity="sha384-rOA1PnstxnOBLzCLMcre8ybwbTmemjzdNlILg8O7z1lUkLXozs4DHonlDtnE7fpc"
	crossorigin="anonymous"></script>

<!-- 슬라이드토글 -->

<!-- headfoot css -->
<link rel="stylesheet" href="/css/headfoot.css" />
<!-- headfoot js -->
<script src="/js/headfoot.js"></script>
</head>


<body>
	<!-- header -->
	<header>
		<nav>
			<c:choose>
				<c:when test="${isMainPage}">
					<div class="logo">
						<a href="/main"> <img src="/images/제주플러스2.png" alt="로고" /></a>
					</div>
				</c:when>

				<c:otherwise>
					<div class="logo">
						<a href="/main"> <img src="/images/제주플러스.png" alt="로고" /></a>
					</div>
				</c:otherwise>
			</c:choose>
			<ul class="menu">
				<li><a href="/air/index">항공권</a></li>
				
				<li><a href="#" id="travelLink">여행 정보</a></li>
				<div id="hiddenMenu2" style="display: none;">
					<div class="hidden_div">
						<div><a href="/contents/touristAreaList">관광지</a></div>
						<div><a href="/contents/restaurantList">음식점</a></div>
						<div><a href="/contents/lodgingList">숙박</a></div>
						<div><a href="/contents/shoppingList">쇼핑</a></div>
					</div>
				</div>
				
				<li><a href="#" id="mytravelLink">나의 여행</a></li>
				<div id="hiddenMenu3" style="display: none;">
					<div class="hidden_div">
						<div><a href="/schedule/list">여행 일정</a></div>
						<div><a href="/contents/favoriteList">찜한 여행</a></div>
					</div>
				</div>
				
				<li><a href="#" id="myPageLink">마이페이지</a></li>

				<div id="hiddenMenu" style="display: none;">
					<div class="hidden_div">
						<c:choose>
							<c:when test="${principal == null}">
								<div><a href="/user/register">회원가입</a></div>
								<div><a href="/user/sign-in">로그인</a></div>
							</c:when>
	
							<c:otherwise>
								<input type="hidden" id="is-login" value='${principal ne null}'>
								<div><a href="/user/userUpdate/${principal.userId}">내 정보</a></div>
								<div><a href="/user/orderList/">구매 내역</a></div>
								<div><a href="/user/logout">로그아웃</a></div>
								<c:if test="${principal.levelId >= 2}">
									<div><a class="common-black-font"
										href="/admin/adminUserManagement">관리자</a></div>
								</c:if>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				
				<li><a href="#" id="toggleButton">날씨</a></li>
				<div class="app">
					<div class="sidebar">
					<!-- 주의! 자동 줄맞춤 시 날씨를 불러오지 못함 -->
						<div id="ww_74290b40007d0" v='1.3' loc='id' a='{"t":"horizontal","lang":"ko","sl_lpl":1,"ids":["wl7410"],"font":"Arial","sl_ics":"one_a","sl_sot":"celsius","cl_bkg":"image","cl_font":"#FFFFFF","cl_cloud":"#FFFFFF","cl_persp":"#81D4FA","cl_sun":"#FFC107","cl_moon":"#FFC107","cl_thund":"#FF5722"}'>More forecasts: <a href="https://sharpweather.com/ko/seoul/" id="ww_74290b40007d0_u" target="_blank">sharpweather.com</a></div>
						<script async src="https://app2.weatherwidget.org/js/?id=ww_74290b40007d0"></script>
					</div>
				</div>
			</ul>
		</nav>
		<script>
			var myPageLink = document.getElementById("myPageLink");
			var hiddenMenu = document.getElementById("hiddenMenu");

			myPageLink.addEventListener("click", function() {
				if (hiddenMenu.style.display === "none") {
					hiddenMenu.style.display = "block";
					hiddenMenu2.style.display = "none";
					hiddenMenu3.style.display = "none";
				} else {
					hiddenMenu.style.display = "none";
				}
			});
			
			var travelLink = document.getElementById("travelLink");
			var hiddenMenu2 = document.getElementById("hiddenMenu2");

			travelLink.addEventListener("click", function() {
				if (hiddenMenu2.style.display === "none") {
					hiddenMenu2.style.display = "block";
					hiddenMenu.style.display = "none";
					hiddenMenu3.style.display = "none";
				} else {
					hiddenMenu2.style.display = "none";
				}
			});
			
			var mytravelLink = document.getElementById("mytravelLink");
			var hiddenMenu3 = document.getElementById("hiddenMenu3");

			mytravelLink.addEventListener("click", function() {
				if (hiddenMenu3.style.display === "none") {
					hiddenMenu3.style.display = "block";
					hiddenMenu.style.display = "none";
					hiddenMenu2.style.display = "none";
				} else {
					hiddenMenu3.style.display = "none";
				}
			});
		</script>
	</header>