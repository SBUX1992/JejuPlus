$(document).ready(function() {// Handle the "change-level" button click
	$(".change-level").on("click",function() {// Get the user's level ID
		var username = $(this).closest("tr").find("#usernameInput").val();
		var levelId = $(this).data("target").split("-")[1];
		// Show the modal for the specific user
		$('#changeLevelModal-'+ username).modal('show');

		// Set the username in the modal for reference
		$('#changeLevelModal-'+ levelId).find("#usernameInput").val(username);

		// Call the changeUserLevel function with the retrieved values
	});
});

function changeUserLevel(levelId, newLevelId, username) {
	// Get the existing levelId and username
	var existingLevelId = levelId; // 이제는 사용하지 않아도 될 수 있습니다.

	// Make an AJAX request to update the user's level
	$.ajax({
		type : 'POST',
	     	url : '/admin/updateUserLevel', // 서버의 업데이트 엔드포인트에 맞게 업데이트해야 합니다.
		data : {
			levelId : existingLevelId, // 필요하다면 기존 레벨도 전달
			newLevelId : newLevelId,
			username : username
				},
		success : function(data) {
			console.log(username);
			console.log(newLevelId);
			console.log(data.result);
			if (data.result === "success") {
				alert("사용자 권한이 변경되었습니다.");
				
				// 모달 창을 닫습니다.
				$("#changeLevelModal-" + username).modal("hide");
				location.reload();
			} else {
				alert("권한 변경에 실패했습니다.");
			}
		},
		error : function(xhr) {
			console.log(username);
			alert("서버에서 오류가 발생했습니다.");
			$("#changeLevelModal-" + username).modal("hide");
		}
	});
}

// 삭제
var deleteButtons = document.querySelectorAll(".delete-user");
deleteButtons.forEach(function (button) {
  button.addEventListener("click", function () {
    var username = this.closest("tr").querySelector("#usernameInput").value;
    console.log(username);

    if (confirm("삭제하시겠습니까?")) {
      var deleteXhr = new XMLHttpRequest();
      deleteXhr.open('POST', '/admin/adminUserDelete/' + username, true);
      deleteXhr.setRequestHeader('Content-Type', 'application/json');
      deleteXhr.onload = function () {
        if (deleteXhr.status === 200) {
          var data = JSON.parse(deleteXhr.responseText);
          if (data && data.result === "success") {
            alert("사용자 정보가 삭제되었습니다.");
            window.location.href = '/admin/adminUserManagement';
          } else {
            alert("서버에서 빈 응답이 돌아왔습니다. 삭제에 실패했습니다.");
          }
        } else {
          console.error("Request failed. Returned status of " + deleteXhr.status);
          alert("삭제에 실패했습니다.");
        }
      };
      deleteXhr.send("username=" + username);
    } else {
      // 사용자가 취소한 경우 아무 작업도 필요하지 않습니다.
    }
  });
});
