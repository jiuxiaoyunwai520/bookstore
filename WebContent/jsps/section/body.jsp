<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/bookstore/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"href="<c:url value='/css/section/body.css'/>">
</head>

<body>
	<div class="container " >
		<div class="col-md-2 bg" style="padding:0px; margin:0px;border:0px">			
			<iframe name="leftList" id="leftList" style="padding:0px; margin:0px;border:0px" align="center" scrolling="no"
				src="/bookstore/CategoryAction?flag=findAll" frameborder="0"
				scrolling="no" width="100%" onload="javascript:iframeAutoHeight1();"></iframe>
		</div>
		<div class="col-md-10">
		    <div align="center">
			<iframe name="bookList" id="bookList" src="/bookstore/jsps/section/carousel.jsp" frameborder="0"
				align="center" scrolling="no" width="100%" onload="javascript:iframeAutoHeight();"></iframe>
		
		</div>

		<script type="text/javascript">
			function reSetIframeHeight() {
				window.top.window.iframeAutoHeight();
				window.top.window.iframeAutoHeight1();
			}
			function iframeAutoHeight() {
				var iframe = document.getElementById("bookList");
				if (navigator.userAgent.indexOf("MSIE") > 0
						|| navigator.userAgent.indexOf("rv:11") > 0
						|| navigator.userAgent.indexOf("Firefox") > 0) {
					iframe.height = iframe.contentWindow.document.body.scrollHeight;
				} else {
					iframe.height = iframe.contentWindow.document.documentElement.scrollHeight;
				}
			}function iframeAutoHeight1() {
				var iframe = document.getElementById("leftList");
				if (navigator.userAgent.indexOf("MSIE") > 0
						|| navigator.userAgent.indexOf("rv:11") > 0
						|| navigator.userAgent.indexOf("Firefox") > 0) {
					iframe.height = iframe.contentWindow.document.body.scrollHeight;
				} else {
					iframe.height = iframe.contentWindow.document.documentElement.scrollHeight;
				}
			}
		</script>
		<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
		<script src="/bookstore/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>