<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/bookstore/bootstrap/css/bootstrap.min.css" rel="stylesheet"> 
<link rel="stylesheet" type="text/css" href="<c:url value='/css/section/top.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/section/search.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/section/navigation.css'/>">
<script type="text/javascript">

/* $(document).ready(function(){	   
		$("#body").load("/bookstore/CategoryAction?flag=findAll");		
	}); */
/* window.location.href = "${pageContext.request.contextPath}/CategoryAction?flag=findAll"; */
</script>
</script>
</head>
<body >
		<jsp:include page="../section/top.jsp" flush="true"/>
		<div style="line-height: 25px;visibility: hidden; ">|</div>  
		<jsp:include page="../section/search.jsp" flush="true"/>
		<div style="line-height: 25px;visibility: hidden; ">|</div>  
		<jsp:include page="../section/navigation.jsp" flush="true"/> 
		<jsp:include page="../section/body.jsp" flush="true"/>
		<%-- <div style="line-height: 25px;visibility: hidden; ">|</div>  
		<jsp:include page="../section/left.jsp" flush="true"/> --%>
		 
		 
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<script src="/bookstore/bootstrap/js/bootstrap.min.js"></script> 
</body>
</html>