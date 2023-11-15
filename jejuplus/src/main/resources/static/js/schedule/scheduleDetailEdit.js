
/*
스케쥴의 세부 일정 수정 페이지
*/
let scheduleDetailEdit = {

	container: document.getElementById('map'), // 지도 컨테이너
	locations: [], // 선택한 장소 정보를 담을 배열
	distances: [], // 거리 정보를 담을 배열
	markers: [], // 마커 배열
	distance: 0, // 하루 일정 동안 총 직선거리
	polyline: null, // 지도에 표시할 선
	isFirst: true, // 지도에 처음 선을 그리는지 여부 (컨텐츠를 처음 일정에 담았는 지)
	options: {  //지도를 생성할 때 필요한 기본 옵션
		center: new kakao.maps.LatLng(33.5056848111, 126.4960226206),
		level: 8 	//지도의 레벨(확대, 축소 정도)
	},
	map: null, // 지도 객체
	// 탭 간 이동 시 해당 탭의 페이지 저장
	allPage: 1,
	attractionPage: 1,
	accomodationPage: 1,
	shoppingPage: 1,
	restaurantPage: 1,
	favoritePage: 1,
	// 검색 결과의 페이지 이동 시 카테고리와 검색어가 바뀌지 않게 저장
	category: '',
	keyword: '',

	init: function() {
		const sortingBtn = document.getElementById('sorting-btn');
		const savingBtn = document.getElementById('saving-btn');
		const editcomplBtn = document.getElementById('edit-compl');
		let scheduleId = document.getElementById('schedule-id').value;

		sortingBtn.addEventListener('click', () => {
			this.sortingScheduleDetail();
		});
		savingBtn.addEventListener('click', () => {
			this.saveScheduleDetail();
		});
		editcomplBtn.addEventListener('click', () => {
			if (confirm('저장되지 않은 일정은 삭제됩니다. 확인 버튼을 누르시면 편집을 종료합니다.')) {
				location.href = "/schedule/detail/show/" + scheduleId;
			}
		});

		this.map = new kakao.maps.Map(this.container, this.options); //지도 생성 및 객체 리턴
		this.printContentsList('all');

		this.requestLocations(1);



	},

	/*캘린더에서 날짜를 변환했을 때*/
	changeDate: function(changedDate) {
		const dateInput = document.getElementById('date');
		const startDateValue = document.getElementById('start-date').value;
		const scheduleDate = document.getElementById('schedule-day');
		const output = document.getElementById('list-output');


		if (confirm('저장되지 않은 일정은 삭제됩니다. 날짜를 바꾸시겠습니까?')) {
			this.deleteMarkers();
			if (!this.isFirst) {
				this.polyline.setMap(null);
			}
			this.isFirst = true;
			this.locations.length = 0;
			let startDate = new Date(startDateValue);
			let seletedDate = new Date(changedDate);
			let diff = Math.abs(startDate.getTime() - seletedDate.getTime());
			diff = Math.ceil(diff / (1000 * 60 * 60 * 24));
			scheduleDate.textContent = diff + 1;
			dateInput.value = changedDate;

			output.innerHTML = '';
			this.requestLocations(diff + 1);

			return true;

		} else {
			return false;
		}

		;
	},

	/* 날짜를 변경했을 시 DB에 저장되어있는 상세일정 불러오기*/
	requestLocations: function(day) {

		this.locations.length = 0;
		const scheduleId = document.getElementById('schedule-id');

		let url = '/schedule/detail/request?scheduleId=' + scheduleId.value + '&itemDay=' + day;
		fetch(url).then(res => {
			if (!res.ok) {
				throw new Error('네트워크 응답 오류');
			}
			return res.json();
		}).then(list => {
			if (list.length > 0) {
				list.forEach(data => {
					let item = {
						id: data.contentsId,
						title: data.title,
						region1: data.region1,
						region2: data.region2,
						label: data.label,
						memo: data.itemMemo,
						x: data.longitude,
						y: data.latitude
					};

					this.locations.push(item);
				});
				this.printScheduleList();
				this.drawLine(this.locations);
				this.moveMapCenter(this.locations[0]);
			}

		})

	},

	/* 해당 장소를 맵의 중앙에 세팅 */
	moveMapCenter: function(location) {
		// 이동할 위도 경도 위치를 생성합니다 
		let moveLatLon = new kakao.maps.LatLng(location.y, location.x);

		// 지도 중심을 부드럽게 이동시킵니다
		// 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
		this.map.panTo(moveLatLon);
	},

	/* 마커를 만들고 지도에 세팅하기 */
	setMarker: function(index, lat, lng) {

		// 커스텀 마커
		let content = "<div class = 'circle' style='width :20px; height: 20px; text-align: center;'>" + (index + 1) + "</div>";

		// 커스텀 마커가 표시될 위치입니다 
		let position = new kakao.maps.LatLng(lat, lng);

		// 커스텀 마커를 생성합니다
		let customOverlay = new kakao.maps.CustomOverlay({
			position: position,
			content: content
		});

		this.markers.push(customOverlay); // 마커 배열에 저장 (추후 삭제를 위함)

		customOverlay.setMap(this.map); // 마커를 맵에 세팅
	},

	/* 마커를 삭제*/
	deleteMarkers: function() {
		this.markers.forEach(marker => {
			marker.setMap(null);
		});
		this.markers.length = 0;
	},

	/* 지도에 선 긋기 */
	drawLine: function(locations) {

		let linePath = [];
		for (let i = 0; i < locations.length; i++) {
			linePath.push(new kakao.maps.LatLng(locations[i].y, locations[i].x));
			this.setMarker(i, locations[i].y, locations[i].x);
		}

		// 지도에 표시할 선을 생성합니다
		this.polyline = new kakao.maps.Polyline({
			path: linePath, // 선을 구성하는 좌표배열 입니다
			strokeWeight: 3, // 선의 두께 입니다
			strokeColor: '#FFAE00', // 선의 색깔입니다
			strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
			strokeStyle: 'solid' // 선의 스타일입니다
		});

		if (this.isFirst) {
			this.isFirst = false;
		}
		// 지도에 선을 표시합니다 
		this.polyline.setMap(this.map);

	},

	/* 위도, 경도로 직선 거리 구하기 */
	getDistanceFromLatLonInKm: function(lat1, lng1, lat2, lng2) {
		function deg2rad(deg) {
			return deg * (Math.PI / 180)
		}
		let R = 6371; // Radius of the earth in km
		let dLat = deg2rad(lat2 - lat1);  // deg2rad below
		let dLon = deg2rad(lng2 - lng1);
		let a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		let d = R * c; // Distance in km
		return d;
	},
	/* active 클래스 제거 및 추가*/
	modifyActive: function(label) {
		const oldItem = document.querySelector('.nav-item.active');
		oldItem.classList.remove('active');
		const selectedTab = document.querySelector('.nav-link[data-tab="' + label + '"]');
		selectedTab.closest('.nav-item').classList.add('active');
	},

	/* 컨텐츠 리스트 출력 */
	printContentsList: function(label) {

		const tabOutput = document.getElementById('tab-output');

		this.modifyActive(label);

		let url = "";
		switch (label) {
			case 'all':
				url = "/schedule/show-list/all?page=" + this.allPage;
				break;
			case 'attraction':
				url = "/schedule/show-list/attraction?page=" + this.attractionPage;
				break;
			case 'accomodation':
				url = "/schedule/show-list/accomodation?page=" + this.accomodationPage;
				break;
			case 'shopping':
				url = "/schedule/show-list/shopping?page=" + this.shoppingPage;
				break;
			case 'restaurant':
				url = "/schedule/show-list/restaurant?page=" + this.restaurantPage;
				break;
			case 'favorite':
				url = "/schedule/show-list/favorite?page=" + this.favoritePage;
				break;
			default:
				url = "/schedule/show-list/all?page=1";
				break;
		}

		fetch(url).then(res => {
			if (!res.ok) {
				throw new Error("네트워크 응답 오류");
			}
			return res.text();
		})
			.then(data => {
				tabOutput.innerHTML = data;
				//location.href="#";
			}).catch((error) => {
				console.error(error);
			});
	},

	/* 컨텐츠 리스트의 페이지 변경 시 페이지 세팅*/
	changePage: function(e) {

		let label = document.getElementById('label').value;

		switch (label) {
			case 'all':
				this.allPage = e.target.getAttribute('data-page');
				break;
			case 'attraction':
				this.attractionPage = e.target.getAttribute('data-page');
				break;
			case 'accomodation':
				this.accomodationPage = e.target.getAttribute('data-page');
				break;
			case 'shopping':
				this.shoppingPage = e.target.getAttribute('data-page');
				break;
			case 'restaurant':
				this.restaurantPage = e.target.getAttribute('data-page');
				break;
			case 'favorite':
				this.favoritePage = e.target.getAttribute('data-page');
				break;
			default:
				this.allPage = 1;
				break;
		}

		this.printContentsList(label);

	},

	/* 검색 창 출력 */
	printSearchWindow: function() {

		this.modifyActive('search');

		const tabOutput = document.getElementById('tab-output');

		fetch('/schedule/call-search')
			.then(res => {
				if (!res.ok) {
					throw new Error('네트워크 응답 오류');
				}
				return res.text();
			}).then(data => {
				tabOutput.innerHTML = data;
				const searchInput = document.getElementById('search-input');
				const searchButton = document.getElementById('search-button');
				searchButton.addEventListener('click', () => {
					this.setSearchValue();
				});
				searchInput.addEventListener('keyup', (event) => {
					if (event.keyCode == 13) {
						this.setSearchValue();
					}
				})
			}).catch((error) => {
				console.error(error);
			});

	},

	/* 검색 카테고리, 검색어 저장 후 출력(페이지를 넘어가도 카테고리와 검색어가 변하지않게)*/
	setSearchValue: function() {
		this.category = document.getElementById('search-select').value;
		this.keyword = document.getElementById('search-input').value;

		this.printSearchList(1);

	},

	/* 검색 결과 출력 */
	printSearchList: function(page) {

		const searchOutput = document.getElementById('search-output');

		let url = "/schedule/search-list?category=" + this.category + "&keyword=" + this.keyword + "&page=" + page;

		fetch(url).then(res => {
			if (!res.ok) {
				throw new Error('네트워크 응답 오류');
			}
			return res.text();
		}).then(data => {
			searchOutput.innerHTML = data;
		});

	},

	/* 상세보기 페이지 팝업*/
	moveToDetailPage: function(contentsId, label) {

		let url = '';

		switch (label) {
			case '관광지':
				url = '/contents/touristAreaDetail/' + contentsId;
				break;
			case '숙박':
				url = '/contents/lodgingDetail/' + contentsId;
				break;
			case '쇼핑':
				url = '/contents/shoppingDetail/' + contentsId;
				break;
			case '음식점':
				url = '/contents/restaurantDetail/' + contentsId;
				break;
			default:
				alert('상세보기가 지원되지 않는 페이지입니다.')
				break;
		}

		window.open(url, '_blank', 'width=1200,height=1000');

	},

	/* 일정추가한 컨텐츠를 일정리스트에 추가 후 지도와 리스트 출력*/
	addToScheduleList: function(idValue, titleValue, region1Value, region2Value, labelValue, mapX, mapY) {
		for (let i = 0; i < this.locations.length; i++) {
			if (this.locations[i].id === idValue) {
				alert("이미 일정에 있는 장소입니다.");
				return;
			}
		}

		let item = {
			id: idValue,
			title: titleValue,
			region1: region1Value,
			region2: region2Value,
			label: labelValue,
			memo: "",
			x: mapX,
			y: mapY
		};

		this.locations.push(item);
		this.printScheduleList();
		if (!this.isFirst) {
			this.polyline.setMap(null);
		}
		this.drawLine(this.locations);
		scrollTo({
			top: 20,
			left: 0,
			behavior: "smooth",
		});
	},

	/* 일정에 추가된 컨텐츠들 출력*/
	printScheduleList: function() {

		const output = document.getElementById('list-output');
		output.innerHTML = '';

		this.locations.forEach((item, index) => {
			const itemDiv = document.createElement('div');
			itemDiv.classList.add('list-card');
			itemDiv.setAttribute("draggable", true);
			itemDiv.addEventListener('dragstart', handleDragStart);
			itemDiv.addEventListener('dragenter', handleDragEnter);
			itemDiv.addEventListener('dragover', handleDragOver);
			itemDiv.addEventListener('dragleave', handleDragLeave);
			itemDiv.addEventListener('drop', handleDrop);
			itemDiv.addEventListener('dragend', handleDragEnd);
			output.appendChild(itemDiv);

			const rowDiv = document.createElement('div');
			rowDiv.classList.add('row');
			itemDiv.appendChild(rowDiv);

			const colDiv2 = document.createElement('div');
			colDiv2.classList.add('col-2');
			rowDiv.appendChild(colDiv2);

			const circle = document.createElement('div');
			circle.classList.add('circle');
			circle.textContent = index + 1;
			colDiv2.appendChild(circle);

			const colDiv7 = document.createElement('div');
			colDiv7.classList.add('col-7');
			colDiv7.innerHTML = "<p>" + item.title + "</p>" + "<p class='list-item-region'>" + item.region1 + ">" + item.region2 + "</p>";
			rowDiv.appendChild(colDiv7);

			const colDiv3 = document.createElement('div');
			colDiv3.classList.add('col-3');
			rowDiv.appendChild(colDiv3);

			const memoIcon = document.createElement('img');
			memoIcon.src = '/images/schedule/writing.png';
			memoIcon.classList.add('item-btn');
			memoIcon.addEventListener('click', () => {
				openModal(item.title, index);
			});
			colDiv3.appendChild(memoIcon);

			const binIcon = document.createElement('img');
			binIcon.src = '/images/schedule/bin.png';
			binIcon.classList.add('item-btn');
			binIcon.addEventListener('click', () => {
				this.removeList(index);
			});
			colDiv3.appendChild(binIcon);

			const itemIdData = document.createElement('input');
			itemIdData.setAttribute('type', 'hidden');
			itemIdData.value = item.id;
			itemDiv.appendChild(itemIdData);


			if (index > 0) {
				const distance = document.createElement('div');
				distance.classList.add('distance');
				const distanceATag = document.createElement('a');
				distanceATag.setAttribute("href", "http://map.naver.com/index.nhn?slng=" + this.locations[index - 1].x + "&slat=" + this.locations[index - 1].y +
					"&stext=" + this.locations[index - 1].title + "&elng=" + item.x + "&elat=" + item.y + "&pathType=0&showMap=true&etext=" + item.title + "&menu=route");
				distanceATag.setAttribute("target", "_blank");
				distanceATag.textContent = this.getDistanceFromLatLonInKm(this.locations[index - 1].y, this.locations[index - 1].x, item.y, item.x).toFixed(2) + ' km';
				distance.appendChild(distanceATag);
				itemDiv.appendChild(distance);
			}

		});

	},

	/* 일정 순서 정렬 */
	sortingScheduleDetail: function() {

		if (confirm('일정 내 첫번째 장소를 기준으로 일정의 순서가 변경됩니다.')) {
			this.distances.length = 0; // distances 안에 있는 값을 비움
			let tempArray = [...this.locations]; // locations를 깊은복사 한 배열 (배열의 원소를 빼주면서 계산하기 때문)
			let sortedArray = []; // 정렬된 location들이 담길 배열

			let criteria = tempArray[0]; // 최단거리 찾기의 기준이 될 경위도 (최초는 첫 번째 장소가 된다.)
			sortedArray.push(criteria);	// locations의 첫 번째 값을 기준으로 동선을 작성하기 때문에 첫 번째 값은 정렬된 배열의 첫 번째 값으로 고정
			tempArray.splice(0, 1); // 최단거리을 찾을 때 기준은 빠져야하기 때문에 제거

			while (tempArray.length > 0) {

				let minDistance = Number.MAX_VALUE; // 최단 거리
				let minIndex = -1; // 최단 거리값을 가진 객체의 index

				// 1. 기준의 경위도와 배열 안 요소의 경위도와 비교하여 거리를 구한다.
				// 2. 배열 길이만큼 for 반복문을 돌며 (직선 기준) 최단 거리일 경우, 최단 거리와 그 인덱스를 저장한다.
				// 3. for 반복문이 끝나면 최단거리를 저장하고 최단거리를 도출하던 location을 기준으로 정하고 tempArray에서 삭제한다. (기준을 제외하고 거리를 구해야하므로) 
				for (let i = 0; i < tempArray.length; i++) {
					const distance = this.getDistanceFromLatLonInKm(criteria.y, criteria.x, tempArray[i].y, tempArray[i].x); // 1
					// 2
					if (distance < minDistance) {
						minDistance = distance;
						minIndex = i;
					}
				}
				// 3
				if (minIndex !== -1) {
					sortedArray.push(tempArray[minIndex]);
					criteria = tempArray[minIndex];
					tempArray.splice(minIndex, 1);
				} else {
					break;
				}
			}

			for (let k = 0; k < sortedArray.length; k++) {
				console.log(sortedArray[k]);
			}

			if (!this.isFirst) {
				this.polyline.setMap(null);
			}
			this.drawLine(sortedArray); // 정렬된 배열으로 선을 그림
			this.moveMapCenter(sortedArray[0]); // 일정의 시작지점을 지도의 중앙으로
			this.locations = [...sortedArray]; // locations에 정렬된 Array복사
			this.printScheduleList(); // 정렬된 일정을 출력

		}

	},
	/* 일정 저장 */
	saveScheduleDetail: function() {
		if (this.locations.length == 0) {
			alert('해당 날짜의 일정이 없습니다.');
			return;
		}

		let details = [];
		let itemDay = document.getElementById('schedule-day').textContent;
		let scehduleId = document.getElementById('schedule-id').value;
		this.locations.forEach((item, index) => {

			let scheduleDetail = {
				contentsId: item.id,
				scheduleId: scehduleId,
				itemMemo: item.memo,
				itemDay: itemDay,
				itemSequence: index + 1
			}
			details.push(scheduleDetail);
		});


		let options = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(details)
		};
		console.log(options.body);
		fetch('/schedule/detail/save', options)
			.then(res => {
				if (!res.ok) {
					throw new Error('일정 저장에 실패했습니다.');
				}

				alert(`${itemDay}일 차 일정이 저장되었습니다.`);
			}).catch(error => {
				alert(error.message);
			});

	},

	/* 일정 항목 삭제 */
	removeList: function(index) {
		this.locations.splice(index, 1);
		this.deleteMarkers();
		this.polyline.setMap(null);
		if (this.locations.length !== 0) {
			this.drawLine(this.locations);
		}
		this.printScheduleList();
	},
};

