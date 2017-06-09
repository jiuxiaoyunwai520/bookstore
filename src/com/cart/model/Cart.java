package com.cart.model;

import java.math.BigDecimal;

import com.book.model.Book;
import com.user.model.User;



public class Cart {

	private String cartItemId;
	private Integer quantity;
	private Book book;
	private User user;
	
	private double subTotal;
	
	public double getSubTotal() {
		//计算
		/**
		 * 第一，java中，凡是涉及到钱的计算，一律用bigDecimal
		 * 第二，bigDecimal必须用字符串的构造方法
		 */
		BigDecimal currentPrice = new BigDecimal(book.getCurrPrice()+"");
		BigDecimal myquantity = new BigDecimal(this.quantity+"");
		BigDecimal mySubTotal = currentPrice.multiply(myquantity); 
		return mySubTotal.doubleValue();
	}
	public String getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public static void main(String[] args) {
		System.out.println(2.0-1.9);
	}
}
