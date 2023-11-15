

$('#sche-calendar').daterangepicker({
	"locale": {
		"format": 'YYYY-MM-DD',
		"applyLabel": "확인",
		"cancelLabel": "취소",
		"daysOfWeek": ["일", "월", "화", "수", "목", "금", "토"],
		"monthNames": ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
	},

}, function(start, end) {

	$("#sche-start-date").val(start.format('YYYY-MM-DD'));
	$("#sche-end-date").val(end.format('YYYY-MM-DD'));
}
);

$('#sche-modify-calendar').daterangepicker({
	"locale": {
		"format": 'YYYY-MM-DD',
		"applyLabel": "확인",
		"cancelLabel": "취소",
		"daysOfWeek": ["월", "화", "수", "목", "금", "토", "일"],
		"monthNames": ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
	},
}, function(start, end) {

	$("#sche-start-modify").val(start.format('YYYY-MM-DD'));
	$("#sche-end-modify").val(end.format('YYYY-MM-DD'));
}
);

