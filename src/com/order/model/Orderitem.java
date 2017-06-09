package com.order.model;

import com.book.model.Book;

public class Orderitem {

	private String orderItemId;//主键
	private int quantity;//数量
	private double subtotal;//小计
	private Book book;//所关联的Book
	private Order orderbean;//所属的订单
	
	public Order getOrderbean() {
		return orderbean;
	}
	public void setOrderbean(Order orderbean) {
		this.orderbean = orderbean;
	}
	public String getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
}
