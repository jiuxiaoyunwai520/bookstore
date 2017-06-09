package com.book.biz.impl;

import java.util.List;

import com.book.dao.impl.BookDaoImpl;
import com.book.model.Book;
import com.page.PageBean;
import com.page.QueryInfo;
import com.page.QueryResult;


public class BookBizImpl {

	BookDaoImpl dao = new BookDaoImpl();
	public PageBean findBookByCid(QueryInfo info){
		QueryResult result = dao.findBookByCid(info);
		return getPageBean(info,result);
	}
	public PageBean findBookByName(QueryInfo info) {
		QueryResult result = dao.findBookByName(info);
		return getPageBean(info,result);
	}
	
	public PageBean getPageBean(QueryInfo info,QueryResult result){
		PageBean pageBean = new PageBean();
		pageBean.setBeanList(result.getBeanList());
		pageBean.setCurrentPage(info.getCurrentPage());
		pageBean.setPageSize(info.getPageSize());
		pageBean.setTotalRecord(result.getTotalRecord());
		return pageBean;
	}
	public PageBean findBookByPress(QueryInfo info) {
		QueryResult result = dao.findBookByPress(info);
		return getPageBean(info,result);
	}
	public PageBean findBookByAuthor(QueryInfo info) {
		QueryResult result = dao.findBookByAuthor(info);
		return getPageBean(info,result);
	}
	public PageBean findBookByCom(QueryInfo info) {
		QueryResult result = dao.findBookByCom(info);
		return getPageBean(info,result);
	}
	public Book findBookById(String bid) {
	
		return dao.findBookById(bid);
	}
	public PageBean findBookByQuery(QueryInfo queryInfo) {
		QueryResult queryResult = dao.findBookByQuery(queryInfo);
		return getPageBean(queryInfo,queryResult);

	}
}
