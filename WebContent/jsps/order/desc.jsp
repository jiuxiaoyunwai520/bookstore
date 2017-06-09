<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单详细</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/order/desc.css'/>">
  </head>
  
<body>
	<div class="divOrder">
		<span>订单号：${orderBean.oid }
		<c:if test="${orderBean.status==1 }">(等待付款)</c:if>
			<c:if test="${orderBean.status==2 }">(等待发货)</c:if>
			<c:if test="${orderBean.status==3 }">(等待确认)</c:if>
			<c:if test="${orderBean.status==4 }">(交易成功)</c:if>	
			<c:if test="${orderBean.status==5 }">(已经取消)</c:if>		
		　　　下单时间：${orderBean.ordertime }</span>
	</div>
	<div class="divContent">
		<div class="div2">
			<dl>
				<dt>收货人信息</dt>
				<dd>${orderBean.address }</dd>
			</dl>
		</div>
		<div class="div2">
			<dl>
				<dt>商品清单</dt>
				<dd>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<th class="tt">商品名称</th>
							<th class="tt" align="left">单价</th>
							<th class="tt" align="left">数量</th>
							<th class="tt" align="left">小计</th>
						</tr>



<c:forEach items="${orderBean.orderitemBeans }" var="orderitemBean">
						<tr style="padding-top: 20px; padding-bottom: 20px;">
							<td class="td" width="400px">
								<div class="bookname">
								  <img align="middle" width="70" src="<c:url value='/${orderitemBean.book.image_b }'/>"/>
								  <a href="<c:url value='/jsps/book/desc.jsp'/>">${orderitemBean.book.bname}</a>
								</div>
							</td>
							<td class="td" >
								<span>&yen;${orderitemBean.book.currPrice}</span>
							</td>
							<td class="td">
								<span>${orderitemBean.quantity}</span>
							</td>
							<td class="td">
								<span>&yen;${orderitemBean.subtotal}</span>
							</td>			
						</tr>

</c:forEach>

					</table>
				</dd>
			</dl>
		</div>
		<div style="margin: 10px 10px 10px 550px;">
			<span style="font-weight: 900; font-size: 15px;">合计金额：</span>
			<span class="price_t">&yen;${orderBean.total }</span><br/>
<c:if test="${orderBean.status==1 }">
	<a  id="pay" href="<c:url value='/OrderAction?flag=pay&oid=${orderBean.oid }'/>" class="pay"><!--支付  --></a><br/>
    <a id="cancel" href="<c:url value='/OrderAction?flag=cancel&oid=${OrderBean.oid }'/>;javascript:alert('订单已取消！');">取消订单</a><br/>
</c:if>  
<c:if test="${orderBean.status==3 }">
	<a id="confirm" href="<c:url value='/OrderAction?flag=buy&oid=${OrderBean.oid }'/>;javascript:alert('交易成功！');">确认收货</a><br/>	
</c:if> 
		</div>
	</div>
</body>
</html>

