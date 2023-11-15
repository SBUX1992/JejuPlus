/**
 * 스케쥴 리스트 노출 페이지
 */

let scheduleList = {

	init: function() {

		const openAddModalBtn = document.querySelectorAll('.open-add-modal');
		const scheAddBtn = document.getElementById('sche-add');
		const closeAddModalBtn = document.getElementById('close-add-modal');
		const closeModifyModalBtn = document.getElementById('close-modify-modal');
		openAddModalBtn.forEach(btn => {
			btn.addEventListener('click', () => {
				openAddModal();
			});
		});
		
		scheAddBtn.addEventListener('click', () => {
			this.insertSche();
		});
		closeAddModalBtn.addEventListener('click', () => {
			closeAddModal();
		});
		closeModifyModalBtn.addEventListener('click', () => {
			closeModifyModal();
		});
	},
	/* 스케쥴 추가*/
	insertSche: function() {
		const title = document.getElementById('schedule-title');
		const startDate = document.getElementById('sche-start-date');
		const endDate = document.getElementById('sche-end-date');
		const userId = document.getElementById('user-id');

		let data = {
			userId: userId.value,
			title: title.value,
			startDate: startDate.value,
			endDate: endDate.value
		}

		let options = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(data)
		}

		fetch('/schedule/insert', options)
			.then(res => {
				if (!res.ok) {
					throw new Error('네트워크 응답 오류');
				}
				return res.text();
			}).then(data => {
				location.href = '/schedule/detail/edit/' + data;
			}).catch(error => {
				alert(error.message);
			});
	},
	/* 스케쥴 삭제*/
	deleteSche: function(scheduleId) {
		fetch('/schedule/delete/' + scheduleId, { method: 'DELETE' })
			.then(res => {
				if (!res.ok) {
					throw new Error('네트워크 응답 오류');
				}
				return res.text();
			}).then(res => {
				if (res != 'success') {
					throw new Error('삭제 도중 오류가 발생했습니다.');
				}
				alert('일정이 삭제되었습니다.');
				location.reload();

			}).catch(error => {
				alert(error.message);
			})
	}
}

scheduleList.init();

/* 모달 */
const addModal = document.getElementById("add-modal");// 일정 추가 모달창
const modifyModal = document.getElementById("modify-modal");// 일정 수정 모달창
/* 일정 추가 열기 */
function openAddModal() {
	addModal.style.display = "block";
}
/* 일정 추가 모달 닫기 버튼 눌렀을 시 */
function closeAddModal() {
	addModal.style.display = "none";
}
/* 일정 수정 열기 */
function openModifyModal(scheduleId, title, startDate, endDate) {
	const scheduleTitle = document.getElementById("modify-sche-title");
	const scheduleStartDate = document.getElementById("sche-start-modify");
	const scheduleEndDate = document.getElementById("sche-end-modify");
	const scheduleModifyBtn = document.getElementById("sche-modify");
	const userId = document.getElementById("user-id");
	scheduleTitle.value = title;
	scheduleStartDate.value = startDate;
	scheduleEndDate.value = endDate;
	/* 버튼을 눌렀을 때 일정 수정하는 이벤트 리스너*/
	scheduleModifyBtn.addEventListener('click', () => {
		let data = {
			scheduleId: scheduleId,
			title: scheduleTitle.value,
			userId: userId.value,
			startDate: scheduleStartDate.value,
			endDate: scheduleEndDate.value
		}

		let options = {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(data)
		}

		fetch('/schedule/modify', options)
			.then(res => {
				if (!res.ok) {
					throw new Error('네트워크 응답 오류');
				}
				return res.text();
			}).then(res => {
				if (res != 'success') {
					throw new Error('일정 수정에 실패했습니다.')
				}
				alert('일정을 수정했습니다.');
				closeModifyModal();
				location.reload();
			}).catch(error => {
				alert(error.message);
			});
	});

	modifyModal.style.display = "block";
}
/* 일정 수정 모달 닫기 버튼 눌렀을 시 */
function closeModifyModal() {
	modifyModal.style.display = "none";
}
