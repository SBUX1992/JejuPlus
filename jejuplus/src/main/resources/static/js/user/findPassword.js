/**
 *  비밀번호 찾기
 */
document.addEventListener("DOMContentLoaded", function () {
    var codeVerified = false; // 인증 여부를 저장하기 위한 변수

    document.getElementById("sendCodeButton").addEventListener("click", function () {
        var username = document.querySelector("input[name='username']").value;
        var email = document.querySelector("input[name='email']").value;
        console.log(username);
        console.log(typeof username);
        

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/user/check-id-email", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                    var response = xhr.responseText;
                    alert(response);  // 텍스트 형식으로 처리
                    if (response === "success") {
                        sendVerificationCode(email);
                        alert("안내가 나올때까지 잠시만 기다려주세요");
                    }
                } else {
                    alert("아이디와 이메일을 한번 더 확인해주세요.");
                }
            }
        };
        xhr.send(JSON.stringify({ username: username, email: email }));
    });

    function sendVerificationCode(email) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/user/send-verification-code?email=" + email, true);  // 변경된 부분
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                    alert("인증 코드가 이메일로 전송되었습니다.");
                } else {
                    alert("인증 코드 전송 도중 오류가 발생했습니다.");
                }
            }
        };
        xhr.send();
    }

    document.getElementById("verifyCodeButton").addEventListener("click", function() {
        var email = document.querySelector("input[name='email']").value;
        var code = document.querySelector("input[name='code']").value;

        verifyCode(email, code);
    });

    function verifyCode(email, code) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/user/verify-code?code=" + code, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                var response = xhr.responseText;  // 텍스트 형식으로 처리
                if (response === "Verification successful") {
                    alert("인증이 성공적으로 완료되었습니다.");
                    codeVerified = true; // 인증 완료
                } else {
                    alert("인증 코드를 다시 확인해주세요.");
                    codeVerified = false; // 인증 실패
                }
            } else {
                alert("인증 코드 확인 도중 오류가 발생했습니다.");
            }
        }
    };
    xhr.send(JSON.stringify({ email: email, code: code }));
}

document.getElementById("reset-password-form").addEventListener("submit", function(event) {
    event.preventDefault();

    var email = document.getElementById("reset-password-email").value;

    if (codeVerified) { // 인증이 성공했을 때에만 임시 비밀번호 발급
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/user/reset-password?email=" + email, true);
		xhr.setRequestHeader("Content-Type", "application/json");
		xhr.onreadystatechange = function() {
    	if (xhr.readyState == 4) {
       		 if (xhr.status == 200) {
       		     var response = xhr.responseText;
      		      alert(response);
      		      window.location.href = "/user/sign-in";
     		   } else {
     		       alert("비밀번호 초기화 도중 오류가 발생했습니다.");
     		   }
   		 }
		 };
				xhr.send();
		} else {
        alert("인증되지 않았습니다.");
    	}
});

});
