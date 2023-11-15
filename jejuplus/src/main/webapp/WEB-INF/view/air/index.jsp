<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/header.jsp" %>
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>
<style>
*{
text-decoration: none;
    color: black;
}
</style>
<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
	
<!-- air css -->
<link rel="stylesheet" href="../../css/air/air.css" />
<!-- js -->
<script src="../../js/air/air.js"></script>

       <!-- main -->
        <main>
        
            <!-- 항공 스케줄 선택 start -->
            
            <section class="flight">
                <div class="flight-container">
	                <form action="/air/index" method="post">
	                    <h4>항공권</h4>
	                    <div class="flight-type">
	                        <label for="round-trip"><input type="radio" name="flight-type" id="round-trip" checked/>왕복</label>
	                        <label for="oneway"><input type="radio" name="flight-type" id="oneway" />편도</label>
	                    </div>
	                    <div class="flight-search">
	                        <!-- dep -->
	                        <div class="dep-name">
	                            <label for="dep-name">출발지</label>
	                            <div class="dep-search">
	                            	<select class="dep-input" name="depAirportNm">
	                            		<option>선택</option>
	                            		<option value="NAARKPK">김해</option>
										<option value="NAARKPC">제주</option>
										<option value="NAARKSS">김포</option>
<!-- 										<option value="NAARKJB">무안</option> -->
<!-- 										<option value="NAARKJJ">광주</option> -->
<!-- 										<option value="NAARKJK">군산</option> -->
<!-- 										<option value="NAARKJY">여수</option> -->
<!-- 										<option value="NAARKNW">원주</option> -->
<!-- 										<option value="NAARKNY">양양</option> -->
<!-- 										<option value="NAARKPS">사천</option> -->
<!-- 										<option value="NAARKPU">울산</option> -->
<!-- 										<option value="NAARKSI">인천</option> -->
<!-- 										<option value="NAARKTH">포항</option> -->
<!-- 										<option value="NAARKTN">대구</option> -->
<!-- 										<option value="NAARKTU">청주</option> -->
	                            	</select>
	                            </div>
	                        </div>
	                        <!-- dep end -->
	
	                        <!-- arr start -->
	                        <div class="arr-name">
	                            <label for="arr-name">도착지</label>
	                            <div class="arr-search">
	                            	<select class="arr-input" name="arrAirportNm">
	                            		<option>선택</option>
	                            		<option value="NAARKPK">김해</option>
										<option value="NAARKPC">제주</option>
										<option value="NAARKSS">김포</option>
<!-- 										<option value="NAARKJB">무안</option> -->
<!-- 										<option value="NAARKJJ">광주</option> -->
<!-- 										<option value="NAARKJK">군산</option> -->
<!-- 										<option value="NAARKJY">여수</option> -->
<!-- 										<option value="NAARKNW">원주</option> -->
<!-- 										<option value="NAARKNY">양양</option> -->
<!-- 										<option value="NAARKPS">사천</option> -->
<!-- 										<option value="NAARKPU">울산</option> -->
<!-- 										<option value="NAARKSI">인천</option> -->
<!-- 										<option value="NAARKTH">포항</option> -->
<!-- 										<option value="NAARKTN">대구</option> -->
<!-- 										<option value="NAARKTU">청주</option> -->
	                            	</select>
	                            </div>
	                        </div>
	                        <!-- arr end -->
	
	                        <!-- customer start -->
							<div class="customer-container">
							    <label for="">탑승객</label>
							    <div class="customer-option">
							        <span>인원</span>
							        <div class="passenger-selector">
							            <a class="cus-minus-adult">-</a>
							            <span class="passengerCountAdult" name="passengerCount">1</span>
							            <a class="cus-plus-adult">+</a>
							        </div>
							    </div>
							</div>
	                        <!-- customer end -->
	                    </div>
	                    
	                    <!-- 날짜 선택 -->
	                    <div class="calnender-container px-1 px-sm-5 mx-auto">
						    <div class="flex-row d-flex justify-content-center">
						        <div class="col-xl-5 col-lg-6 col-11 px-1">
						            <div class="input-group-m1">
						                <label class="calender-label">가는날 : </label>
						                <input type="text" name="depPlandTime" class="calender-input" readonly />
						            </div>
						        </div>
						        <div class="col-xl-5 col-lg-6 col-11 px-1">
						            <div class="input-group-m2">
						                <label class="calender-label">오는날 : </label>
						                <input type="text" name="arrPlandTime" class="calender-input" readonly />
						            </div>
						        </div>
						    </div>
						</div>
	                    <!-- 예약하기 버튼 -->
	                    <div class="flight-find">
	                        <button type="submit" class="flight-find-btn" onclick="checkFlightSelection()">예약하기</button>
	                    </div>
	                    <!-- 예약하기 버튼 -->
                    </form>
                </div>
            </section>
           
            <!-- 항공 스케줄 선택 end -->

            <!-- 공지사항 start -->
            <!-- 
            <section class="air_notice">
                <h2>항공권 공지사항</h2>
                <div class="more_view">
                    <a href="#">더 보기</a>
                </div>
                <div class="notice_list">
                    <table>
                        <tr>
                            <td class="notice-title"><a href="#">[공지] 해외결제 피싱문자 주의!!</a></td>
                            <td class="notice-date">2023-10-24</td>
                        </tr>
                        <tr>
                            <td class="notice-title"><a href="#">[공지] 해외결제 피싱문자 주의!!</a></td>
                            <td class="notice-date">2023-10-24</td>
                        </tr>
                        <tr>
                            <td class="notice-title"><a href="#">[공지] 해외결제 피싱문자 주의!!</a></td>
                            <td class="notice-date">2023-10-24</td>
                        </tr>
                    </table>
                </div>
            </section>
             -->
            <!-- 공지사항 end -->
        </main>
<%@ include file="/WEB-INF/view/footer.jsp" %>