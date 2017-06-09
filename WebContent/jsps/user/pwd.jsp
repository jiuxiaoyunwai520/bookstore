<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>pwd.jsp</title>
<link href="/bookstore/bootstrap/css/bootstrap.min.css" rel="stylesheet"> 
<link rel="stylesheet" type="text/css" href="<c:url value='/css/user/login.css'/>">
</head>
<body>
	<form class="form-horizontal"  action="<c:url value="/UserAction"/>" method="post" target="_top">
		<input type="hidden" name="flag" value="updatePass">
		<div class="container">
			<div class="form-group">
			    <div class="col-sm-offset-4 col-sm-4 text-center">
			      <h3>用户密码修改</h3>			
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
			    <label for="inputRePassword" class="col-sm-4 control-label">新密码：</label>
			    <div class="col-sm-4">
			      <input type="password" class="tdInput form-control" name="newpass"  id="newpass" placeholder="请输入确认密码">
			    </div>		   
			  </div>   
			  
	  			<div class="form-group">
			    <div class="col-sm-offset-4 col-sm-4">
			      <span id="newpassError" class="spanError">${errors.newpass}</span>
			    </div>
			  </div>
			  
			  
			   <div class="form-group">
			    <label for="inputRePassword" class="col-sm-4 control-label">确认密码：</label>
			    <div class="col-sm-4">
			      <input type="password" class="tdInput form-control" name="reloginpass"  id="reloginpass" placeholder="请输入确认密码">
			    </div>		   
			  </div>   
			  
	  			<div class="form-group">
			    <div class="col-sm-offset-4 col-sm-4">
			      <span id="reloginpassError" class="spanError">${errors.reloginpass}</span>
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
			      <button type="submit" class="btn btn-default">Sign in</button>
			    </div>
			  </div>  
	     </div>
    </form>
   <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
   <script type="text/javascript" src="<c:url value='/js/user/pwd.js'/>"></script>
   <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
   <script src="/bookstore/bootstrap/js/bootstrap.min.js"></script>  
</body>
</html>