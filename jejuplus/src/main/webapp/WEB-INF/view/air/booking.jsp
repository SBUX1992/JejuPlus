<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/view/header.jsp" %>
<!-- css -->
<link rel="stylesheet" href="/css/air/air.css" />
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>
<!-- js -->
<script src="../../js/air/air.js"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<!-- main -->
<main>
	<div class="booking-container">
		<div class="mybooking">
			<div>
				<section class="nav">
					<p class="current">
						<span>1</span> 예약/결제
					</p>
					<p>
						<span>2</span> 주문완료
					</p>
				</section>
			</div>
			<div class="booking-info">
				<form id="paymentForm" action="/air/booking" method="post">
					<div class="left-info">
						<!-- flight-info start -->
						<!-- 가는 편 -->
	                    <div class="info-wrap">
	                        <div class="info-header">
	                            <p class="info-title">총 ${n1_itemList.size()} 개의 항공편이 있습니다</p>
	                            <div class="round-ticket">가는 편</div>
	                        </div>
	                        	<c:forEach var="n1_item" items="${n1_itemList}" varStatus="loop">
							    	<div class="info-body">
							        	<div class="flight-info-row">
							        		<div class="flight-info-column">
							                	<div class="flight-info-head">
							                    	<img src="../../images/air/jeju_air_logo.png" alt="jeju_logo" class="flight-logo" />
							                    	<p class="flight-brand">${n1_item.airlineNm}</p>
							                	</div>
							                	<div class="flight-info-body">
							                    	<div class="">
							                        	<div class="dep-block">
							                            	<p>${fn:substring(n1_item.depPlandTime, 8, 10)}:${fn:substring(n1_item.depPlandTime, 10, 12)}</p>
							                            	<p id="n1-depPlandTime" style="display:none;">${n1_item.depPlandTime}</p>
							                            	<div class="flight-time">
							                                	<span class="flight-time-info">${n1_item.flightTimeMinutes} 분 소요</span>
							                            	</div>
						                        		</div>
							                        	<p class="airport-name" id="n1-dep-airport">${n1_item.depAirportNm}</p>
							                    	</div>
							                    	<div style="margin-left: 35px">
								                        <div class="arr-block">
								                            <p>${fn:substring(n1_item.arrPlandTime, 8, 10)}:${fn:substring(n1_item.arrPlandTime, 10, 12)}</p>
								                            <p id="n1-arrPlandTime" style="display:none;">${n1_item.arrPlandTime}</p>
								                        </div>
							                        	<p class="airport-name" id="n1-arr-airport">${n1_item.arrAirportNm}</p>
							                    	</div>
							                	</div>
							            	</div>
								            <div class="price-block">
								                <span>${n1_item.economyCharge} 원</span>
								            </div>
								            <div class="n1-check-block">
										        <input type="radio" id="n1-check${loop.index}" name="n1-check" class="n1-check-input" />
										        <label for="n1-check${loop.index}" class="n1-check-air">선택</label>
										    </div>
							        	</div>
							    	</div>
								</c:forEach>
                    	</div>
                    	<!-- 오는 편 -->
                    	<div class="info-wrap">
	                        <div class="info-header">
	                            <p class="info-title">총 ${n2_itemList.size()} 개의 항공편이 있습니다</p>
	                            <div class="round-ticket">오는 편</div>
	                        </div>
	                        	<c:forEach var="n2_item" items="${n2_itemList}" varStatus="loop">
							    	<div class="info-body">
							        	<div class="flight-info-row">
							        		<div class="flight-info-column">
							                	<div class="flight-info-head">
							                    	<img src="../../images/air/jeju_air_logo.png" alt="jeju_logo" class="flight-logo" />
							                    	<p class="flight-brand">${n2_item.airlineNm}</p>
							                	</div>
							                	<div class="flight-info-body">
							                    	<div class="">
							                        	<div class="dep-block">
							                            	<p>${fn:substring(n2_item.depPlandTime, 8, 10)}:${fn:substring(n2_item.depPlandTime, 10, 12)}</p>
							                            	<p id="n2-depPlandTime" style="display:none;">${n2_item.depPlandTime}</p>
							                            	<div class="flight-time">
							                                	<span class="flight-time-info">${n2_item.flightTimeMinutes} 분 소요</span>
							                            	</div>
						                        		</div>
							                        	<p class="airport-name" id="n2-dep-airport">${n2_item.depAirportNm}</p>
							                    	</div>
							                    	<div style="margin-left: 35px">
								                        <div class="arr-block">
								                            <p>${fn:substring(n2_item.arrPlandTime, 8, 10)}:${fn:substring(n2_item.arrPlandTime, 10, 12)}</p>
								                            <p id="n2-arrPlandTime" style="display:none;">${n2_item.arrPlandTime}</p>
								                        </div>
							                        	<p class="airport-name" id="n2-arr-airport">${n2_item.arrAirportNm}</p>
							                    	</div>
							                	</div>
							            	</div>
								            <div class="price-block">
								                <span>${n2_item.economyCharge} 원</span>
								            </div>
								            <div class="n2-check-block">
										        <input type="radio" id="n2-check${loop.index}" name="n2-check" class="n2-check-input" />
										        <label for="n2-check${loop.index}" class="n2-check-air">선택</label>
										    </div>
							        	</div>
							    	</div>
								</c:forEach>
                    	</div>
	                    <!-- flight-info end -->
						<!-- 항공권 정보 start -->
						<div class="info-wrap">
							<div class="info-header">
								<p class="info-title">항공권 정보</p>
							</div>
							<div class="info-body">
								<div class="info-item">
									<div class="round-ticket">가는 편</div>
									<p class="round-ticket-content">
										기내수하물 규정이 아래와 같이 변경되니 참고 바랍니다. <br /> [기내/위탁 수하물 안내] <br />
										기내 : 삼면(가로 55cm/세로 20cm/높이 40cm)의 합이 115cm, 중량 10kg 이하인 수하물 /
										1개 <br /> 위탁 : 15kg 이하 / 1개(초과 시 KG당 2,000원) <br /> ★ 특가석(P)
										이용 시 주의사항 <br /> 단, 특가석(P)은 무료 위탁수하물 제공이 되지 않습니다.(유료 → 1kg 당
										2,000원)
									</p>
								</div>
								<div class="info-item">
									<div class="round-ticket">오는 편</div>
									<p class="round-ticket-content">
										기내수하물 규정이 아래와 같이 변경되니 참고 바랍니다. <br /> [기내/위탁 수하물 안내] <br />
										기내 : 삼면(가로 55cm/세로 20cm/높이 40cm)의 합이 115cm, 중량 10kg 이하인 수하물 /
										1개 <br /> 위탁 : 15kg 이하 / 1개(초과 시 KG당 2,000원) <br /> ★ 특가석(P)
										이용 시 주의사항 <br /> 단, 특가석(P)은 무료 위탁수하물 제공이 되지 않습니다.(유료 → 1kg 당
										2,000원)
									</p>
								</div>
							</div>
						</div>
						<!-- 항공권 정보 end -->
					</div>
					<div class="right-info">
						<!-- 항공권 취소 환불 정책 start -->
						<div class="info-wrap">
							<div class="info-header">
								<p class="info-title">항공권 취소 환불 정책</p>
							</div>
							<div class="info-body">
								<div class="info-item">
									<div class="round-ticket">가는 편</div>
									<p class="cancel-content">
										■ 취소수수료 규정 (예약일 : ~ 21. 10. 24) <br /> 출발 61일 전 : 일반석 /
										할인석(특가석) 2,000원 <br /> 출발 60일 ~ 31일 전 : 일반석 / 할인석(특가석) 4,000원
										<br /> 출발 30일 ~ 15일 전 : 일반석 / 할인석(특가석) 6,000원 <br /> 출발 14일 ~
										2일 전 : 일반석 / 할인석(특가석) 9,000원 <br /> 출발 1일 전 : 일반석 / 할인석(특가석)
										13,000원 <br /> 출발당일 이후 : 일반석 / 할인석(특가석) 14,000원 <br />
										<br /> ■ 취소수수료 규정 (예약일 : 21. 10. 25 ~) <br /> 구매 후 24시간 전 :
										취소수수료 없음 <br /> 구매 후 24시간 후 ~ 출발 61일 전 : 비즈니스석, 일반석 1,000원 /
										할인석(특가석) 2,000원 <br /> 출발 60일 ~ 31일 전 : 비즈니스석, 일반석 3,000원 /
										할인석(특가석) 4,000원 <br /> 출발 30일 ~ 15일 전 : 비즈니스석, 일반석 5,000원 /
										할인석(특가석) 6,000원 <br /> 출발 14일 ~ 2일 전 : 비즈니스석, 일반석 8,000원 /
										할인석(특가석) 9,000원 <br /> 출발 1일 전 : 비즈니스석, 일반석 12,000원 / 할인석(특가석)
										13,000원 <br /> 출발당일 이후 : 비즈니스석, 일반석 13,000원 / 할인석(특가석) 14,000원
										<br /> [취소수수료 규정 공통사항] <br /> - 사전 좌석지정(체크인)한 항공권은 취소 불가하니
										항공사로 좌석지정(체크인) 취소를 하신 후 취소진행을 해주시길 바랍니다. <br /> - 매일
										23:55~00:10 까지는 항공사 시스템 점검으로 이용이 제한될 수 있으니 예약취소 시 주의 바랍니다. <br />
										- 제주항공 콜센터를 통한 유선상으로 취소/변경 시에는 편도 1인 5,000원의 서비스 수수료가 부과됩니다. <br />
										■ 탑승권 키오스크(무인발급기) 발행 안내 <br /> 1. 시행일 : 2019년 11월 1일 (탑승권 발급
										기준) <br /> 2. 수수료 : 3,000원/1인 (키오스크/무인발급기 미이용 시) <br /> 3. 적용
										공항 : 국내선 공항 (광주, 무안공항 제외) <br /> 4. 대상 고객 : 모바일 탑승권이나 키오스크 이용이
										가능함에도 불구하고, 카운터에서 탑승권 발급을 희망 하는 고객 자세한 사항은 제주항공
										홈페이지(www.jejuair.net) 또는 제주항공 1599-1500(08:00~19:00) 으로 문의
										바랍니다. <br /> ■ (중요) 결항/비운항/스케쥴 변경으로 인한 예약취소 및 취소수수료 면제 절차 <br />
										STEP 1. 항공사로부터 결항/비운항/스케쥴 변경 안내 문자(알림톡) 접수 <br /> STEP 2. 항공권
										예약 여행사를 통한 유선상으로 접수(신청) '필수' <br /> STEP 3. 여행사에서 접수 후 취소수수료
										면제 및 전액환불 처리 <br /> (중요) 필히 항공권 예약 여행사를 통해 접수를 해서 처리해야 되며,
										예약내역(마이페이지)에서 직접 취소 시에는 규정된 수수료가 부과됩니다. <br /> (면제 접수 절차가 아닌
										직접취소 등으로 수수료 발생 후 추후 소급적용은 불가합니다.)
									</p>
								</div>
								<div class="info-item">
									<div class="round-ticket">오는 편</div>
									<p class="cancel-content">
										■ 취소수수료 규정 (예약일 : ~ 21. 10. 24) <br /> 출발 61일 전 : 일반석 /
										할인석(특가석) 2,000원 <br /> 출발 60일 ~ 31일 전 : 일반석 / 할인석(특가석) 4,000원
										<br /> 출발 30일 ~ 15일 전 : 일반석 / 할인석(특가석) 6,000원 <br /> 출발 14일 ~
										2일 전 : 일반석 / 할인석(특가석) 9,000원 <br /> 출발 1일 전 : 일반석 / 할인석(특가석)
										13,000원 <br /> 출발당일 이후 : 일반석 / 할인석(특가석) 14,000원 <br />
										<br /> ■ 취소수수료 규정 (예약일 : 21. 10. 25 ~) <br /> 구매 후 24시간 전 :
										취소수수료 없음 <br /> 구매 후 24시간 후 ~ 출발 61일 전 : 비즈니스석, 일반석 1,000원 /
										할인석(특가석) 2,000원 <br /> 출발 60일 ~ 31일 전 : 비즈니스석, 일반석 3,000원 /
										할인석(특가석) 4,000원 <br /> 출발 30일 ~ 15일 전 : 비즈니스석, 일반석 5,000원 /
										할인석(특가석) 6,000원 <br /> 출발 14일 ~ 2일 전 : 비즈니스석, 일반석 8,000원 /
										할인석(특가석) 9,000원 <br /> 출발 1일 전 : 비즈니스석, 일반석 12,000원 / 할인석(특가석)
										13,000원 <br /> 출발당일 이후 : 비즈니스석, 일반석 13,000원 / 할인석(특가석) 14,000원
										<br /> [취소수수료 규정 공통사항] <br /> - 사전 좌석지정(체크인)한 항공권은 취소 불가하니
										항공사로 좌석지정(체크인) 취소를 하신 후 취소진행을 해주시길 바랍니다. <br /> - 매일
										23:55~00:10 까지는 항공사 시스템 점검으로 이용이 제한될 수 있으니 예약취소 시 주의 바랍니다. <br />
										- 제주항공 콜센터를 통한 유선상으로 취소/변경 시에는 편도 1인 5,000원의 서비스 수수료가 부과됩니다. <br />
										■ 탑승권 키오스크(무인발급기) 발행 안내 <br /> 1. 시행일 : 2019년 11월 1일 (탑승권 발급
										기준) <br /> 2. 수수료 : 3,000원/1인 (키오스크/무인발급기 미이용 시) <br /> 3. 적용
										공항 : 국내선 공항 (광주, 무안공항 제외) <br /> 4. 대상 고객 : 모바일 탑승권이나 키오스크 이용이
										가능함에도 불구하고, 카운터에서 탑승권 발급을 희망 하는 고객 자세한 사항은 제주항공
										홈페이지(www.jejuair.net) 또는 제주항공 1599-1500(08:00~19:00) 으로 문의
										바랍니다. <br /> ■ (중요) 결항/비운항/스케쥴 변경으로 인한 예약취소 및 취소수수료 면제 절차 <br />
										STEP 1. 항공사로부터 결항/비운항/스케쥴 변경 안내 문자(알림톡) 접수 <br /> STEP 2. 항공권
										예약 여행사를 통한 유선상으로 접수(신청) '필수' <br /> STEP 3. 여행사에서 접수 후 취소수수료
										면제 및 전액환불 처리 <br /> (중요) 필히 항공권 예약 여행사를 통해 접수를 해서 처리해야 되며,
										예약내역(마이페이지)에서 직접 취소 시에는 규정된 수수료가 부과됩니다. <br /> (면제 접수 절차가 아닌
										직접취소 등으로 수수료 발생 후 추후 소급적용은 불가합니다.)
									</p>
								</div>
							</div>
						</div>
						<!-- 항공권 취소 환불 정책 end -->
						<!-- 가격 안내 start -->
						<div class="info-wrap">
							<div class="info-header">
								<p class="info-title">결제 정보</p>
							</div>
							<div class="info-body">
								<div class="pay-info">
									<div class="pay-detail">
										<div class="round-ticket">가는 편 항공권</div>
										<div class="pay-ditail-list">
											<div class="price-info">
												<p class="item-text">
													<span>탑승객</span> <span> X 1</span>
												</p>
												<p class="item-price">
													<p name="depPrice"> 원</p>
												</p>
											</div>
											<div class="price-info">
												<p class="item-text">
													<span>티켓 수수료</span>
												</p>
												<p class="item-price">
													<span>5,000 원</span>
												</p>
											</div>
										</div>
									</div>
									<div class="pay-detail">
										<div class="round-ticket">오는 편 항공권</div>
										<div class="pay-ditail-list">
											<div class="price-info">
												<p class="item-text">
													<span>탑승객</span>
													<span> X 1</span>
												</p>
												<p class="item-price">
													<p name="arrPrice"> 원</p>
												</p>
											</div>
											<div class="price-info">
												<p class="item-text">
													<span>티켓 수수료</span>
												</p>
												<p class="item-price">
													<span>5,000 원</span>
												</p>
											</div>
										</div>
									</div>
								</div>
								<div class="final-price-info">
									<p class="final-price">총 금액</p>
									<p class="final-price" name="totalPrice"> 원</p>
								</div>
								<div class="final-price-plus">항공료+유류할증료+세금 포함</div>
							</div>
							<div style="display:none;">
								<p name="n1DepAirport">n1출발공항</p>
								<p name="n1ArrAirport">n1도착공항</p>
								<p name="n2DepAirport">n2출발공항</p>
								<p name="n2ArrAirport">n2도착공항</p>
								<p name="airlineName">항공사 명</p>
								<p name="n1DepPlandTime">출발시간1</p>
								<p name="n1ArrPlandTime">도착시간1</p>
								<p name="n2DepPlandTime">출발시간2</p>
								<p name="n2ArrPlandTime">도착시간2</p>
							</div>
						</div>
						<!-- 가격 안내 end -->
						<!-- 구매자 정보 start -->
						<div class="info-wrap">
							<div class="info-header">
								<p class="info-title">구매자 정보</p>
							</div>
							<div class="info-body">
								<div class="customer-info-row">
									<div class="customer-info">
										<label for="info-name">이름 (한글)</label>
										<input type="text" class="customer-info-input" name="customer-name" placeholder="홍길동" />
									</div>
								</div>
								<div class="customer-info-row">
									<div class="customer-info">
										<label for="info-name">전화번호</label>
										<input type="text" class="customer-info-input" name="customer-phone" placeholder="010-1234-5678"/>
									</div>
									<div class="customer-info">
										<label for="info-name">이메일</label>
										<input type="text" class="customer-info-input" name="customer-email" placeholder="example@example.com"/>
									</div>
								</div>
							</div>
						</div>
						<!-- 구매자 정보 end -->
						<!-- 탑승객 정보 start -->
						<div class="info-wrap">
							<div class="info-header">
								<p class="info-title">탑승객 1</p>
							</div>
							<div class="info-body">
								<div class="same-info">
									<label>
										<input type="radio" name="info" id="same">
										<span>구매자와 동일</span>
									</label>
									<label>
										<input type="radio" name="info" id="new" checked>
										<span>직접 입력</span>
									</div>
								</label>
								<div class="customer-info-row">
									<div class="customer-info">
										<label for="info-name">이름 (한글)</label>
										<input type="text" name="username" class="customer-info-input" placeholder="홍길동" />
									</div>
								</div>
								<div class="customer-info-row">
									<div class="customer-info">
										<label for="info-name">전화번호</label>
										<input type="text" name="phone" class="customer-info-input" placeholder="010-1234-5678"/>
									</div>
									<div class="customer-info">
										<label for="info-name">이메일</label>
										<input type="text" name="email" class="customer-info-input" placeholder="example@example.com"/>
									</div>
								</div>
							</div>
						</div>
						<!-- 탑승객 정보 end -->
						<!-- 결제수단 start -->
						<div class="info-wrap">
							<div class="info-header">
								<p class="info-title">결제 수단</p>
							</div>
							<div class="info-body">
								<div class="orderBox">
									<div class="paymentList">
										<input type="radio" id="chk1" name="payment" value="1" />
										<label for="chk1">카드결제</label>
										<input type="radio" id="chk2" name="payment" value="2" />
										<label for="chk2">실시간계좌이체</label>
										<input type="radio" id="chk3" name="payment" value="3" />
										<label for="chk3">무통장입금</label>
										<input type="radio" id="chk4" name="payment" value="4" />
										<label for="chk4">휴대폰결제</label>
										<input type="radio" id="chk5" name="payment" value="5" checked="checked" />
										<label for="chk5">카카오페이</label>
										<input type="radio" id="chk6" name="payment" value="6" />
										<label for="chk6">네이버페이</label>
									</div>
								</div>
							</div>
							<button type="button" class="btnOrder" onclick="requestPay()">결제하기</button>
						</div>
						<!-- 결제수단 end -->
					</div>
				</div>
			</form>
		</div>
	</div>
