// main 달력
$(document).ready(function () {
    $(function () {
        $('input[name="daterange"]').daterangepicker({
            startDate: moment(),
            endDate: moment(),
            opens: 'center',
            autoApply: true,
            locale: {
                format: 'DD/MM/YYYY',
            },
        });
    });
});

// 사이드바 토글
$(document).ready(function () {
    $('#toggleButton').click(function () {
        $('.sidebar').toggleClass('show-sidebar');
    });
});
