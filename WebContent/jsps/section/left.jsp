<%@page import="java.nio.channels.SeekableByteChannel"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/bookstore/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
<link rel="stylesheet" type="text/css"href="<c:url value='/css/section/left.css'/>">
</head>
<body id="body " >
	<div class="container bg">
		<div class="col-md-2"  style="padding:0px; margin:0px;">
			<c:forEach items="${parents}" var="parent">
				<div  style="margin: 0px">
					<a class="a" style="margin: 0px;padding:0px; margin:0px; font-size:20px;">${parent.cname}</a>
				</div>
				<c:forEach items="${parent.children }" var="child">
					<a class="a" style="margin: 0px" href="/bookstore/BookAction?flag=findBookByCid&cid=${child.cid}" target="bookList">${child.cname }</a>
				   &nbsp;&nbsp;
				</c:forEach>
			</c:forEach>
		</div>
	</div>

	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<script src="/bookstore/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>