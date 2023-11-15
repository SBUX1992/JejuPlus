<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="error" style="width: 100%; height: 100%;">
	<p><img src="/images/logo_dark.png"></p>
	<br>
	<h2>500 ERROR</h2>
	<h4 style="color: #767676;">Internal Server Error</h4>
</div>
<style>
  .error {
    width: 100%;
    height: 100%;
    min-height: 800px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .error img {
    display: block;
    margin: 0 auto;
    max-width: 400px;
    max-height: 100px;
    width: auto;
    height: auto;
    
  }
</style>
<!-- ------------------------------------------------------------- -->
<%@ include file="/WEB-INF/view/footer.jsp"%>