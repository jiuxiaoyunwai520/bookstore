<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>cartlist.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script src="<c:url value='/js/round.js'/>"></script>
	
	<link rel="stylesheet" type="text/css" href="/bookstore/css/cart/list.css">
<script type="text/javascript">

$(function(){
	//页面打开，计算总计
	subTotal();
	//
	$("#selectAll").click(function(){
		var bool = $("#selectAll").attr("checked");
		//1.所有条目跟着选中或者取消
		cartitemchechornot(bool);
		//2.总计需要重新计算
		subTotal();
		//3.结算按钮
		jiesuanbut(bool);
	});
	
	$(":checkbox[name=checkboxBtn]").click(function(){
		//1.获取所有单选个数
		var allCheckBoxBtn=$(":checkbox[name=checkboxBtn]").length;
		//2.获取选中的个数
		var allCheckedBtn=$(":checkbox[name=checkboxBtn][checked=true]").length;
		//3.如果相等->全选，把全选按钮勾上，小记从新计算
		if(allCheckBoxBtn==allCheckedBtn){
			$("#selectAll").attr("checked",true);
			jiesuanbut(true);
		}else if(allCheckedBtn==0){
			//4.如果选中个数等于0->全取消  结算按钮置灰，小记从新计算
			$("#selectAll").attr("checked",false);
			jiesuanbut(false);
		}else{
			//5.部分选中->全选不选，结算不灰，小记从新计算
			$("#selectAll").attr("checked",false);
			jiesuanbut(true);
		}
		subTotal();
	});
	
	$(".jia").click(function(){
		var jiaid = $(this).attr("id");
		var cartItemId = jiaid.substring(0,32);
		var quantityId = cartItemId+"Quantity";
		var quantity = $("#"+quantityId).val();
		updateQuantity(cartItemId,Number(quantity)+1);
	});
	
	$(".jian").click(function(){
		var jiaid = $(this).attr("id");
		var cartItemId = jiaid.substring(0,32);
		var quantityId = cartItemId+"Quantity";
		var quantity = $("#"+quantityId).val();
		if(Number(quantity)<2){
			alert("请点击删除按钮进行删除");
			return false;
		}
		updateQuantity(cartItemId,Number(quantity)-1);
	});
});
function updateQuantity(itemid,quantity){
	$.ajax({
		url:"/bookstore/CartAction?flag=updateQuantity",
		data:{"itemid":itemid,"quantity":quantity},
		dataType:"json",
		success:function(result){
			$("#"+itemid+"Quantity").val(result.quantity);
			$("#"+itemid+"Subtotal").text(result.subtotal);
			subTotal();
		}
	});
}
//规定条目选定与否
function cartitemchechornot(bool){
	//获取所有条目
	$(":checkbox[name=checkboxBtn]").attr("checked",bool);
}

//计算总计
function subTotal(){
	var total=0;//总计
	//1.获取所有选中的条目
	$(":checkbox[name=checkboxBtn][checked=true]").each(function(){
		//2.获取选中条目的id
		var cid = $(this).val();
		//3.根据id获取该条目小计
		var subtotal=$("#"+cid+"Subtotal").text();
		//4.把所有小计加起来，得到总计
		total+=Number(subtotal);
	});
	//5.把总计赋值到页面
	$("#total").text(round(total,2));
	
}

//结算按钮的置灰和点亮
function jiesuanbut(bool){
	//如果true，点亮
	if(bool){
		$("#jiesuan").addClass("jiesuan").removeClass("kill");
		$("#jiesuan").unbind("click");
	}else{
		//置灰
		$("#jiesuan").removeClass("jiesuan").addClass("kill");
		$("#jiesuan").click(function(){return false});
	}
}
//批量删除
function batchDelete(){
	var cartItemIds = new Array();
	$(":checkbox[name=checkboxBtn][checked=true]").each(function(){
		cartItemIds.push($(this).val());
	});
	//如果用户没有选中数据，提示用户请勾选，不要访问后台
	if(cartItemIds.length<=0){
		alert("请勾选您要删除的数据");
		return;
	}
	window.location.href="/bookstore/CartAction?flag=batchDelete&cartItems="+cartItemIds;
}

function jiesuan(){
	//1.获取选中的条目
	var cartItemIds = new Array();
	$(":checkbox[name=checkboxBtn][checked=true]").each(function(){
		cartItemIds.push($(this).val());
	});
	//2.把id赋值给cartItemIds输入项
	$("#cartItemIds").val(cartItemIds.toString());
	//3.提交表单
	$("#form1").submit();
}
</script>
  </head>
  <body>

<c:choose>
  <c:when test="${empty carts}">
	  <table width="95%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right">
					<img align="top" src="<c:url value='/images/icon_empty.png'/>"/>
				</td>
				<td>
					<span class="spanEmpty">您的购物车中暂时没有商品</span>
				</td>
			</tr>
		</table>  
	<br/>
	<br/>
  </c:when>
  <c:otherwise>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
	<tr align="center" bgcolor="#efeae5">
		<td align="left" width="50px">
			<input type="checkbox" id="selectAll" checked="checked"/><label for="selectAll">全选</label>
		</td>
		<td colspan="2">商品名称</td>
		<td>单价</td>
		<td>数量</td>
		<td>小计</td>
		<td>操作</td>
	</tr>

<c:forEach items="${carts }" var="cartItem">
	<tr align="center">
		<td align="left">
			<input value="${cartItem.cartItemId }" type="checkbox" name="checkboxBtn" checked="checked"/>
		</td>
		<td align="left" width="70px">
			<a class="linkImage" href="<c:url value='/BookAction?flag=findBookById&bid=${cartItem.book.bid }'/>"><img border="0" width="54" align="top" src="<c:url value='/${cartItem.book.image_b }'/>"/></a>
		</td>
		<td align="left" width="350px">
		    <a href="<c:url value='/BookAction?flag=findBookById&bid=${cartItem.book.bid }'/>"><span>${cartItem.book.bname }</span></a>
		</td>
		<td><span>&yen;<span class="currPrice" id="12345CurrPrice">${cartItem.book.currPrice }</span></span></td>
		<td>
			<a class="jian" id="${cartItem.cartItemId }Jian"></a><input class="quantity" readonly="readonly" id="${cartItem.cartItemId }Quantity" type="text" value="${cartItem.quantity }"/><a class="jia" id="${cartItem.cartItemId }Jia"></a>
		</td>
		<td width="100px">
			<span class="price_n">&yen;<span class="subTotal" id="${cartItem.cartItemId }Subtotal">${cartItem.subTotal }</span></span>
		</td>
		<td>
			<a href="<c:url value='/CartAction?flag=batchDelete&cartItems=${cartItem.cartItemId }'/>">删除</a>
		</td>
	</tr>
</c:forEach>
	
	<tr>
		<td colspan="4" class="tdBatchDelete">
			<a href="javascript:batchDelete();">批量删除</a>
		</td>
		<td colspan="3" align="right" class="tdTotal">
			<span>总计：</span><span class="price_t">&yen;<span id="total"></span></span>
		</td>
	</tr>
	<tr>
		<td colspan="7" align="right">
			<a href="javascript:jiesuan()" id="jiesuan" class="jiesuan"></a>
		</td>
	</tr>
</table>
	<form id="form1" action="<c:url value='/CartAction'/>" flag="post">
		<input type="hidden" name="cartItemIds" id="cartItemIds"/>
		<input type="hidden" name="flag" value="loadCartItems"/>
	</form>
  </c:otherwise>
</c:choose>
  </body>
</html>
