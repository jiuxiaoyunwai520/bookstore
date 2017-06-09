<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/order/list.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
  </head>
  
  <body>
<div class="divMain">
	<div class="divTitle">
		<span style="margin-left: 150px;margin-right: 280px;">商品信息</span>
		<span style="margin-left: 40px;margin-right: 38px;">金额</span>
		<span style="margin-left: 50px;margin-right: 40px;">订单状态</span>
		<span style="margin-left: 50px;margin-right: 50px;">操作</span>
	</div>
	<br/>
	<table align="center" border="0" width="100%" cellpadding="0" cellspacing="0">

<c:forEach items="${pageBean.beanList }" var="OrderBean">
		<tr class="tt">
			<td width="320px">订单号：<a  href="<c:url value='/jsps/order/desc.jsp'/>">${OrderBean.oid }</a></td>
			<td width="200px">下单时间：${OrderBean.ordertime }</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr style="padding-top: 10px; padding-bottom: 10px;">
			<td colspan="2">


<c:forEach items="${OrderBean.orderitemBeans }" var="OrderitemBean">
	<a class="link2" href="<c:url value='/jsps/book/desc.jsp'/>">
	    <img border="0" width="70" src="<c:url value='/${OrderitemBean.book.image_b }'/>"/>
	</a>
</c:forEach>	




			</td>
			<td width="115px">
				<span class="price_t">&yen;${OrderBean.total }</span>
			</td>
			<td width="142px">
			<c:if test="${OrderBean.status==1 }">(等待付款)</c:if>
			<c:if test="${OrderBean.status==2 }">(等待发货)</c:if>
			<c:if test="${OrderBean.status==3 }">(等待确认)</c:if>
			<c:if test="${OrderBean.status==4 }">(交易成功)</c:if>	
			<c:if test="${OrderBean.status==5 }">(已经取消)</c:if>			
			</td>
			<td>

				<a href="<c:url value='/OrderAction?flag=findOrderById&oid=${OrderBean.oid }'/>">查看</a><br/>
<c:if test="${OrderBean.status==1 }">
				<a href="<c:url value='/OrderAction?flag=pay&oid=${OrderBean.oid }'/>">支付</a><br/>
				<a href="<c:url value='/OrderAction?flag=cancel&oid=${OrderBean.oid }'/>" target="_parent">取消</a><br/>						
</c:if>
<c:if test="${OrderBean.status==3 }">
				<a href="<c:url value='/OrderAction?flag=buy&oid=${OrderBean.oid }'/>">确认收货</a><br/>
</c:if>

			</td>
		</tr>

</c:forEach>

	</table>
	<br/>
	<%@include file="/jsps/pager/pager.jsp" %>
</div>
  </body>
</html>
