/**
 * date-range-picker
 */


$('#calendar').daterangepicker({
	"locale": {
		"format": 'YYYY-MM-DD',
		"applyLabel": "확인",
		"cancelLabel": "취소",
		"daysOfWeek": ["일", "월", "화", "수", "목", "금", "토"],
		"monthNames": ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
	},
	"singleDatePicker": true,
	"autoUpdateInput": false,
	"minDate": $('#start-date').val(),
	"maxDate": $('#end-date').val(),
	setValue : function(date){
		$('#calendar').val(date);
	}
}, function(date) {
	let oldDate = $('#date').val();
	let result = scheduleDetailShow.changeDate(date.format('YYYY-MM-DD'));
	if(!result){
		$("#calendar").data('daterangepicker').setStartDate(oldDate);
		$("#calendar").data('daterangepicker').setEndDate(oldDate);
	}
	
});

