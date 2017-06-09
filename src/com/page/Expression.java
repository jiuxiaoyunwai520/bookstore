package com.page;
/**
 * 拼接whereSql语句用的bean
 * @author XD
 *
 */
public class Expression {

	private String key;//数据库表中的字段
	private String operation;//操作 like = 
	private  String value;//值
	
	public Expression(String key, String operation, String value) {
		super();
		this.key = key;
		this.operation = operation;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
