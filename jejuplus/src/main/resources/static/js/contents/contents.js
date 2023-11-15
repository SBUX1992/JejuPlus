let contents = {
	version: 1,
	init: function() {		
		// 찜 등록
		const unfavorite = document.querySelector("#unfavorite");
		if (unfavorite != null) {
			unfavorite.addEventListener("click", () => {
				this.favorite();
			});
		} 
		const unfavorite2 = document.querySelector("#unfavorite2");
		if (unfavorite2 != null) {
			unfavorite2.addEventListener("click", () => {
				alert("로그인이 필요합니다.");
				window.location.href = "/user/sign-in";
			});
		} 

		// 찜 취소
		const favorite = document.querySelector("#favorite");

		if (favorite != null) {
			favorite.addEventListener("click", () => {
				this.unfavorite();
			});
		}
		
		// 추천 등록
		const unrecommended = document.querySelector("#unrecommended");
		if (unrecommended != null) {
			unrecommended.addEventListener("click", () => {
				this.recommended();
			});
		}
		const unrecommended2 = document.querySelector("#unrecommended2");
		if (unrecommended2 != null) {
			unrecommended2.addEventListener("click", () => {
				alert("로그인이 필요합니다.");
				window.location.href = "/user/sign-in";
			});
		} 

		// 추천 취소
		const recommended = document.querySelector("#recommended");

		if (recommended != null) {
			recommended.addEventListener("click", () => {
				this.unrecommended();
			});
		}
		
		// 리뷰 등록
		document.querySelector("#insertReviewBtn").addEventListener("click", () => {
			this.insertReview();
		});
		
		
		// 모달창 열기
		openModalBtn.addEventListener("click", () => {
			modal.style.display = "block";
			document.body.style.overflow = "hidden"; // 스크롤바 제거
		});
		
		// 모달창 닫기
		closeMadalBtn.addEventListener("click", () => {
			modal.style.display = "none";
			document.body.style.overflow = "auto"; // 스크롤바 보이기
		});
		
		
		// 리뷰 수정
		const modifyModal = document.querySelector("#modifyModal");
		modifyModal.addEventListener("click", () => {
			this.modifyReview();			
		});
		
		// 리뷰 삭제
		const deleteReviewBtn = document.querySelector("#deleteReviewBtn");
		if (deleteReviewBtn != null) {
			document.querySelector("#deleteReviewBtn").addEventListener("click", () => {
				this.deleteReview();
			});
		}
	},
	
	// 찜 등록
	favorite: function() {
		let favorite = {
			userId: document.getElementById("userId").value,
			contentsId: document.getElementById("contentsId").value,
			contentsLabel: document.getElementById("contentsLabel").value
		};
		
		console.log('dpd')

		fetch("/api/contents/"+ favorite.contentsLabel + "/" + favorite.contentsId + "/favorite", {
			method: "POST",
			body: JSON.stringify(favorite),
			headers: {
				"Content-Type": "application/json",
			}
		}).then(response => {
			 
			if(response.ok){
			 alert("찜이 등록되었습니다.");
			} else if(response.status == 401) {
				alert("로그인이필요합니다.");
			} else {
				alert("리뷰 등록 실패");
			}
				
			
			window.location.reload();
		});
	},

	// 찜 취소
	unfavorite: function() {
		let unfavorite = {
			contentsId: document.getElementById("contentsId").value,
			contentsLabel: document.getElementById("contentsLabel").value
		};
		console.log(unfavorite.contentsId);
		fetch("/api/contents/"+ unfavorite.contentsLabel + "/" + unfavorite.contentsId + "/unfavorite", {
			method: "DELETE",
			headers: {
				"Content-Type": "application/json",
			}
		}).then(response => {
			const msg = (response.ok) ? "찜이 취소되었습니다." : "찜 취소 실패";
			alert(msg);
			window.location.reload();
		});
	},
	
	// 추천
	recommended: function() {
		let recommended = {
			userId: document.getElementById("userId").value,
			contentsId: document.getElementById("contentsId").value,
			contentsLabel: document.getElementById("contentsLabel").value
		};

		fetch("/api/contents/"+ recommended.contentsLabel + "/" + recommended.contentsId + "/recommended", {
			method: "POST",
			body: JSON.stringify(recommended),
			headers: {
				"Content-Type": "application/json",
			}
		}).then(response => {
			const msg = (response.ok) ? "추천되었습니다." : "추천 등록 실패";
			alert(msg);
			window.location.reload();
		});
	},

	// 추천 취소
	unrecommended: function() {
		let unrecommended = {
			contentsId: document.getElementById("contentsId").value,
			contentsLabel: document.getElementById("contentsLabel").value
		};
		console.log(unrecommended.contentsId);
		fetch("/api/contents/"+ unrecommended.contentsLabel + "/" + unrecommended.contentsId + "/unrecommended", {
			method: "DELETE",
			headers: {
				"Content-Type": "application/json",
			}
		}).then(response => {
			const msg = (response.ok) ? "추천이 취소되었습니다." : "추천 삭제 실패";
			alert(msg);
			window.location.reload();
		});
	},

	// 리뷰 등록
	insertReview: function() {
		
		let review = {
			reviewStar: document.querySelector('input[name="reviewStar"]:checked').value,
			reviewContent: document.getElementById("reviewContent").value,
			contentsId: document.getElementById("contentsId").value,
			contentsLabel: document.getElementById("contentsLabel").value
		};
		console.log(contentsLabel);
		
		fetch("/api/contents/"+ review.contentsLabel + "/" + review.contentsId + "/review", {
			method: "POST",
			body: JSON.stringify(review),
			headers: {
				"Content-Type": "application/json",
			}
		}).then(response => {
			if(response.ok){
				alert("리뷰가 등록되었습니다.");
				window.location.reload();
            } else if(response.status == 401) {
                alert("로그인이 필요합니다.");
                window.location.href = "/user/sign-in";
            } else if(response.status == 400){
                alert("이미 등록된 댓글입니다.");
				window.location.reload();
            } else {
                alert("리뷰 등록 실패");
            }
		});
	},
	
	// 리뷰 수정
	modifyReview: function() {

		let review = {
			reviewStar: document.querySelector('input[name="modifyStar"]:checked').value,
			reviewContent: document.getElementById("modifyContent").value,
			reviewId: document.querySelector("#reviewId").value,
			userId: document.getElementById("userId").value,
			contentsId: document.getElementById("contentsId").value,
		};
		console.log(review);
		
		fetch("/api/contents/"+ review.contentsLabel + "/" + review.contentsId + "/review", {
			method: "PATCH",
			body: JSON.stringify(review),
			headers: {
				"Content-Type": "application/json",
			}
		}).then(response => {
			const msg = (response.ok) ? "리뷰가 수정되었습니다." : "리뷰 수정 실패";
			alert(msg);
			window.location.reload();
		});
	},
	
	// 리뷰 삭제
	deleteReview: function() {
		let reviewId = document.getElementById("reviewId").value;

		fetch("/api/review/" + reviewId, {
			method: "DELETE",
			headers: {
				"Content-Type": "application/json"
			}
		}).then(response => {
			const msg = (response.ok) ? "리뷰가 삭제되었습니다." : "리뷰 삭제 실패";
			alert(msg);

			window.location.reload();
		});
	},
}
contents.init();
