
/*
스케쥴의 세부 일정 페이지
*/
let scheduleDetailShow = {

	container: document.getElementById('map'), // 지도 컨테이너
	locations: [], // 선택한 장소 정보를 담을 배열
	distances: [], // 거리 정보를 담을 배열
	markers: [], // 마커 배열
	distance: 0, // 하루 일정 동안 총 직선거리
	polyline: null, // 지도에 표시할 선
	isFirst: true, // 지도에 처음 선을 그리는지 여부
	options: {  //지도를 생성할 때 필요한 기본 옵션
		center: new kakao.maps.LatLng(33.5056848111, 126.4960226206),
		level: 8 	//지도의 레벨(확대, 축소 정도)
	},

	init: function() {

		this.map = new kakao.maps.Map(this.container, this.options); //지도 생성 및 객체 리턴

		this.requestLocations(1);

	},

	/*캘린더에서 날짜를 변환했을 때*/
	changeDate: function(changedDate) {
		const dateInput = document.getElementById('date');
		const startDateValue = document.getElementById('start-date').value;
		const scheduleDate = document.getElementById('schedule-day');
		const output = document.getElementById('list-output');

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
	},
	showDetail: function(index) {
		this.moveMapCenter(this.locations[index]);
		const output = document.getElementById('contents-output');
		output.innerHTML = '';
		
		fetch('/schedule/contentsDetail/'+ this.locations[index].id)
		.then(res=>{
			if(!res.ok){
				throw new Error('네트워크 응답 오류')
			}
			return res.json();
		}).then(data=>{
			const cardElement = document.createElement('div');
			cardElement.classList.add('card');
			cardElement.classList.add('flex-row');
			output.appendChild(cardElement);
			
			const cardHeader = document.createElement('div');
			cardHeader.classList.add('card-header');
			cardHeader.classList.add('border-0');
			cardElement.appendChild(cardHeader);
			
			const thumbnailImage = document.createElement('img');
			thumbnailImage.setAttribute('src',data.thumbnailPath);
			thumbnailImage.setAttribute('onerror',"this.src='/images/NoImage.jpg'");
			thumbnailImage.classList.add('thumbnail');
			cardHeader.appendChild(thumbnailImage);
			
			const cardBody = document.createElement('div');
			cardBody.classList.add('card-body');
			cardBody.classList.add('p-3');
			cardElement.appendChild(cardBody);
			
			const contentsTitle = document.createElement('h4');
			contentsTitle.classList.add('card-title');
			contentsTitle.textContent = data.title;
			contentsTitle.addEventListener('click',()=>{
				this.moveToDetailPage(data.contentsId, data.contentsLabel);
			});
			cardBody.appendChild(contentsTitle);
			
			
			const contentsMemo = document.createElement('p');
			contentsMemo.classList.add('card-text');
			contentsMemo.textContent = this.locations[index].memo.length != 0 ? this.locations[index].memo : '작성된 메모가 없어요';
			cardBody.appendChild(contentsMemo);
			
			
			
		}).catch(error=>{
			alert(error.message);
		})
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
			const listOutput = document.getElementById('list-output');
			
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
			} else{
				const errorDiv = document.createElement('div');
				errorDiv.classList.add('error-div');
				listOutput.appendChild(errorDiv);
				
				const errorImg = document.createElement('img');
				errorImg.src = '/images/schedule/list-none-calendar.png';
				errorImg.classList.add('error-img');
				errorDiv.appendChild(errorImg);
				
				const errorMsg = document.createElement('p');
				errorMsg.textContent = '이 날에 등록 된 일정이 없어요.\n편집버튼을 눌러 추가해주세요!';
				errorMsg.classList.add('error-msg');
				errorDiv.appendChild(errorMsg);
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
	
	moveToDetailPage: function(contentsId, label){
		
		let url = '';
		
		switch(label){
			case '관광지' :
				url = '/contents/touristAreaDetail/'+contentsId;
				break;
			case '숙박' :
				url = '/contents/lodgingDetail/'+contentsId;
				break;
			case '쇼핑' :
				url = '/contents/shoppingDetail/'+contentsId;
				break;
			case '음식점' :
				url = '/contents/restaurantDetail/'+contentsId;
				break;
			default :
				alert('상세보기가 지원되지 않는 페이지입니다.')
				break;	
		}
		
		window.open(url, '_blank', 'width=1200,height=1000');
		
	},



	/* 일정에 추가된 컨텐츠들 출력*/
	printScheduleList: function() {

		const output = document.getElementById('list-output');
		output.innerHTML = '';

		this.locations.forEach((item, index) => {
			const itemDiv = document.createElement('div');
			itemDiv.classList.add('list-card');
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
			colDiv7.textContent = item.title;
			rowDiv.appendChild(colDiv7);

			const colDiv3 = document.createElement('div');
			colDiv3.classList.add('col-3');
			rowDiv.appendChild(colDiv3);

			const markerIcon = document.createElement('img');
			markerIcon.src = '/images/schedule/placeholder.png';
			markerIcon.classList.add('item-btn');
			markerIcon.addEventListener('click', () => {
				this.showDetail(index);
			});
			colDiv3.appendChild(markerIcon);


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

	}
};

/* 최초 페이지 실행 시*/
scheduleDetailShow.init();


