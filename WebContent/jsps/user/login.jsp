<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/bookstore/bootstrap/css/bootstrap.min.css" rel="stylesheet"> 
<link rel="stylesheet" type="text/css" href="<c:url value='/css/user/login.css'/>">
</head>
<body>
	<form class="form-horizontal"  action="<c:url value="/UserAction"/>" method="post" target="_top">
		<input type="hidden" name="flag" value="login">
		<div class="container">
			<div class="form-group">
			    <div class="col-sm-offset-4 col-sm-4 text-center">
			      <h3>用户登录</h3>			
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputLoginname" class="col-sm-4 control-label" >用户名:</label>
			    <div class="col-sm-4">
			      <input type="text" class="tdInput form-control" name="loginname" id="loginname" placeholder="请输入用户名">
			    </div>
			  </div>
			
			 <div class="form-group">
			    <div class="col-sm-offset-4 col-sm-4">
			      <span id="loginnameError" class="spanError">${errors.loginname}</span>
			      <span id="msgError" class="spanError">${msg}</span>
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="inputPassword" class="col-sm-4 control-label">登录密码：</label>
			    <div class="col-sm-4">
			      <input type="password" class="tdInput form-control" name="loginpass" id="loginpass" placeholder="请输入登录密码">
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <div class="col-sm-offset-4 col-sm-4">
			      <span id="loginpassError" class="spanError">${errors.loginpass}</span>
			    </div>
			  </div>
	  
	  		  <div class="form-group">
			    <label for="inputVerifyCode" class="col-sm-4 control-label">图形验证码：</label>
			    <div class="col-sm-4">
			      <input type="text" class="tdInput form-control" name="verifyCode" id="verifyCode" placeholder="请输入验证码">
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <div class="col-sm-offset-4 col-sm-4">
			      <span id="verifyCodeError" class="spanError">${errors.verifyCode}</span>
			    </div>
			  </div> 
			  
	 		 <div class="form-group">
			    <div class="col-sm-offset-4 col-sm-4">
					 <div class="divImg">
						<img class="imgVar" id="img" src="<c:url value="/VerifyCode"/>">
					 </div>
					 <a href="javaScript:_change()">换一张</a>
			    </div>
			  </div>
	  
			  <div class="form-group">
			    <div class="col-sm-offset-4 col-sm-4">
			      <button type="submit" class="btn btn-default">登录</button>
			    </div>
			  </div>  
	     </div>
    </form>
   <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
   <script type="text/javascript" src="<c:url value='/js/user/login.js'/>"></script> 
   <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
   <script src="/bookstore/bootstrap/js/bootstrap.min.js"></script>  
</body>
</html>