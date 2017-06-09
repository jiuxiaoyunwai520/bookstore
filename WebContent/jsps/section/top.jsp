<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/bookstore/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/section/top.css'/>">
</head>
<body>
	<div class="bgf7f7f7">
		<div class="container">
			<div class="row">
				<c:choose>
					<c:when test="${user.loginname==null}">
						<div class="col-md-2">
							<a class="a" href="<c:url value='/jsps/user/login.jsp'/>"
								target="_parent">亲，请登录</a> <a class="a"
								href="<c:url value='/jsps/user/regist.jsp'/>" target="_parent">免费注册</a>
						</div>
						<div class="col-md-offset-6 col-md-1">
							<a href="<c:url value='/OrderAction?flag=myOrder'/>" class="a">我的订单</a>
						</div>
						<div class="col-md-1">
							<a href="<c:url value='/CartAction?flag=myCart'/>" class="a">购物车</a>
						</div>
					</c:when>
					<c:otherwise>
						<div class="col-md-2">
							会员： ${user.loginname}&nbsp;&nbsp;|&nbsp;&nbsp; <a class="a"
								href="<c:url value='/jsps/user/pwd.jsp'/>"
								target="_parent">修改密码</a>
						</div>
						<div class="col-md-offset-5 col-md-1">
							<a href="<c:url value='/OrderAction?flag=myOrder'/>" class="a"
								target="bookList">我的订单</a>
						</div>

						<div class="col-md-1">
							<a href="<c:url value='/CartAction?flag=myCart'/>"
								target="bookList" class="a">购物车</a>
						</div>
					</c:otherwise>
				</c:choose>
				<div class="col-md-1">
					<a href="#" class="a dropdown-toggle" data-toggle="dropdown"
						role="button" aria-haspopup="true" aria-expanded="false">联系客服<span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a class="a" href="#">帮助中心</a></li>

						<li role="separator" class="divider"></li>
						<li><a class="a" href="#">联系客服</a></li>
						<li><a class="a" href="#">我要投诉</a></li>
						<li><a class="a" href="#">意见建议</a></li>
					</ul>
				</div>
				<div class="col-md-1">
					<a class="a">网站导航</a>
				</div>

				<c:choose>
					<c:when test="${user.loginname!=null}">
						<div class="col-md-1">
							<a class="a" href="<c:url value='/UserAction?flag=quit'/>"
								target="_parent">退出</a>
						</div>
					</c:when>
				</c:choose>
			</div>
		</div>
	</div>
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<script src="/bookstore/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>