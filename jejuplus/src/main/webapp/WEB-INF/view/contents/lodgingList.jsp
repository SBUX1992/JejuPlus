<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/view/header.jsp"%>
<link rel="stylesheet" href="/css/contents/list.css" />
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>
<body>
	<div id="contentsLabel">숙박</div>
	<hr>
	<br><br>
	<div id="container">
		<c:forEach var="contents" items="${lodgingList}">
			<div id="img-box"><img src="${contents.thumbnailPath}" onerror="this.src='/images/NoImage.jpg'" id="img"></div>
			<div id="content-box">
				<div id="title">${contents.title}</div>
				<div id="location">${contents.region1} > ${contents.region2}</div>
				<div id="tag">${contents.tag}</div>
				<div id="icon">
					<div><img src="/images/좋아요.png" class="icon">${contents.recommendedCnt}</div>
					<div><img src="/images/리뷰.png" class="icon">${contents.reviewAvg} (${contents.reviewCnt})</div>
				</div>
				<div><input type="hidden" value="${contents.contentsLabel}" id="contents-label"></div>
				<input type="button" onclick="location.href='/contents/lodgingDetail/${contents.contentsId}'" value="자세히보기" id="btn">
			</div>
		</c:forEach>
		<div class="paging">
			<div class="text-center clearfix">
				<ul class="pagination" id="pagination">
					<c:if test="${pagination.prev}">
						<li class="page-item"><a class="page-link"
							onclick="contentsList.changePage(event)" data-page="${pagination.beginPage-1}">Prev</a></li>
					</c:if>
					<c:forEach var="num" begin="${pagination.beginPage}"
						end="${pagination.endPage}">
						<li
							class="${pagination.paging.page == num ? 'page-item active' : ''}"><a
							class="page-link" onclick="contentsList.changePage(event)" data-page="${num}">${num}</a></li>
					</c:forEach>
		
					<c:if test="${pagination.next}">
						<li class="page-item"><a class="page-link"
							onclick="contentsList.changePage(event)" data-page="${pagination.endPage+1}">Next</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
	
</body>
<script src='/js/contents/list.js'>
</script>
<%@ include file="/WEB-INF/view/footer.jsp"%>