document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("find-user-Button").addEventListener("click", function(event) {
        event.preventDefault();

        var email = document.getElementById("email").value;
        alert("이메일을 확인 중 입니다. 안내가 나올때까지 잠시만 기다려주세요");

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/user/find-username", true);
        xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                    var responseText = xhr.responseText;
                    if (responseText === "UserNotFound") {
                        alert("사용자를 찾을 수 없습니다.");
                    } else {
                        alert("입력하신 이메일에서 아이디를 확인해주세요: ");
                        window.location.href = "/user/sign-in";
                    }
                } else {
                    alert("서버 오류가 발생했습니다.");
                }
            }
        };
        xhr.send(email);
    });
});
