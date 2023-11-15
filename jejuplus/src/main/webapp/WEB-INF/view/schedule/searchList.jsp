<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:forEach var="item" items="${list}">
	<div class="card flex-row">
		<div class="card-header border-0">
			<img src="${item.thumbnailPath}" class="thumbnail"
				onerror="this.src='/images/NoImage.jpg'">
		</div>
		<div class="card-body p-3">
			<h4 class="card-title" onclick="scheduleDetailEdit.moveToDetailPage(${item.contentsId},'${item.contentsLabel}')">${item.title}</h4>
			<p class="card-text">${item.region1 }>${item.region2}</p>
			<p class="card-text tag">${item.tag }</p>
			<button class="btn btn-orange float-right"
				onclick="scheduleDetailEdit.addToScheduleList(${item.contentsId},'${item.title}','${item.region1}','${item.region2}','${item.contentsLabel}',${item.longitude},${item.latitude})">일정추가</button>
		</div>
	</div>
</c:forEach>

<div class="paging">
	<div class="text-center clearfix">
		<ul class="pagination" id="pagination">
			<c:if test="${pagination.prev}">
				<li class="page-item "><a class="page-link page-prev"
					onclick="scheduleDetailEdit.printSearchList(${pagination.beginPage-1})" data-page="${pagination.beginPage-1}">Prev</a></li>
			</c:if>
			<c:forEach var="num" begin="${pagination.beginPage}"
				end="${pagination.endPage}">
				<li
					class="${pagination.paging.page == num ? 'page-item active' : ''}"><a
					class="page-link" onclick="scheduleDetailEdit.printSearchList(${num})" data-page="${num}">${num}</a></li>
			</c:forEach>

			<c:if test="${pagination.next}">
				<li class="page-item"><a class="page-link page-next"
					onclick="scheduleDetailEdit.printSearchList(${pagination.endPage+1})" data-page="${pagination.endPage+1}">Next</a></li>
			</c:if>
		</ul>
		<!-- 페이지 관련 버튼을 클릭 시 같이 숨겨서 보낼 값 -->
	</div>
</div>