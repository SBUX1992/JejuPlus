<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/view/header.jsp"%>
<link rel="stylesheet" href="/css/contents/favorite.css" />
<link rel='stylesheet' href='//fonts.googleapis.com/earlyaccess/notosanskr.css'>

<body>
	<div id="favoriteArea">찜한여행</div>
	<hr>
	<br><br>
	<div id="container">
		<c:forEach var="contents" items="${favoriteList}">
			<div id="box" onclick="location.href='/contents/touristAreaDetail/${contents.contentsId}'">
				<div id="img-box">
					<div id="label">${contents.contentsLabel}</div>
					<img src="${contents.thumbnailPath}" onerror="this.src='/images/NoImage.jpg'" id="img">
				</div>
				<div id="content-box">
					<div id="title">${contents.title}</div> 
					<div id="location">${contents.region1} > ${contents.region2}</div>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
<script>


</script>
<%@ include file="/WEB-INF/view/footer.jsp"%>