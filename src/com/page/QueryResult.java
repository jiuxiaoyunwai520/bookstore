package com.page;

import java.util.List;


/**
       分页时，从数据库查询出来的数据
 * @author XD
 *
 */
public class QueryResult {

	private List beanList;//页面的数据
	private int totalRecord;//共多少条
	public List getBeanList() {
		return beanList;
	}
	public void setBeanList(List beanList) {
		this.beanList = beanList;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	
}