/* 최초 페이지 실행 시*/
scheduleDetailEdit.init();


/* 드래그 드랍 시 발생하는 이벤트 */

let dragSrcEl;

function handleDragStart(e) {
	dragSrcEl = this;
	e.dataTransfer.effectAllowed = 'move';
	e.dataTransfer.setData('text/html', this.innerHTML);
}

function handleDragEnter() {
	this.classList.add('over');
}

function handleDragOver(e) {
	if (e.preventDefault) {
		e.preventDefault();
	}
	e.dataTransfer.dropEffect = 'move';
	return false;
}

function handleDragLeave() {
	this.classList.remove('over');
}

function handleDrop(e) {
	if (dragSrcEl !== this) {
		const srcContent = dragSrcEl.innerHTML;
		dragSrcEl.innerHTML = this.innerHTML;
		this.innerHTML = srcContent;
	}
	return false;
}

// 드래그 앤 드랍이 완료 됐을 시 일정 리스트 수정, 지도, 마커 수정
function handleDragEnd() {
	let listItems = document.querySelectorAll('.list-card');
	let newArray = [];

	listItems.forEach(listItem => {
		listItem.classList.remove('over');
		const idDataElement = listItem.querySelector('input[type="hidden"]');
		const idData = idDataElement.value;
		scheduleDetailEdit.locations.forEach(item => {
			if (item.id == idData) {
				newArray.push(item);
			}
		});
	});


	scheduleDetailEdit.locations = [...newArray];
	scheduleDetailEdit.deleteMarkers();
	scheduleDetailEdit.polyline.setMap(null);
	scheduleDetailEdit.drawLine(scheduleDetailEdit.locations);
	scheduleDetailEdit.printScheduleList();
}


/* 모달 */
const modal = document.getElementById("myModal");// 모달 창
/* 모달창(메모창) 열기 */
function openModal(title, index) {
	modal.style.display = "block";
	let contentsTitle = document.getElementById('contents-title');
	let contentsIndex = document.getElementById('contents-index');
	let contentsMemo = document.getElementById('contents-memo');
	contentsMemo.value = scheduleDetailEdit.locations[index].memo; // 해당 컨텐츠의 메모내용
	contentsTitle.textContent = title; //해당 컨텐츠(장소)의 이름
	contentsIndex.value = index;

}

/* 모달 닫기 버튼 눌렀을 시 */
function closeModal() {
	scheduleDetailEdit.locations[document.getElementById('contents-index').value].memo = document.getElementById('contents-memo').value;
	document.getElementById('contents-memo').value = '';
	document.getElementById('contents-index').value = "";
	modal.style.display = "none";
}
