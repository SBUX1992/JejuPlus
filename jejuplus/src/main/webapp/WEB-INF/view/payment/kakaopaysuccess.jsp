<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>카카오페이 결제가 완료되었습니다.</h1>
	<p>결제일시: ${info.approved_at}</p>
	<p>주문번호: ${info.partner_order_id}</p>
	<p>상품명: ${info.item_name}</p>
	<p>상품수량: ${info.quantity}</p>
	<p>결제금액: ${info.amount.total}</p>
	<p>결제방법: ${info.payment_method_type}</p>



	<h2>[[${info}]]</h2>
</body>
</html>