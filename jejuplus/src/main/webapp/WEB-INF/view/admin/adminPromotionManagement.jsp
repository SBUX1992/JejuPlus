<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/admin/layout/header.jsp"%>



<!-- PAGE CONTAINER-->
<div class="page-container">
	<!-- HEADER DESKTOP-->
	<header class="header-desktop">
		<div class="section__content section__content--p30">
			<div class="container-fluid">
				<div class="header-wrap">
					<form class="form-header" action="/admin/adminPromotionManagement"
						method="GET">
						<div class="form-group">
							<input class="au-input au-input--xl" type="text" name="search"
								placeholder="업체이름을 입력해주세요" />
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
					<div class="col-md-12">
						<!-- DATA TABLE -->
						<h3 class="title-5 m-b-35">Promotion table</h3>

						<div class="table-responsive table-responsive-data2">
							<table class="table table-data2">
								<thead>
									<tr>
										<th>사진</th>
										<th>이름</th>
										<th>간단한 소개</th>
										<th>시작날짜</th>
										<th>종료날짜</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="promotion" items="${promotions}">

										<tr>
											<!-- Display Images for the Current Promotion -->
											<td><img src="${promotion.imageUrl}"
												alt="Promotion Image" class="img-fluid"
												style="max-width: 100px;"></td>

											<!-- Other Promotion Details -->
											<td>${promotion.title}</td>
											<td>${promotion.introduce}</td>
											<td>${promotion.startDate}</td>
											<td>${promotion.endDate}</td>
											<td><input type="hidden" class="promotionIdInput"
												value="${promotion.promotionId}">
												<div class="table-data-feature">
												<button class="item extension-promotion"
														 data-toggle="tooltip"
														data-placement="top" title="연장하기">
														<i class="zmdi zmdi-money-box"></i>
													</button>
												<button class="item update-promotion"
														 data-toggle="tooltip"
														data-placement="top" title="수정하기">
														<i class="zmdi zmdi-edit"></i>
													</button>
													<button class="item delete-promotion" data-toggle="tooltip"
														data-placement="top" title="삭제하기" data-original-title="업체삭제">
														<i class="zmdi zmdi-delete"></i>
													</button>
													<button class="item detail-promotion"
														 data-toggle="tooltip"
														data-placement="top" title="상세보기">
														<i class="zmdi zmdi-more"></i>
													</button>
												
												</div>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="pagination" id="pagination"
		style="justify-content: center;">
		<c:if test="${page > 1}">
			<a href="?page=1">처음</a>
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
			<a href="?page=${totalPages}">마지막</a>
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
<script>
$(document).ready(function() {
    // 삭제
    $(".delete-promotion").on("click", function() {
        var $row = $(this).closest("tr"); 
        var promotionId = $row.find(".promotionIdInput").val(); 

        if (confirm("삭제하시겠습니까?")) {
            $.ajax({
                type: 'POST',
                url: '/admin/adminPromotionDelete/' + promotionId,
                data: {
                    "promotionId": promotionId
                },
                dataType: 'json',
                success: function(data) {
                    if (data && data.result === "success") {
                        alert("업체 광고 정보가 삭제되었습니다.");
                        $row.remove(); 
                    } else {
                        alert("서버에서 빈 응답이 돌아왔습니다. 삭제에 실패했습니다.");
                    }
                },
                error: function(xhr) {
                    alert("삭제에 실패했습니다.");
                }
            });
        } else {
            // 사용자가 취소한 경우 아무 작업도 필요하지 않습니다.
        }
    });
    
 // 상세보기
    $(".detail-promotion").on("click", function() {
        var $row = $(this).closest("tr");
        var promotionId = $(this).closest("tr").find(".promotionIdInput").val();
		console.log(promotionId);
        if (confirm("상세보기로 이동하시겠습니까?")) {
            window.location.href = '/user/promotionDetail/' + promotionId;
        } else {
            // 사용자가 취소한 경우 아무 작업도 필요하지 않습니다.
        }
    });
 
 // 수정
    $(".update-promotion").on("click", function() {
        var $row = $(this).closest("tr");
        var promotionId = $(this).closest("tr").find(".promotionIdInput").val();
		console.log(promotionId);
        if (confirm("광고를 수정하러 이동하시겠습니까?")) {
            window.location.href = '/admin/updatePromotion/' + promotionId;
        } else {
            // 사용자가 취소한 경우 아무 작업도 필요하지 않습니다.
        }
    }); 
 
 	// 연장
    $(".extension-promotion").on("click", function() {

        var promotionId = $(this).closest("tr").find(".promotionIdInput").val();
		console.log(promotionId);
		 if (confirm("한달 연장하시겠습니까?")) {
	            $.ajax({
	                type: 'POST',
	                url: '/admin/updatePromotionEndDate/' + promotionId,
	                data: {
	                    "promotionId": promotionId
	                },
	                dataType: 'json',
	                success: function(data) {
	                    if (data && data.result === "success") {
	                        alert("광고가 한달 연장 되었습니다.");
	                        location.reload();
	                    } else {
	                        alert("서버에서 빈 응답이 돌아왔습니다. 연장에 실패했습니다.");
	                    }
	                },
	                error: function(xhr) {
	                    alert("연장에 실패했습니다.");
	                }
	            });
	        } else {
	            // 사용자가 취소한 경우 아무 작업도 필요하지 않습니다.
	        }      
    });
 
});

</script>






<!-- Bootstrap JS-->
<script src="/vendor/bootstrap-4.1/popper.min.js"></script>
<script src="/vendor/bootstrap-4.1/bootstrap.min.js"></script>
<!-- Vendor JS       -->
<script src="/vendor/slick/slick.min.js">
		
	</script>
<script src="/vendor/wow/wow.min.js"></script>
<script src="/vendor/animsition/animsition.min.js"></script>
<script src="/vendor/bootstrap-progressbar/bootstrap-progressbar.min.js">
		
	</script>
<script src="/vendor/counter-up/jquery.waypoints.min.js"></script>
<script src="/vendor/counter-up/jquery.counterup.min.js">
		
	</script>
<script src="/vendor/circle-progress/circle-progress.min.js"></script>
<script src="/vendor/perfect-scrollbar/perfect-scrollbar.js"></script>
<script src="/vendor/chartjs/Chart.bundle.min.js"></script>
<script src="/vendor/select2/select2.min.js">
    </script>



</body>
</html>