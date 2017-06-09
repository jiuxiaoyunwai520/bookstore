<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>组合查询</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	
	-->
	<link href="/bookstore/bootstrap/css/bootstrap.min.css" rel="stylesheet"> 
  </head>
  
  <body>
	
	<form class="form-horizontal"  action="<c:url value='/BookAction'/>" method="get" >
		<input type="hidden" name="flag" value="findBookByQuery"/>
		<div class="container">
			<div class="form-group">
			    <div class="col-sm-offset-4 col-sm-4 text-center">
			      <h3>高级搜索</h3>			
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputLoginname" class="col-sm-4 control-label" >书名:</label>
			    <div class="col-sm-4">
			      <input type="text" class="tdInput form-control" name="bname" id="loginname" >
			    </div>
			  </div>
			<div class="form-group">
			    <label for="inputLoginname" class="col-sm-4 control-label" >作者:</label>
			    <div class="col-sm-4">
			      <input type="text" class="tdInput form-control" name="author" id="" >
			    </div>
			  </div>
			
	   		<div class="form-group">
			    <label for="inputLoginname" class="col-sm-4 control-label" >出版社:</label>
			    <div class="col-sm-4">
			      <input type="text" class="tdInput form-control" name="press" id="">
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-sm-offset-4 col-sm-4">
			      <button type="submit" class="btn btn-default">搜     索</button>
			       <button type="reset" class="btn btn-default">重新填写</button>
			    </div>
			  </div>  
	     </div>
	     <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
   <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
   <script src="/bookstore/bootstrap/js/bootstrap.min.js"></script>  
    </form>
  </body>
</html>
