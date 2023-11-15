document.addEventListener("DOMContentLoaded", function () {
    // Email Update 부분
    var originalEmail = document.querySelector("input[name='email']").value;

    document.getElementById("updateEmailButton").addEventListener("click", function () {
        document.getElementById("email").style.display = "none";
        document.querySelector("label[for='orgemail']").style.display = "none";
        document.getElementById("updateEmailButton").style.display = "none";
        document.getElementById("verificationCodeSection").style.display = "block";
    });

    document.getElementById("sendCodeButton").addEventListener("click", function () {
        var newEmail = document.querySelector("input[name='newEmail']").value;
        console.log(newEmail);

        if (!newEmail) {
            alert("이메일을 입력해주세요.");
        } else {
            // Check for email duplication
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "/user/check-email?email=" + newEmail, true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    var response = xhr.responseText.trim();
                    console.log(response);
                    if (response === "duplicate") {
                        alert("이미 누군가 사용 중인 이메일입니다.");
                    } else {
						alert("안내가 나올때까지 잠시 기다려주세요.")
                        // Send a request to get the verification code
                        var codeXhr = new XMLHttpRequest();
                        codeXhr.open("POST", "/user/send-verification-code?email=" + newEmail, true);
                        codeXhr.setRequestHeader("Content-Type", "application/json");
                        codeXhr.onreadystatechange = function () {
                            if (codeXhr.readyState == 4 && codeXhr.status == 200) {
                                alert("인증 코드가 이메일로 전송되었습니다.");
                            }
                        };
                        codeXhr.send(JSON.stringify({ email: newEmail }));
                    }
                }
            };
            xhr.send();
        }
    });

    document.getElementById("verifyCodeButton").addEventListener("click", function () {
        var email = document.querySelector("input[name='newEmail']").value;
        var code = document.querySelector("input[name='code']").value;
        var userId = document.querySelector("input[name='userId']").value;

        // Send a request to verify the code
        var verifyXhr = new XMLHttpRequest();
        verifyXhr.open("POST", "/user/verify-code?code=" + code, true);
        verifyXhr.setRequestHeader("Content-Type", "application/json");
        verifyXhr.onreadystatechange = function () {
            if (verifyXhr.readyState == 4 && verifyXhr.status == 200) {
                var response = verifyXhr.responseText.trim();
                if (response === "Verification successful") {
                    alert("변경이 성공적으로 완료되었습니다.");
                    var updateXhr = new XMLHttpRequest();
                    updateXhr.open("POST", "/user/update-email/" + userId +"?email=" + email, true);
                    updateXhr.setRequestHeader("Content-Type", "application/json");
                    updateXhr.onreadystatechange = function () {
                        if (updateXhr.readyState == 4 && updateXhr.status == 200) {
                            alert("이메일이 성공적으로 변경되었습니다.");
                            window.location.href = "/user/userUpdate/" + userId;
                        }
                    };
                    updateXhr.send(JSON.stringify({ email: email }));
                } else {
                    alert("인증 실패");
                }
            }
        };
        verifyXhr.send(JSON.stringify({ email: email, code: code, userId: userId }));
    });

    // Password Update 부분
    document.getElementById("password-update-btn").addEventListener("click", function (event) {
        event.preventDefault();
        document.getElementById("password-update-btn").style.display = "none";
        document.getElementById("password-update-section").style.display = "block";
    });

    document.getElementById("confirm-password-update-btn").addEventListener("click", function () {
        var currentPassword = document.getElementById("current-password").value;
        var newPassword = document.getElementById("new-password").value;
        var userId = document.querySelector("input[name='userId']").value;

        var requestData = {
            currentPassword: currentPassword,
            newPassword: newPassword
        };

        console.log("비동기 함수 시작");
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/user/check-update-password/" + userId, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                var response = JSON.parse(xhr.responseText);
                if (response.status === "PasswordUpdated") {
                    alert("비밀번호가 성공적으로 변경되었습니다.");
                    window.location.href = "/user/userUpdate/" + userId;
                } else if (response.status === "PasswordMismatch") {
                    alert("현재 비밀번호가 아닙니다.");
                } else {
                    alert("비밀번호 변경 실패");
                }
            }
        };
        xhr.send(JSON.stringify(requestData));
        console.log("비동기 함수 끝");
    });

    // 수정하기 버튼
    document.getElementById("update-user-Button").addEventListener("click", function () {
        alert("수정이 완료되었습니다.");
    });
});
