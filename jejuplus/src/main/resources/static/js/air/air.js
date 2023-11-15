/**
 * 2023-10-26
 * 강중현
 */


/* main 달력 start */
$(document).ready(function() {
	var depPlandTime_input = $('input[name="depPlandTime"]');
	var arrPlandTime_input = $('input[name="arrPlandTime"]');

	depPlandTime_input.daterangepicker({
		singleDatePicker: true,
		startDate: moment(),
		opens: 'center',
		autoApply: true,
		locale: {
			format: 'YYYY-MM-DD',  // "yyyy-mm-dd" 형식으로 설정
		},
		minDate: moment(), // 이전 날짜 선택 방지
	});

	arrPlandTime_input.daterangepicker({
		singleDatePicker: true,
		startDate: moment(),
		opens: 'center',
		autoApply: true,
		locale: {
			format: 'YYYY-MM-DD',  // "yyyy-mm-dd" 형식으로 설정
		},
		minDate: moment(), // 이전 날짜 선택 방지
	});

	// 가는날 선택 시 오는날과 연결
	depPlandTime_input.on('apply.daterangepicker', function(ev, picker) {
		arrPlandTime_input.data('daterangepicker').setStartDate(picker.startDate);
		arrPlandTime_input.data('daterangepicker').setEndDate(picker.startDate);
	});
});
/* main 달력 end */

/* 탑승객 숫자 start */
document.addEventListener('DOMContentLoaded', function() {
	// 승객과 관련된 요소들을 선택
	const decreaseButtonsAdult = document.querySelectorAll('.cus-minus-adult');
	const increaseButtonsAdult = document.querySelectorAll('.cus-plus-adult');
	const passengerCountElementsAdult = document.querySelectorAll('.passengerCountAdult');

	// 초기 승객 수를 1로 설정
	let passengerCountAdult = 1;

	// 초기 승객 수를 각 요소에 업데이트
	passengerCountElementsAdult.forEach((element) => {
		element.textContent = passengerCountAdult;
	});

	// 승객 감소 버튼 클릭 이벤트
	decreaseButtonsAdult.forEach((button, index) => {
		button.addEventListener('click', () => {
			if (passengerCountAdult > 1) {
				passengerCountAdult--;
				passengerCountElementsAdult[index].textContent = passengerCountAdult;
				updateTotalPassengerCount(); // 전체 승객 수 업데이트
			}
		});
	});

	// 승객 증가 버튼 클릭 이벤트
	increaseButtonsAdult.forEach((button, index) => {
		button.addEventListener('click', () => {
			if (getTotalPassengerCount() < 6) {
				passengerCountAdult++;
				passengerCountElementsAdult[index].textContent = passengerCountAdult;
				updateTotalPassengerCount(); // 전체 승객 수 업데이트
			} else {
				alert('총 승객 수는 6명을 초과할 수 없습니다.');
			}
		});
	});

	// 전체 승객 수 업데이트 함수
	function updateTotalPassengerCount() {
		const totalPassengerCount = getTotalPassengerCount();
		if (totalPassengerCount < 1) {
			alert('적어도 1명의 승객을 선택해야 합니다.');
		}
		// 여기서 필요한 작업을 수행하세요.
	}

	// 전체 승객 수 반환 함수
	function getTotalPassengerCount() {
		return passengerCountAdult;
	}
});

/* 탑승객 숫자 end */

/* 예약하기 버튼 */
function checkFlightSelection() {
	var depAirport = $('select[name="depAirportNm"]').val();
	var arrAirport = $('select[name="arrAirportNm"]').val();

	if (depAirport === '선택' || arrAirport === '선택') {
		alert('출발지와 도착지를 선택해주세요.');
	} else if (depAirport === arrAirport) {
		alert('출발지와 도착지가 동일합니다. 예약할 수 없습니다.');
	} else {
		// 출발지와 도착지가 유효하며 동일하지 않은 경우 예약 프로세스를 진행할 수 있습니다.
		// 여기에 필요한 예약 프로세스 코드를 추가하세요.
		// 예를 들어, 서버로 데이터를 전송하거나 예약 페이지로 이동하는 등의 작업을 수행할 수 있습니다.
	}
}

/* 결제버튼 start */
// 카카오페이 외 라디오 버튼 클릭 안됨
//$(document).ready(function() {
//	// 결제 수단 라디오 버튼이 변경될 때 이벤트를 설정합니다.
//	$('input[name="payment"]').on('change', function() {
//		var selectedPayment = $(this).val();
//
//		// 만약 선택한 결제 수단이 '카카오페이'가 아닌 경우 알림을 표시
//		if (selectedPayment !== '5') { // '5'는 카카오페이의 value 값
//			alert('선택한 결제 수단은 현재 사용 불가능합니다. 다른 결제 수단을 선택해 주세요.');
//			// 라디오 버튼을 초기 상태로 되돌림
//			$('input[name="payment"][value="5"]').prop('checked', true); // 카카오페이를 선택한 상태로 되돌립니다.
//		}
//	});
//
//	// 결제하기 버튼 클릭 시 폼을 제출합니다.
//	//$('.btnOrder').on('click', function() {
//	// 여기에서 폼을 제출하는 로직을 작성하면 됩니다.
//	// 예를 들어, 폼의 id가 'paymentForm'라면:
//	// $('#paymentForm').submit();
//	//});
//});

