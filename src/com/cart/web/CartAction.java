package com.cart.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.book.model.Book;
import com.cart.biz.impl.CartBizImpl;
import com.cart.model.Cart;
import com.commons.BeanKit;
import com.servlet.BaseServlet;
import com.user.model.User;


/**
 * 第一，完成cartbean中小计的计算
 * 第二，完成查询
 * 第三，bean的封装
 * @author XD
 *  排错的方法是：缩小范围
 */
public class CartAction extends BaseServlet {
	private static final long serialVersionUID = -8587418118495983326L;

	/**
	 * 加载要购买的商品
	 * @param request
	 * @param response
	 * @return
	 */
	public String loadCartItems(HttpServletRequest request,HttpServletResponse response){
		try {
			
			
			/**
			 * 1.获取参数，ids;用,
			 */
			String cartItemIds = request.getParameter("cartItemIds");
			/**
			 * 2.调用后台查询
			 */
			CartBizImpl service = new CartBizImpl();
			List<Cart> cartBeans = service.loadCartItems(cartItemIds);
			/**
			 * 3.返回
			 */
			request.setAttribute("cartBeans", cartBeans);
			request.setAttribute("cartItemIds", cartItemIds);
			request.getRequestDispatcher("/jsps/cart/showitem.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 批量删除（也可以删除单条数据）
	 * @param request
	 * @param response
	 * @return
	 */
	public String batchDelete(HttpServletRequest request,HttpServletResponse response){
		/**
		 * 1.获取前台参数，知道要删除那一条数据 ：cartItems 多个id之间用逗号分隔
		 */
		String cartItemIds = request.getParameter("cartItems");
		/**
		 * 2.调用后台删除
		 */
		CartBizImpl service = new CartBizImpl();
		service.batchDelete(cartItemIds);
		/**
		 * 3.返回到我的购物车
		 */
		return myCart(request, response);
	}
	public String updateQuantity(HttpServletRequest request,HttpServletResponse response){
		try {
			/*
			 * 1.获取参数
			 */
			String itemid = request.getParameter("itemid");
			String quantity = request.getParameter("quantity");
			/**
			 * 2.调用service，更新数量
			 */
			CartBizImpl service = new CartBizImpl();
			Cart bean = service.updateQuantity(itemid,quantity);
			/**
			 * 3.返回数据
			 */
			JSONObject json = new JSONObject();
			json.put("quantity", bean.getQuantity());
			json.put("subtotal", bean.getSubTotal()+"");
			response.getWriter().write(json.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	/**
	 * 向购物车添加商品
	 * @param request
	 * @param response
	 * @return
	 */
	public String addCart(HttpServletRequest request,HttpServletResponse response){
		/**
		 * 1.获取前台提供的数据，封装
		 */
		Cart cartBean = BeanKit.toBean(request.getParameterMap(),Cart.class);
		Book bookBean = BeanKit.toBean(request.getParameterMap(),Book.class);
		/**
		 * 2.补齐user信息
		 */
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			return "r:/jsps/user/login.jsp";
		}
		cartBean.setBook(bookBean);
		cartBean.setUser(user);
		/**
		 * 3.插入数据
		 */
		CartBizImpl service = new CartBizImpl();
		service.addCart(cartBean);
		/**
		 * 4.返回到购物车
		 */
		return myCart(request, response);
	}
	
	public String myCart(HttpServletRequest request,HttpServletResponse response){
		/**
		 * 1.得到uid用户id
		 */
		User userBean = (User) request.getSession().getAttribute("user");
		
		if(userBean==null){
			return "r:/jsps/user/login.jsp";
		}
		
		
		String uid = userBean.getUid();
		/**
		 * 2.用用户的id调用service方法，去后台，查询购物车
		 */
		CartBizImpl service = new CartBizImpl();
		List<Cart> carts = service.myCart(uid);
		/**
		 * 3.保存，返回到前台 /jsps/cart/list.jsp
		 */
		request.setAttribute("carts", carts);
		return "f:/jsps/cart/list.jsp";
	}
}
