package com.order.biz.impl;


import com.order.dao.impl.OrderDaoImpl;
import com.order.model.Order;
import com.page.PageBean;
import com.page.QueryInfo;
import com.page.QueryResult;

public class OrderBizImpl {

	OrderDaoImpl dao = new OrderDaoImpl();
	public PageBean findOrderByUid(String uid, QueryInfo queryInfo) {
		QueryResult result =  dao.findOrderByUid(uid,queryInfo);
		return getPageBean(queryInfo,result);
	}
	
	public PageBean getPageBean(QueryInfo info,QueryResult result){
		PageBean pageBean = new PageBean();
		pageBean.setBeanList(result.getBeanList());
		pageBean.setCurrentPage(info.getCurrentPage());
		pageBean.setPageSize(info.getPageSize());
		pageBean.setTotalRecord(result.getTotalRecord());
		return pageBean;
	}

	public void add(Order orderBean) {
		dao.add(orderBean);
	}

	public Order findOrderById(String oid) {
		return dao.findOrderById(oid);
	}

	public void updateStatus(String oid) {
		 dao.updateStatus(oid);	
	}

	public void cancel(String oid) {
		// TODO Auto-generated method stub
	 dao.cance(oid);
	}

	public void buy(String oid) {
		// TODO Auto-generated method stub
		dao.buy(oid);
	}

	

}
