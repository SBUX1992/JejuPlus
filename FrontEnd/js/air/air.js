// main 달력
$(document).ready(function () {
    $(function () {
        $('input[name="daterange"]').daterangepicker({
            startDate: moment(),
            endDate: moment(),
            opens: 'center',
            autoApply: true,
            locale: {
                format: 'YYYY/MM/DD',
            },
            minDate: moment(), // 이전 날짜 선택 방지
        });
    });
});

// 사이드바 토글
$(document).ready(function () {
    $('#toggleButton').click(function () {
        $('.sidebar').toggleClass('show-sidebar');
    });
});

// close
$(function () {
    $('input[name=dep-name]').keydown(function () {
        if ($(this).val().trim() != '') {
            $(this).next().css('display', 'block');
        } else {
            $(this).next().css('display', 'none');
        }
    });
    $('.fa-times-circle').click(function () {
        $(this).prev().val('');
        $(this).css('display', 'none');
    });
});

// 탑승객 숫자
const decreaseButton = document.getElementById('decreasePassenger');
const increaseButton = document.getElementById('increasePassenger');
const passengerCountElement = document.getElementById('passengerCount');

let passengerCount = 1;

decreaseButton.addEventListener('click', () => {
    if (passengerCount > 1) {
        passengerCount--;
        passengerCountElement.textContent = passengerCount;
    }
});

increaseButton.addEventListener('click', () => {
    passengerCount++;
    passengerCountElement.textContent = passengerCount;
});
