<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/bookstore/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/section/search.css'/>">
</head>
<body>
	<div class="container">
	  <div class="col-md-offset-2 col-md-8">
		<form action="<c:url value='/BookAction'/>" class="navbar-form navbar-left" method="get" target="bookList" id="form1">
		<input type="hidden" name="flag" value="findBookByQuery"/>
			<div class="form-group">
				<input name="bname" type="text" class="input" placeholder="Search">
			</div>
			<button type="submit" class="btn btn-default">搜索</button>
			<span>
			<a href="<c:url value='/jsps/section/gj.jsp'/>" style="font-size: 10pt; color: #404040;" target="bookList">高级搜索</a>
			</span>
		</form>
		</div>			
	</div>
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<script src="/bookstore/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>