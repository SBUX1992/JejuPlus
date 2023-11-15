<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/view/admin/layout/header.jsp"%>
		<!-- PAGE CONTAINER-->
		<div class="page-container">
			<!-- HEADER DESKTOP-->
			<header class="header-desktop">
				<div class="section__content section__content--p30">
					<div class="container-fluid">
						<div class="header-wrap">
							<form class="form-header" action="" method="GET">
								<div class="form-group">
									<select name="category" class="form-control">
										<option value="all">모두보기</option>
										<option value="username">아이디</option>
										<option value="email">이메일</option>
										<option value="fullname">이름</option>
										<option value="phoneNumber">전화번호</option>
									</select>
								</div>
								<div class="form-group">
									<input class="au-input au-input--xl" type="text" name="search"
										placeholder="Search..." />
								</div>
								<button class="au-btn--submit" type="submit">
									<i class="zmdi zmdi-search"></i>
								</button>
							</form>


						</div>
					</div>
				</div>
			</header>
			<!-- END HEADER DESKTOP-->
			<!-- MAIN CONTENT-->
			<div class="main-content">
				<div class="section__content section__content--p30">
					<div class="container-fluid">

						<div class="row">
							<div class="col-lg-6">
								<!-- USER DATA-->

								<!-- END USER DATA-->
							</div>
							<div class="col-lg-6">
								<!-- TOP CAMPAIGN-->

								<!--  END TOP CAMPAIGN-->
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<!-- DATA TABLE -->
								<h3 class="title-5 m-b-35">User table</h3>



								<div class="table-responsive table-responsive-data2">
									<table class="table table-data2">
										<thead>
											<tr>
												<th>아이디</th>
												<th>이메일</th>
												<th>이름</th>
												<th>전화번호</th>
												<th>카카오여부</th>
												<th>권한</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="user" items="${users}">
												<tr class="spacer"></tr>
												<tr class="tr-shadow">
													<td><span id="username">${user.username}</span><input
														type="hidden" id="usernameInput" value="${user.username}"></td>
													<td><span class="block-email">${user.email}</span></td>
													<td>${user.fullname}</td>
													<td class="desc">${user.phoneNumber}</td>
													<td><span class="status--denied">${user.isKakao}</span>
													</td>
													
													<c:set var="level" value="${user.levelId}" />
													<c:if test="${ level == 1}">
													<td id="userLevel-${user.username}">일반유저</td>
													</c:if>
													<c:if test="${ level == 2}">
													<td id="userLevel-${user.username}">관리자</td>
													</c:if>
													<c:if test="${ level == 3}">
													<td id="userLevel-${user.username}">VIP</td>
													</c:if>
													
													<td>
													
													
														<div class="table-data-feature">


															<button class="item delete-user" data-toggle="tooltip"
																data-placement="top" title=""
																data-original-title="사용자삭제">
																<i class="zmdi zmdi-delete"></i>
															</button>
															<button class="item change-level" data-toggle="tooltip"
																data-placement="top"
																data-target="#changeLevelModal-${user.username}"
																title="관리자권한승급">
																<i class="zmdi zmdi-edit"></i>
															</button>

															<!-- Modal for changing level -->
															<div class="modal fade"
																id="changeLevelModal-${user.username}" tabindex="-1"
																role="dialog"
																aria-labelledby="changeLevelModalLabel-${user.username}"
																aria-hidden="true" data-backdrop="false">
																<div class="modal-dialog" role="document">
																	<div class="modal-content">
																		<div class="modal-header">
																			<h5 class="modal-title"
																				id="changeLevelModalLabel-${user.username}">Change
																				Level</h5>
																			<button type="button" class="close"
																				data-dismiss="modal" aria-label="Close">
																				<span aria-hidden="true">&times;</span>
																			</button>
																		</div>
																		<div class="modal-body">
																			<!-- Add your buttons with specific functions here -->
																			<button class="btn btn-primary"
																				onclick="changeUserLevel('${user.levelId}', '2', '${user.username}')">관리자
																				권한</button>

																			<button class="btn btn-primary"
																				onclick="changeUserLevel('${user.levelId}', '1', '${user.username}')">일반유저
																				권한</button>

																			<button class="btn btn-primary"
																				onclick="changeUserLevel('${user.levelId}', '3', '${user.username}')">VIP
																				권한</button>



																		</div>
																	</div>
																</div>
															</div>
											</c:forEach>

										</tbody>
									</table>
								</div>



								<!-- END DATA TABLE -->
							</div>
						</div>

						<div class="pagination" id="pagination"
							style="justify-content: center;">
							<c:if test="${page > 1}">
								<a href="?page=${page - 1}">이전</a>
							</c:if>

							<c:forEach begin="1" end="${totalPages}" var="pageNumber">
								<c:choose>
									<c:when test="${pageNumber == page}">
										<span>${pageNumber}</span>
									</c:when>
									<c:otherwise>
										<a href="?page=${pageNumber}">${pageNumber}</a>
									</c:otherwise>
								</c:choose>
							</c:forEach>

							<c:if test="${page < totalPages}">
								<a href="?page=${page + 1}">다음</a>
							</c:if>
						</div>

						<div class="row">
							<div class="col-md-12">
								<div class="copyright">
									<p>
										Copyright © 2018 Colorlib. All rights reserved. Template by <a
											href="https://colorlib.com">Colorlib</a>.
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>




	<script src='/js/admin/adminUserManagement.js'></script>
	<!-- Bootstrap JS-->
	<script src="/vendor/bootstrap-4.1/popper.min.js"></script>
	<script src="/vendor/bootstrap-4.1/bootstrap.min.js"></script>
	<!-- Vendor JS       -->
	<script src="/vendor/slick/slick.min.js">
		
	</script>
	<script src="/vendor/wow/wow.min.js"></script>
	<script src="/vendor/animsition/animsition.min.js"></script>
	<script
		src="/vendor/bootstrap-progressbar/bootstrap-progressbar.min.js">
		
	</script>
	<script src="/vendor/counter-up/jquery.waypoints.min.js"></script>
	<script src="/vendor/counter-up/jquery.counterup.min.js">
		
	</script>
	<script src="/vendor/circle-progress/circle-progress.min.js"></script>
	<script src="/vendor/perfect-scrollbar/perfect-scrollbar.js"></script>
	<script src="/vendor/chartjs/Chart.bundle.min.js"></script>
	<script src="/vendor/select2/select2.min.js">
		
	</script>

	<!-- Main JS-->
	<script src="/js/main.js"></script>

</body>
</html>