// 라디오 버튼 클릭은 되지만 결제하기 누르면 경고창 발생
$(document).ready(function() {
	// 결제하기 버튼 클릭 시 알림창을 표시합니다.
	$('.btnOrder').on('click', function() {
		var selectedPayment = $('input[name="payment"]:checked').val();

		// 만약 선택한 결제 수단이 '카카오페이'가 아닌 경우 알림을 표시합니다.
		if (selectedPayment !== '5') { // '5'는 카카오페이의 value 값입니다.
			alert('선택한 결제 수단은 현재 사용 불가능합니다. 다른 결제 수단을 선택해 주세요.');
		} else {
			// 여기에서 폼을 제출하는 로직을 작성하면 됩니다.
			// 예를 들어, 폼의 id가 'paymentForm'라면:
			// $('#paymentForm').submit();
		}
	});
});
/* 결제버튼 end */

/* 왕복 편도 라디오 버튼 start */
$(document).ready(function() {
	// 왕복 라디오 버튼 선택 시
	$('#round-trip').on('change', function() {
		if ($(this).is(':checked')) {
			// 왕복 선택 시 가는날과 오는날 모두 활성화
			$('input[name="depPlandTime"]').prop('disabled', false);
			$('input[name="arrPlandTime"]').prop('disabled', false);
			// 오는날 레이블과 입력 필드 보이기
			$('.input-group-m2').show();
		}
	});

	// 편도 라디오 버튼 선택 시
	$('#oneway').on('change', function() {
		if ($(this).is(':checked')) {
			// 편도 선택 시 가는날만 활성화, 오는날 비활성화
			$('input[name="depPlandTime"]').prop('disabled', false);
			$('input[name="arrPlandTime"]').prop('disabled', true);
			// 오는날 레이블과 입력 필드 숨기기
			$('.input-group-m2').hide();
		}
	});
});

/* 왕복 편도 라디오 버튼 end */

/* 구매자와 동일 start */
$(document).ready(function() {
	// "구매자와 동일" 라디오 버튼 클릭 시
	$('#same').click(function() {
		if (this.checked) {
			const customerName = $('input[name=customer-name]').val();
			const customerPhone = $('input[name=customer-phone]').val();
			const customerEmail = $('input[name=customer-email]').val();

			$('input[name=username]').val(customerName);
			$('input[name=phone]').val(customerPhone);
			$('input[name=email]').val(customerEmail);
		}
	});

	$('#new').click(function() {
		if (this.checked) {
			// 탑승객 1 정보 초기화
			$('input[name=username]').val("");
			$('input[name=phone]').val("");
			$('input[name=email]').val("");
		}
	});
});
/* 구매자와 동일 end */
/* 결제 정보 가격 start */
$(document).ready(function() {
	// "가는 편" 라디오 버튼 및 "오는 편" 라디오 버튼 클릭 시
	$('input[name=n1-check], input[name=n2-check]').click(function() {
		if (this.checked) {
			// 선택된 가격을 가져와서 표시
			const selectedPrice = $(this).closest('.info-body').find('.price-block span').text();
			let fee = 0;

			const airlineName = $(this).closest('.info-body').find('.flight-brand').text();
			const n1DepAirport = $(this).closest('.info-body').find('#n1-dep-airport').text();
			const n1ArrAirport = $(this).closest('.info-body').find('#n1-arr-airport').text();
			const n2DepAirport = $(this).closest('.info-body').find('#n2-dep-airport').text();
			const n2ArrAirport = $(this).closest('.info-body').find('#n2-arr-airport').text();
			const n1DepPlandTime = $(this).closest('.info-body').find('#n1-depPlandTime').text();
			const n1ArrPlandTime = $(this).closest('.info-body').find('#n1-arrPlandTime').text();
			const n2DepPlandTime = $(this).closest('.info-body').find('#n2-depPlandTime').text();
			const n2ArrPlandTime = $(this).closest('.info-body').find('#n2-arrPlandTime').text();

			//            console.log("Airline Name:", airlineName);
			//            console.log("n1DepAirport:", n1DepAirport);
			//            console.log("n1ArrAirport:", n1ArrAirport);
			//            console.log("n2DepAirport:", n2DepAirport);
			//            console.log("n2ArrAirport:", n2ArrAirport);
			//
			//			$('p[name=n1DepAirport]').text(n1DepAirport);

			// 가는 편인지 오는 편인지에 따라 적절한 위치에 가격을 표시
			if ($(this).attr('name') === 'n1-check') {
				fee = 5000; // 가는 편 추가할 수수료 수정
				$('p[name=depPrice]').text(selectedPrice);
				$('p[name=n1DepAirport]').text(n1DepAirport);
				$('p[name=n1ArrAirport]').text(n1ArrAirport);
				$('p[name=n1DepPlandTime]').text(n1DepPlandTime);
				$('p[name=n1ArrPlandTime]').text(n1ArrPlandTime);
			} else if ($(this).attr('name') === 'n2-check') {
				fee = 10000; // 오는 편 추가할 수수료 수정
				$('p[name=arrPrice]').text(selectedPrice);
				$('p[name=n2DepAirport]').text(n2DepAirport);
				$('p[name=n2ArrAirport]').text(n2ArrAirport);
				$('p[name=airlineName]').text(airlineName);
				$('p[name=n2DepPlandTime]').text(n2DepPlandTime);
				$('p[name=n2ArrPlandTime]').text(n2ArrPlandTime);
			}

			// 가는 편 및 오는 편의 총 가격 계산
			const depPrice = parseInt($('p[name=depPrice]').text().replace(/[^\d]/g, '')) || 0;
			const arrPrice = parseInt($('p[name=arrPrice]').text().replace(/[^\d]/g, '')) || 0;
			const totalPrice = depPrice + arrPrice + fee;

			// 총 가격을 표시
			$('p[name=totalPrice]').text(totalPrice.toLocaleString() + ' 원');
		}
	});

	// "결제하기" 버튼 클릭 시
	$('.btnOrder').click(function() {
		// 결제 함수 호출
		requestPay();

	});
});
/* 결제 정보 가격 end */
/*  */

