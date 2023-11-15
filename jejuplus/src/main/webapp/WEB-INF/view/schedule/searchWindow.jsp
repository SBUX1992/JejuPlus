<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="search-container">
	<input type="text" id="search-input" placeholder="검색어를 입력하세요">
	<select id="search-select">
		<option value="tag">태그</option>
		<option value="title">이름</option>
	</select>
	<button id="search-button">검색</button>
</div>
<div id="search-output"></div>