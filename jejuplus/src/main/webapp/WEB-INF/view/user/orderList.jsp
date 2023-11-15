<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/view/header.jsp"%>
<!-- css -->
<link rel="stylesheet" href="/css/user/orderlist.css" />
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>

<!-- 메인 -->
<main>
	<div class="mainBackgorund">
		<div class="mainContent">
			<section class="myContent">
				<div class="myorderFrame">
					<!-- 기간 선택 -->
					<div class="byDate">
						<div>
							<strong>${user.fullname}</strong> 고객님 주문 내역
						</div>
					</div>
					<!-- 주문 내역 start -->
					<div class="orderList">
						<table class="" id="orderListTable">
							<p>총 ${orderList.size()} 개의 구매내역이 존재합니다</p>
							<thead>
								<tr>
									<th>상품정보</th>
									<th>출발시간</th>
									<th>도착시간</th>
									<th>주문번호</th>
									<th>기타</th>
								</tr>
							</thead>
							<tbody>
							<c:choose>
									<c:when test="${empty orderList}">
								<!-- 비었으면 -->
								<tr>
									<td colspan="5">구매 내역이 없습니다.</td>
								</tr>
								</c:when>
									<c:otherwise>
								<!-- 아이템 반복 -->
								<c:forEach var="order" items="${orderList}">
									<tr>
										<td>
											<div>
												<a href="">
													<img src="../../images/air/jeju_air_logo.png" alt="jeju_logo" class="flight-logo" />
												</a>
												<ul class="info">
													<li id="company">항공사 : ${order.airlineNm}</li>
													<li id="prodName">${order.depAirportNm} - > ${order.arrAirportNm}</li>
												</ul>
											</div>
										</td>
										<td>${fn:substring(order.depPlandTime, 8, 10)}:${fn:substring(order.depPlandTime, 10, 12)}</td>
										<td>${fn:substring(order.arrPlandTime, 8, 10)}:${fn:substring(order.arrPlandTime, 10, 12)}</td>
										<td>${order.paymentId}</td>
										<td>
											<div class="">
												<button type="submit" class="btn-refund">환불하기</button>
											</div>
										</td>
									</tr>
								</c:forEach>
								</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<!-- 주문내역 end -->
					<div class="center">
						<div class="pagination">
							<a class="prevPage" href="#">&lsaquo;</a>
							<a href="#" class="active">1</a>
							<a class="nextPage" href="#">&rsaquo;</a>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
</main>
</body>

<!-- footer.jsp  -->
<%@ include file="/WEB-INF/view/footer.jsp"%>