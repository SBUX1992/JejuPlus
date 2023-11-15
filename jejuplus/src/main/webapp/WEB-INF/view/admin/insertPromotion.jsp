<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/admin/layout/header.jsp"%>


		<!-- MENU SIDEBAR-->
		<aside class="menu-sidebar d-none d-lg-block">
			<div class="logo">
				<a href="#"> <img src="/images/icon/logo.png" alt="Cool Admin">
				</a>
			</div>
			<div class="menu-sidebar__content js-scrollbar1 ps"
				style="background-color: #E2E2E2;">
				<nav class="navbar-sidebar">
					<ul class="list-unstyled navbar__list">
					<li class="active"><a href="/main">
								<i class="fas fa-table"></i>Main
						</a></li>

						<li class="active"><a href="/admin/adminUserManagement">
								<i class="fas fa-table"></i>User
						</a></li>
						<li class="active"><a href="/admin/adminPromotionManagement">
								<i class="fas fa-table"></i>Promotion
						</a></li>
						<li class="active"><a href="/admin/insertPromotion"> <i
								class="fas fa-table"></i>Promotion Write
						</a></li>

					</ul>
				</nav>
				<div class="ps__rail-x" style="left: 0px; bottom: 0px;">
					<div class="ps__thumb-x" tabindex="0"
						style="left: 0px; width: 0px;"></div>
				</div>
				<div class="ps__rail-y" style="top: 0px; right: 0px;">
					<div class="ps__thumb-y" tabindex="0"
						style="top: 0px; height: 0px;"></div>
				</div>
			</div>
		</aside>
		<!-- END MENU SIDEBAR-->

		<!-- PAGE CONTAINER-->
		<div class="page-container">
			<!-- HEADER DESKTOP-->
			<header class="header-desktop">
				<div class="section__content section__content--p30">
					<div class="container-fluid">
						<div class="header-wrap">

							<h2>광고 작성</h2>
						</div>
					</div>
				</div>
			</header>
			<!-- END HEADER DESKTOP-->
			<!-- MAIN CONTENT-->
			<div class="main-content" style="background-color: white;">
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
								<h3 class="title-5 m-b-35">Writer Promation</h3>

								<div>

									<form action="/admin/insertPromotion" method="post"
										enctype="multipart/form-data" id="myForm">
										<div class="form-group">
											<label for="title">홍보할곳:</label> <input type="text"
												name="title" id="titleField" class="form-control"
												placeholder="제목을 입력하세요">
										</div>
										<div class="form-group">
											<label for="introduce">소개:</label> <input type="text"
												name="introduce" id="introduceField" class="form-control"
												placeholder="간단한 소개글을 입력하세요(45자 이내)">
										</div>
										<div class="form-group">
											<label for="content">본문:</label>
											<textarea id="content" name="content" rows="6"
												class="form-control" placeholder="내용을 입력하세요"></textarea>
										</div>
										<div class="form-group">
											<label for="images">대표사진 업로드:</label> <input type="file"
												name="images" id="imageField" multiple
												class="form-control-file">
										</div>
										<div class="form-group">
											<label for="images">이미지 업로드:</label> <input type="file"
												name="images" id="imageField" multiple
												class="form-control-file">
										</div>
										<div class="form-group">
											<label for="images">이미지 업로드:</label> <input type="file"
												name="images" id="imageField" multiple
												class="form-control-file">
										</div>
										<div class="button-container">
											<button type="submit" class="btn btn-primary">올리기</button>
										</div>
									</form>





								</div>





							</div>
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

	<script>
document.querySelector('#myForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const formData = new FormData(this);
	var introduce = document.getElementById('introduceField').value;
	var title = document.getElementById('titleField').value;
	
    fetch('/admin/insertPromotion', {
        method: 'POST',
        body: formData,
    })
    .then(response => {
    	if(introduce.length > 45) {
    		alert("소개글이 45글자를 초과했습니다.");
    		return;
    	} else if(title.length > 45) {
    		alert("장소를 입력하는 곳은 50글자가 최대입니다.");
    		return;
    	} else {
        if (response.ok) {
            // Form submission was successful
            console.log(introduce);
            alert('광고가 업로드 되었습니다.');
            window.location.href='/admin/insertPromotion';
        } else {
            // Form submission failed
            alert('작성하지 않은 곳이 있는지 다시 한번 확인해주세요');
        }
    	}
    })
    .catch(error => {
        console.error('Error:', error);
    });
});


</script>


</body>
</html>