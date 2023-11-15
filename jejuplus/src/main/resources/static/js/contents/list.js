let contentsList = {
	/* 컨텐츠 리스트의 페이지 변경 시 페이지 세팅*/
	changePage: function(e) {
		const contentsLabel = document.getElementById("contents-label").value;

		console.log(e.target.getAttribute('data-page'));
		
	if(contentsLabel == '관광지'){
		location.href = '/contents/touristAreaList?page=' +e.target.getAttribute('data-page');	
	}else if(contentsLabel == '쇼핑'){
		location.href = '/contents/shoppingList?page=' +e.target.getAttribute('data-page');	
	}else if(contentsLabel == '음식점'){
		location.href = '/contents/restaurantList?page=' +e.target.getAttribute('data-page');	
	}else if(contentsLabel == '숙박'){
		location.href = '/contents/lodgingList?page=' +e.target.getAttribute('data-page');	
	}
	
	}
}