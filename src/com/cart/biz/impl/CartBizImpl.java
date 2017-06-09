package com.cart.biz.impl;

import java.util.List;

import com.cart.dao.impl.CartDaoImpl;
import com.cart.model.Cart;
import com.commons.StrKit;



public class CartBizImpl {

	CartDaoImpl dao = new CartDaoImpl();
	public List<Cart> myCart(String uid) {
		return dao.findCartByUid(uid);
	}
	/**
	 * 原则：凡是插入直线，都要判断，是否已经有该条数据
	 * @param cartBean
	 */
	public void addCart(Cart cartBean) {
		/**
		 * 1.首先，查看购物车，是否有该商品
		 */
		Cart resultBean = dao.findCartItemByUidAndBid(cartBean.getUser().getUid(),cartBean.getBook().getBid());
		/**
		 * 2.如果有，更新数量
		 */
		if(resultBean!=null){
			dao.updateQuantity(resultBean.getCartItemId(),resultBean.getQuantity()+cartBean.getQuantity());
		}else{
			/**
			 * 3.如果没有，插入数据
			 */
			cartBean.setCartItemId(StrKit.uuid());
			dao.addCartItem(cartBean);
		}
		
		
	}
	public Cart updateQuantity(String itemid, String quantity) {
		dao.updateQuantity(itemid, Integer.parseInt(quantity));
		return dao.findCartItemById(itemid);
	}
	
	public void batchDelete(String cartItemIds) {
		dao.batchDelete(cartItemIds);
	}
	public List<Cart> loadCartItems(String cartItemIds) {
		return dao.loadCartItems(cartItemIds);
	}

}
