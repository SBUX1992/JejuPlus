<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- main css -->

<link rel="stylesheet" href="/css/main.css" />
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>

<!-- main -->
<main>

	<section class="hero">
	<c:set var="isMainPage" value="true" />
	<%@ include file="/WEB-INF/view/header.jsp"%>
	
		<h1>제주를 더하다, 제주플러스</h1>
		<p>항공권 예약부터 일정까지 제주여행을 더 간편하게!</p>
		<input type="button" onclick="location.href='/schedule/list'" value="나의 일정 만들기" id="main_btn">


	</section>
	
	<!-- 광고 start -->
	<div class="main-packages-body">
		<!-- 왼쪽 화살표 버튼 -->
		<button class="slide-prev" id="slide-prev-3">
			<i class="bi bi-caret-left-fill"></i>
		</button>
		<section class="tour-packages">
			<div>
				<h2># 광고</h2>
			</div>

			<div class="carousel-container">
				<div class="packageContainer" id="multiple-items-3">
					<!-- 슬라이드 내용 -->
					<c:forEach var="promotions" items="${promotions}">
						<div class="package" style="width:300px;">
							<img src="${promotions.imageUrl}" alt="sample" />
							<h3>${promotions.title}</h3>
							<p>${promotions.introduce}</p>
							<a href="/user/promotionDetail/${promotions.promotionId}" style="color:#f37021">자세히 보기</a>
						</div>
					</c:forEach>
				</div>
			</div>
		</section>
		<!-- 오른쪽 화살표 버튼 -->
		<button class="slide-next" id="slide-next-3">
			<i class="bi bi-caret-right-fill "></i>
		</button>
	</div>
	<!-- 맛집 end -->
	
	

	<!-- 맛집 start -->
	<div class="main-packages-body">
		<!-- 왼쪽 화살표 버튼 -->
		<button class="slide-prev" id="slide-prev">
			<i class="bi bi-caret-left-fill"></i>
		</button>
		<section class="tour-packages">
			<div>
				<h2># 맛집</h2>
			</div>

			<div class="carousel-container">
				<div class="packageContainer" id="multiple-items" >
					<!-- 슬라이드 내용 -->
					<c:forEach var="restaurantDto" items="${restaurantDto}" >
						<div class="package" >
							<img src="${restaurantDto.imgPath}" alt="sample" />
							<h3>${restaurantDto.title}</h3>
							<p>${restaurantDto.tag}</p>
							<a href="/contents/restaurantDetail/${restaurantDto.contentsId}" style="color:#f37021">자세히 보기</a>
						</div>
					</c:forEach>
				</div>
			</div>
		</section>
		<!-- 오른쪽 화살표 버튼 -->
		<button class="slide-next" id="slide-next">
			<i class="bi bi-caret-right-fill "></i>
		</button>
	</div>

	<!-- 인기 관광지 start -->
	<div class="main-packages-body">
		<!-- 왼쪽 화살표 버튼 -->
		<button class="slide-prev" id="slide-prev-2">
			<i class="bi bi-caret-left-fill"></i>
		</button>
		<section class="tour-packages">
			<div>
				<h2># 인기 관광지</h2>
			</div>

			<div class="carousel-container">
				<div class="packageContainer" id="multiple-items-2">
					<!-- 슬라이드 내용 -->
					<c:forEach var="palceDto" items="${palceDto}">
						<div class="package">
							<img src="${palceDto.imgPath}" alt="sample" />
							<h3>${palceDto.title}</h3>
							<p>${palceDto.tag}</p>
							<a href="/contents/touristAreaDetail/${palceDto.contentsId}" style="color:#f37021">자세히 보기</a>
						</div>
					</c:forEach>
				</div>
			</div>
		</section>
		<!-- 오른쪽 화살표 버튼 -->
		<button class="slide-next" id="slide-next-2">
			<i class="bi bi-caret-right-fill "></i>
		</button>
	</div>


	<!-- 인기 투어 패키지 end -->

	<!-- 인기 숙소 start -->

	<!-- 인기 숙소 end -->

	<!-- 맛집 추천 start -->

	<!-- 맛집 추천 end -->
</main>

<!-- js -->
<script>


$(document).ready(function() {
	// 광고 슬라이더
    $('#multiple-items-3').slick({
        infinite: true,
        slidesToShow: 5,
        slidesToScroll: 2,
        autoplay : true,      // 자동 스크롤 사용 여부
        autoplaySpeed : 3000
    });
    $('#slide-prev-3').click(function() {
        $('#multiple-items-3').slick('slickPrev');
    });

    // "다음" 버튼 클릭 시 슬라이드 오른쪽으로 이동
    $('#slide-next-3').click(function() {
        $('#multiple-items-3').slick('slickNext');
    });
	
	// 맛집 슬라이더
    $('#multiple-items').slick({
        infinite: true,
        slidesToShow: 5,
        slidesToScroll: 2,
        autoplay : true,      // 자동 스크롤 사용 여부
        autoplaySpeed : 3000
    });
    $('#slide-prev').click(function() {
        $('#multiple-items').slick('slickPrev');
    });

    // "다음" 버튼 클릭 시 슬라이드 오른쪽으로 이동
    $('#slide-next').click(function() {
        $('#multiple-items').slick('slickNext');
    });
    
    
	// 관광지 슬라이더
    $('#multiple-items-2').slick({
        infinite: true,
        slidesToShow: 5,
        slidesToScroll: 2,
        autoplay : true,      // 자동 스크롤 사용 여부
        autoplaySpeed : 3000
    });
    $('#slide-prev-2').click(function() {
        $('#multiple-items-2').slick('slickPrev');
    });

    // "다음" 버튼 클릭 시 슬라이드 오른쪽으로 이동
    $('#slide-next-2').click(function() {
        $('#multiple-items-2').slick('slickNext');
    });    
});




	</script>
<script src="../js/main.js"></script>
<%@ include file="/WEB-INF/view/footer.jsp"%>