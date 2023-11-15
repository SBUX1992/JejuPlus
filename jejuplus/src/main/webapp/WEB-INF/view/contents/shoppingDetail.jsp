<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/view/header.jsp"%>

<link rel="stylesheet" href="/css/contents/detail.css" />
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>

<body>
	<div id="img-cover">
		<div id="img-box">
		<img src="${shoppingDetail.imgPath}" onerror="this.src='/images/NoImage.jpg'" id=imgPath>
		</div>
		<div id="title">${shoppingDetail.title}</div>
	</div>
	<div id="box">		
		<br>
		<br>
		<div id="introduction">" ${shoppingDetail.introduction} "</div>
		<br>
		<div id="tag">${shoppingDetail.tag}</div>
		<div><input type="hidden" value="${shoppingDetail.contentsLabel}" id="contentsLabel"></div>
		<br><hr>
		
		<table id="icon">
			<tr>
				<td>
					<c:if test="${principal != null}">
						<c:choose>
							<c:when test="${isRecommended}">
								<img src="/images/좋아요2.png" class="icon" id="recommended" >
							</c:when>
							<c:otherwise>
								<img src="/images/좋아요.png" class="icon" id="unrecommended">
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${principal == null}">
						<img src="/images/좋아요.png" class="icon" id="unrecommended2">
					</c:if>
				</td>
				<td><a href="#insertReview"><img src="/images/리뷰.png" class="icon"></a></td>
				<td>
					<input type="hidden" id="userId" value="${principal.userId}">
					<c:if test="${principal != null}">
						<c:choose>
							<c:when test="${isFavorite}">
								<img src="/images/저장2.png" class="icon" id="favorite" >
							</c:when>
							<c:otherwise>
								<img src="/images/저장.png" class="icon" id="unfavorite">
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${principal == null}">
						<img src="/images/저장.png" class="icon" id="unfavorite2">
					</c:if>
				</td>
			</tr>
			<tr>
				<td>추천하기</td>
				<td>리뷰쓰기</td>
				<td>저장하기</td>
			</tr>
		</table>
		<hr><br><br>
		<div class="subTitle">상세정보</div><br>
		
		<div>
			<div id=map>지도</div>
			<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=	23d4bf3926d523313e54a46d82cbb016"></script>
		</div><br>
		<div id="infomation">
			<div id="infomation-title">기본정보</div>
			<hr>
			<table id="table">
				<tr>
					<th>장소</th>
					<td style="text-align:left">${shoppingDetail.title}</td>
				</tr>
				<tr>
					<th>주소</th>
					<td style="text-align:left">${shoppingDetail.roadAddress}</td>
				</tr>
				<tr>
					<th>연락처</th>
					<td style="text-align:left">${shoppingDetail.phoneNo}</td>
				</tr>
			</table>
		</div>
		<br><br><br>
		<div class="subTitle">리뷰</div>
		<br>
	</div>
	
	<form name="insertReview" id="insertReview" method="post">
		<fieldset>
			<span class="text-bold">별점을 선택해주세요</span>
			<input type="radio" name="reviewStar" value="5" id="rate1"><label for="rate1">★</label>
			<input type="radio" name="reviewStar" value="4" id="rate2"><label for="rate2">★</label>
			<input type="radio" name="reviewStar" value="3" id="rate3"><label for="rate3">★</label>
			<input type="radio" name="reviewStar" value="2" id="rate4"><label for="rate4">★</label>
			<input type="radio" name="reviewStar" value="1" id="rate5"><label for="rate5">★</label>
		</fieldset>
		<div>
			<textarea id="reviewContent" placeholder="직접 경험한 솔직한 리뷰를 남겨주세요."></textarea>
		</div>
		<div>
			<input type="hidden" id="contentsId" value="${shoppingDetail.contentsId}">
		</div>
		<div id="review-btn"><input type="button" value="리뷰등록" id="insertReviewBtn"></div>
	</form>
	<br>
	<br>
	
	<div id="reviewList">
	<c:forEach var="review" items="${review}">
	<table width="1000px">
		<tbody>
				<tr>
					<td width="10%">
						<div><input type="hidden" value="${review.reviewId}" id="reviewId"></div>
						<div id="username">${review.username}</div>
					</td>
					<td width="8%">
						<div>
							<span class="${review.reviewStar>=1 ? "yellowStar" : "greyStar"}">★</span>
							<span class="${review.reviewStar>=2 ? "yellowStar" : "greyStar"}">★</span>
							<span class="${review.reviewStar>=3 ? "yellowStar" : "greyStar"}">★</span>
							<span class="${review.reviewStar>=4 ? "yellowStar" : "greyStar"}">★</span>
							<span class="${review.reviewStar>=5 ? "yellowStar" : "greyStar"}">★</span>
						</div>					
					</td>
					<td width="64%">
						<div id="reviewStar">(${review.reviewStar})</div>
					</td>
					<td style="text-align:right" width="20%">
						<div id="reviewDate"><fmt:formatDate value = "${review.createdAt}" pattern="yyyy-MM-dd hh:mm"/></div>
					</td>
				</tr>
				<tr>
					<td colspan="3" width="85%" style="word-break:break-all">${review.reviewContent}</td>
					<c:if test="${review.userId eq principal.userId}">
						<td style="text-align:right" width="15%"><input type="button" value="수정" id="modifyReviewBtn">
						<input type="button" value="삭제" id="deleteReviewBtn"></td>
					</c:if>
				</tr>
		</tbody>
	</table>
	<hr>
	</c:forEach>
	</div>
	<br><br>
	
	<div id="modal">
		<div class="modal-content">
			<div id="modal-title">리뷰 수정</div>
			<form name="modifyReview" id="modifyReview" method="post">
				<fieldset>
					<span class="text-bold">별점을 선택해주세요</span>
					<input type="radio" name="modifyStar" value="5" id="rate_1"><label for="rate_1">★</label>
					<input type="radio" name="modifyStar" value="4" id="rate_2"><label for="rate_2">★</label>
					<input type="radio" name="modifyStar" value="3" id="rate_3"><label for="rate_3">★</label>
					<input type="radio" name="modifyStar" value="2" id="rate_4"><label for="rate_4">★</label>
					<input type="radio" name="modifyStar" value="1" id="rate_5"><label for="rate_5">★</label>
				</fieldset>
				<textarea id="modifyContent" placeholder="직접 경험한 솔직한 리뷰를 남겨주세요."></textarea>
			</form>
			<div id="modal-button">
				<button id="modifyModal">수정완료</button>
				<button id="closeModal">취소</button>
			</div>
		</div>
	</div>
	
	<input type="hidden" id="user-id" value="${principal.userId }">
</body>

<script>
	const container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	
	let options = { //지도를 생성할 때 필요한 기본 옵션
		center: new kakao.maps.LatLng("${shoppingDetail.latitude}", "${shoppingDetail.longitude}"), //지도의 중심좌표.
		level: 7 //지도의 레벨(확대, 축소 정도)
	};
	let map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
	
	// 마커가 표시될 위치
	var markerPosition  = new kakao.maps.LatLng("${shoppingDetail.latitude}", "${shoppingDetail.longitude}"); 

	// 마커를 생성
	var marker = new kakao.maps.Marker({
	    position: markerPosition
	});

	// 마커가 지도 위에 표시되도록 설정
	marker.setMap(map);
	
	const modal = document.getElementById("modal");
	const openModalBtn = document.getElementById("modifyReviewBtn");
	const closeMadalBtn = document.getElementById("closeModal");
	
</script>
<script src="/js/contents/contents.js"></script>
<%@ include file="/WEB-INF/view/footer.jsp"%>