/**
 *  회원가입
 */
var usernameChecked = false;
var passwordChecked = false;
var emailChecked = false;
var codeVerified = false;
var firstUsername;
var firstPassword;

// Function to display alerts
function showAlert(message) {
    alert(message);
}

// Function to check email duplication and send verification code
function checkEmailAndSendCode() {
    var email = $("input[name='email']").val();

    $.get("/user/check-email", { email: email })
        .done(function(response) {
            if (response === "duplicate") {
                showAlert("이미 누군가 사용 중인 이메일입니다.");
                emailChecked = false;
            } else {
                showAlert("이메일을 확인 중 입니다. 안내가 나올때까지 잠시만 기다려주세요");
                $.post("/user/send-verification-code", { email: email })
                    .done(function(response) {
                        showAlert("인증 코드가 이메일로 전송되었습니다.");
                        emailChecked = true;
                    })
                    .fail(function(error) {
                        showAlert("인증 코드 전송에 실패했습니다.");
                        emailChecked = false;
                    });
            }
            checkFormSubmission();
        })
        .fail(function(error) {
            showAlert("이미 사용중인 이메일 입니다.");
            emailChecked = false;
        });
}

// Function to verify the code
function verifyCode() {
    var email = $("input[name='email']").val();
    var code = $("input[name='code']").val();

    $.post("/user/verify-code", { email: email, code: code }, function(response) {
        if (response === "Verification successful") {
            showAlert("인증이 성공적으로 완료되었습니다.");
            codeVerified = true;
        } else {
            showAlert("인증 실패");
            codeVerified = false;
        }
        checkFormSubmission();
    });
}

// Function to check username availability
function checkUsername() {
    var username = $("#usernameInput").val();
    var maxUsernameLength = 15;
    var minUsernameLength = 6;

    if (username.length > maxUsernameLength || username.length < minUsernameLength) {
        showAlert("아이디는 6자에서 15자 사이여야 합니다.");
        usernameChecked = false;
    } else {
        $.get("/user/check-username", { username: username }, function(response) {
            if (response === "Username available") {
                showAlert("사용 가능한 아이디입니다.");
                usernameChecked = true;
                firstUsername = username; // Update the hidden input field
                $("#firstUsernameInput").val(username);
            } else {
                showAlert("아이디가 이미 존재하거나 한글로 작성했습니다.");
                usernameChecked = false;
            }
            checkFormSubmission();
        });
    }
}

// Function to check password validity
function checkPassword() {
    var password = $("#passwordInput").val();
    var maxPasswordLength = 20;
    var minPasswordLength = 8;

    if (password.length > maxPasswordLength || password.length < minPasswordLength) {
        showAlert("비밀번호는 8자에서 20자 사이여야 합니다.");
        passwordChecked = false;
    } else {
        $.get("/user/check-password", { password: password }, function(response) {
            if (response === "Password valid") {
                showAlert("사용 가능한 비밀번호입니다.");
                passwordChecked = true;
                firstPassword = password; // Update the hidden input field
                $("#firstPasswordInput").val(password);
            } else {
                showAlert("비밀번호가 요구 사항을 충족하지 않습니다.");
                passwordChecked = false;
            }
            checkFormSubmission();
        });
    }
}

// Function to enable/disable the submission button
function checkFormSubmission() {
    var registerFinalButton = $("#register-final-button");
    if (usernameChecked && passwordChecked && emailChecked && codeVerified) {
        registerFinalButton.prop("disabled", false);
    } else {
        registerFinalButton.prop("disabled", true);
    }
}

// Click event for the "sendCodeButton"
$("#sendCodeButton").click(function() {
    checkEmailAndSendCode();
});

// Click event for the "verifyCodeButton"
$("#verifyCodeButton").click(function() {
    verifyCode();
});

// Click event for the "checkUsernameButton"
$("#checkUsernameButton").click(function() {
    checkUsername();
});

// Click event for the "checkUserPasswordButton"
$("#checkUserPasswordButton").click(function() {
    checkPassword();
});

// Form submission event
$("#registrationForm").submit(function(event) {
    event.preventDefault(); // Prevent form submission
    var formData = $(this).serialize();
    var username = $("#usernameInput").val();
    var password = $("#passwordInput").val();
    var email = $("#emailInput").val();
    var code = $("#codeInput").val();
    var fullname = $("#fullnameInput").val();
    var phoneNumber = $("#phoneNumberInput").val();

    // Validation for empty fields
    if (!username || !password || !email || !code || !fullname || !phoneNumber) {
        showAlert("입력된 정보가 모두 필요합니다.");
    } else {
        if (firstUsername == username && firstPassword == password) {
            // AJAX request for registration
            $.post("/user/register", formData, function(response) {
                if (response === "Registration successful") {
                    showAlert("가입이 성공적으로 완료되었습니다.");
                    window.location.href = "/user/sign-in"; // Redirect to login page
                } else {
                    showAlert("가입 실패"); // Display failure message
                }
            }).fail(function(jqXHR, textStatus, errorThrown) {
                if (jqXHR.status === 400) {
                    showAlert("아이디 또는 이메일을 확인해주세요.");
                } else {
                    showAlert("가입에 실패했습니다. 다시 시도해주세요.");
                }
            });
        } else {
            showAlert("입력하신 정보가 변경되었습니다.");
        }
    }
});