</main>
<script>

/* Kakao Pay start */
var IMP = window.IMP;
IMP.init("imp88272048");

// Create and set 'pg_tid'
var pgTid = "";

function requestPay() {
    // 가격을 불러와서 표시
    const totalPriceText = $('p[name=totalPrice]').text();
    const totalPrice = parseInt(totalPriceText.replace(/[^\d]/g, '')) || 0;

    // 선택 시 데이터 수집
    const airlineName = $('p[name=airlineName]').text();
    const n1DepAirport = $('p[name=n1DepAirport]').text();
    const n1ArrAirport = $('p[name=n1ArrAirport]').text();
    const n2DepAirport = $('p[name=n2DepAirport]').text();
    const n2ArrAirport = $('p[name=n2ArrAirport]').text();
    const n1DepPlandTime = $('p[name=n1DepPlandTime]').text();
    const n1ArrPlandTime = $('p[name=n1ArrPlandTime]').text();
    const n2DepPlandTime = $('p[name=n2DepPlandTime]').text();
    const n2ArrPlandTime = $('p[name=n2ArrPlandTime]').text();
    
    const depPrice = parseInt($('p[name=depPrice]').text().replace(/[^\d]/g, '')) || 0;
	const arrPrice = parseInt($('p[name=arrPrice]').text().replace(/[^\d]/g, '')) || 0;
    
    IMP.request_pay({
        pg: "kakaopay",
        pay_method: "card",
        name: "Jeju Plus",
        amount: totalPrice, // 수정된 부분
        pg_tid: pgTid,
        // 추가 데이터
        airlineName: airlineName,
        n1DepAirport: n1DepAirport,
        n1ArrAirport: n1ArrAirport,
        n2DepAirport: n2DepAirport,
        n2ArrAirport: n2ArrAirport,
        n1DepPlandTime: n1DepPlandTime,
        n1ArrPlandTime: n1ArrPlandTime,
        n2DepPlandTime: n2DepPlandTime,
        n2ArrPlandTime: n2ArrPlandTime,
        depPrice: depPrice,
        arrPrice: arrPrice,
        
    }, function(rsp) {
        console.log("Payment rsp: " + JSON.stringify(rsp));

        if (rsp.success) {
            jQuery.ajax({
                url: "/air/booking",
                type: 'POST',
                dataType: 'json',
                data: {
                    imp_uid: rsp.imp_uid,
                    pg_tid: rsp.pg_tid, // add pg_tid
                    merchant_uid: rsp.merchant_uid,
                 	// 추가 데이터
                    airlineName: airlineName,
                    n1DepAirport: n1DepAirport,
                    n1ArrAirport: n1ArrAirport,
                    n2DepAirport: n2DepAirport,
                    n2ArrAirport: n2ArrAirport,
                    n1DepPlandTime: n1DepPlandTime,
                    n1ArrPlandTime: n1ArrPlandTime,
                    n2DepPlandTime: n2DepPlandTime,
                    n2ArrPlandTime: n2ArrPlandTime,
                    depPrice: depPrice,
                    arrPrice: arrPrice,
                }
            }).done(function(data, textStatus, xhr) {
                console.log("result OK " + data.name);
                alert(data);
                window.location.href = "http://localhost:80/air/bookingcomplete";
            }).fail(function(error) {
                console.log("Failure");
                alert("Failure: " + error.responseText);
            });
        } else {
            var msg = 'Payment failed.';
            msg += '\nError details: ' + rsp.error_msg;
            alert(msg);
        }
    });
}
/* 카카오페이 end */
/*  */

	
</script>
<%@ include file="/WEB-INF/view/footer.jsp"%>