package com.order.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.JdbcUtils;
import com.cart.biz.impl.CartBizImpl;
import com.cart.model.Cart;
import com.commons.BeanKit;
import com.commons.StrKit;
import com.connection.ConnectionPoolManager;
import com.order.biz.impl.OrderBizImpl;
import com.order.model.Order;
import com.order.model.Orderitem;
import com.page.PageBean;
import com.page.QueryInfo;
import com.servlet.BaseServlet;
import com.user.model.User;


public class OrderAction extends BaseServlet {

	
	public String findOrderById(HttpServletRequest request,HttpServletResponse response){
		/**
		 * 1、获取参数
		 */
		String oid = request.getParameter("oid");
		/**
		 * 2.通过id获取bean
		 */
		OrderBizImpl service = new OrderBizImpl();
		Order orderBean = service.findOrderById(oid);
		/**
		 * 3。返回到desc.jsp
		 * 
		 */
		request.setAttribute("orderBean", orderBean);
		return "f:/jsps/order/desc.jsp";
	}
	/**
	 * 创建订单
	 * @param request
	 * @param response
	 * @return
	 */
	public String createOrder(HttpServletRequest request,HttpServletResponse response){
		try {
				
			
			/**
			 * 1.获取前台参数，地址，购车id
			 */
			String cartItemIds = request.getParameter("cartItemIds");
			String address = request.getParameter("address");
			/**
			 * 2.获取购物车信息
			 */
			CartBizImpl cartService = new CartBizImpl();
			List<Cart> Carts = cartService.loadCartItems(cartItemIds);
			/**
			 * 3.创建订单，orderBean->order对象  orderItemBeans->orderItem
			 */
			User User = (User) request.getSession().getAttribute("user");
			Order orderBean = new Order();
			orderBean.setOid(StrKit.uuid());//设置主键（主键的原则：第一，生成的uuid；第二，自增）
			orderBean.setAddress(address);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			orderBean.setOrdertime(format.format(new Date()));
			orderBean.setOwner(User);
			orderBean.setStatus(1);
			
			BigDecimal totalDecimal = new BigDecimal("0");
			for (Cart Cart : Carts) {
				totalDecimal = totalDecimal.add(new BigDecimal(Cart.getSubTotal()+""));
			}
			orderBean.setTotal(totalDecimal.doubleValue());
			
		
			/**
			 * 4.创建orderItemBeans（这些订单项是数据上面创建的订单的）
			 */
			List<Orderitem> orderitemBeans = new ArrayList<Orderitem>();
			
			
			for (Cart Cart : Carts) {
				Orderitem orderitemBean = new Orderitem();
				orderitemBean.setOrderItemId(StrKit.uuid());
				orderitemBean.setBook(Cart.getBook());
				orderitemBean.setOrderbean(orderBean);
				orderitemBean.setQuantity(Cart.getQuantity());
				orderitemBean.setSubtotal(Cart.getSubTotal());
				orderitemBeans.add(orderitemBean);
			}
			orderBean.setOrderitemBeans(orderitemBeans);
			/**
			 * 5.调用service，完成数据的插入
			 *   完成购买的同时，删除购物车中的条目
			 */
			OrderBizImpl service = new OrderBizImpl();
			//事物
			/*JdbcUtils.beginTransaction();*/
			ConnectionPoolManager.beginTransaction();
			service.add(orderBean);//第一，把订单项插入订单表；第二，把订单项信息，插入订单项表
			cartService.batchDelete(cartItemIds);
			/*JdbcUtils.commitTransaction();*/
			ConnectionPoolManager.commitTransaction();
			/**
			 * 6.查看该订单的信息ordersuccess.jsp
			 */
			
			request.setAttribute("orderBean", orderBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "f:/jsps/order/ordersucc.jsp";
	}
	
	/**
	 * 查询我的订单
	 * @param request
	 * @param response
	 * @return
	 */
	public String myOrder(HttpServletRequest request,HttpServletResponse response){
		/**
		 * 1.获取user
		 */
		User User = (User) request.getSession().getAttribute("user");
		if(User==null){
			return "r:/jsps/user/login.jsp";
		}
		
		
		/**
		 * 2.分页
		 */
		QueryInfo queryInfo = BeanKit.toBean(request.getParameterMap(),QueryInfo.class);
		/**
		 * 3.调用service
		 */
		OrderBizImpl service = new OrderBizImpl();
		PageBean pageBean = service.findOrderByUid(User.getUid(),queryInfo);
		/**
		 * 4.封装pagebean
		 */
		setUrlToPageBean(request,pageBean);
		return "f:/jsps/order/list.jsp";
	}
	/**
	 * 优化，把url的操作，封装成方法
	 * @param request
	 * @param pageBean
	 */
	public void setUrlToPageBean(HttpServletRequest request,PageBean pageBean){
		String url = request.getRequestURI()+"?"+request.getQueryString();
		int index = url.lastIndexOf("&currentPage=");
		if(index>0){
			url = url.substring(0,index);
		}
		pageBean.setUrl(url);
		request.setAttribute("pageBean", pageBean);
	}
	
	
	public String pay(HttpServletRequest request,HttpServletResponse response){
		
		
		/**
		 * 1、获取参数
		 */
		String oid = request.getParameter("oid");
		/**
		 * 2.通过id获取bean
		 */
		OrderBizImpl service = new OrderBizImpl();
		Order orderBean = service.findOrderById(oid);
		/**
		 * 3。返回到desc.jsp
		 * 
		 */
		request.setAttribute("orderBean", orderBean);
		return "f:/jsps/order/pay.jsp";
		
	}
	public String payOk(HttpServletRequest request,HttpServletResponse response){
		String oid = request.getParameter("oid");
		OrderBizImpl service = new OrderBizImpl();
		
		service.updateStatus(oid);
		return "f:/jsps/main/main.jsp";

	}
	public String cancel(HttpServletRequest request,HttpServletResponse response){
		String oid = request.getParameter("oid");
		OrderBizImpl service = new OrderBizImpl();
		
		service.cancel(oid);
		return "f:/jsps/main/main.jsp";
	
	}
	public String buy(HttpServletRequest request,HttpServletResponse response){
		String oid = request.getParameter("oid");
		OrderBizImpl service = new OrderBizImpl();
		
		service.buy(oid);
		return "f:/jsps/main/main.jsp";
	
	}
}
