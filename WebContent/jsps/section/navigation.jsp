<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/bookstore/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/section/navigation.css'/>">
</head>
<body>
	<div class="height container" >
		<div class="row">
			<div class="col-md-2 bg" style="width: 190px;margin-left: 15px;">图书分类</div>
			<div class="col-md-offset-1 col-md-1"  style="margin-right:15px;"><a class="a" href="/bookstore/index.jsp">首页</a></div>
			<div class="col-md-1"><a class="a" href="#">预售</a></div>
			<div class="col-md-1"><a class="a" href="#">电子书</a></div>
			<div class="col-md-1"><a class="a" href="#">陪读计划</a></div>
			<div class="col-md-1"><a class="a" href="#">特色书店</a></div>
			<div class="col-md-1"><a class="a" href="#">邮币商城</a></div>
			<div class="col-md-1"><a class="a" href="#">社区</a></div>
			<div class="col-md-1"><a class="a" href="#">图书勋章</a></div> 
			<div class="col-md-1"></div> 
		</div>
	</div>
	
<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<script src="/bookstore/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>