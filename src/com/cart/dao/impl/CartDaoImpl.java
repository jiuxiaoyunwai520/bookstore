package com.cart.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.book.model.Book;
import com.cart.model.Cart;
import com.commons.BeanKit;
import com.dbutils.TxQueryRunner;
import com.user.model.User;



public class CartDaoImpl {

	QueryRunner runner = new TxQueryRunner();
	/**
	 * 根据用户的id，查询出该用户购物车中全部商品
	 * @param uid
	 * @return
	 */
	public List<Cart> findCartByUid(String uid) {
		List<Cart> Carts = new ArrayList<Cart>();
		try {
			String sql = "select * from t_cartitem,t_book where uid=? and t_cartitem.bid=t_book.bid";
			List<Map<String, Object>> list = runner.query(sql, new MapListHandler(), uid);
			
			for (Map<String, Object> map : list) {
				
				Cart cartItem = BeanKit.toBean(map, Cart.class);
				
				
				Book Book = BeanKit.toBean(map, Book.class);
				cartItem.setBook(Book);
				
				User User = BeanKit.toBean(map, User.class);
				cartItem.setUser(User);
				
				Carts.add(cartItem);
			}
		} catch (Exception e) {
		}
		return Carts;
	}
	/**
	 * 根据用户id和书id查询购物车商品数据
	 * @param uid
	 * @param bid
	 * @return
	 */
	public Cart findCartItemByUidAndBid(String uid, String bid) {
		Cart cart = null;
		try {
			String sql = "select * from t_cartitem where uid=? and bid=?";
			Object[] params= {uid,bid};
			cart = runner.query(sql, new BeanHandler<Cart>(Cart.class), params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cart;
	}
	public void updateQuantity(String cartItemId, int quantity) {
		try {
			String  sql = "update t_cartitem set quantity=? where cartItemId=?";
			Object[] params= {quantity,cartItemId};
			runner.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void addCartItem(Cart cart) {
		try {
			String sql = "insert into t_cartitem(cartItemId,quantity,bid,uid) values(?,?,?,?)";
			Object[] params= {cart.getCartItemId(),cart.getQuantity(),cart.getBook().getBid(),cart.getUser().getUid()};
			runner.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Cart findCartItemById(String itemid) {
		Cart cartItem = null;
		try {
			String sql = "select * from t_cartitem,t_book where cartItemId=? and t_cartitem.bid=t_book.bid";
			Map<String, Object> map = runner.query(sql, new MapHandler(), itemid);
			cartItem = BeanKit.toBean(map, Cart.class);
			Book Book = BeanKit.toBean(map, Book.class);
			cartItem.setBook(Book);
				
			User User = BeanKit.toBean(map, User.class);
			cartItem.setUser(User);
		} catch (Exception e) {
		}
		return cartItem;
	}
	/**
	 * 批量删除
	 * @param cartItemIds
	 */
	public void batchDelete(String cartItemIds) {
		try {
			Object[] mycartItemIds = cartItemIds.split(",");
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < mycartItemIds.length; i++) {
				builder.append("?").append(",");
			}
			String params = builder.substring(0, builder.length()-1);
			String sql = "delete from t_cartitem where cartItemId in("+params+")";
			runner.update(sql,mycartItemIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据ids获取多条购物车数据
	 * @param cartItemIds
	 * @return
	 */
	public List<Cart> loadCartItems(String cartItemIds) {
		List<Cart> Carts = new ArrayList<Cart>();
		try {
			Object[] mycartItemIds = cartItemIds.split(",");
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < mycartItemIds.length; i++) {
				builder.append("?").append(",");
			}
			String params = builder.substring(0, builder.length()-1);
			String sql = "select * from t_cartitem,t_book where cartItemId in ("+params+") and t_cartitem.bid=t_book.bid";
			
			List<Map<String, Object>> list = runner.query(sql, new MapListHandler(), mycartItemIds);
			
			for (Map<String, Object> map : list) {
				
				Cart cartItem = BeanKit.toBean(map, Cart.class);
				Book book = BeanKit.toBean(map, Book.class);
				cartItem.setBook(book);
				
				User user = BeanKit.toBean(map, User.class);
				cartItem.setUser(user);
				
				Carts.add(cartItem);
			}
		} catch (Exception e) {
		}
		return Carts;
	}

}
