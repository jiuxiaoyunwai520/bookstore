package com.book.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.biz.impl.BookBizImpl;
import com.book.model.Book;
import com.commons.BeanKit;
import com.page.PageBean;
import com.page.QueryInfo;
import com.servlet.BaseServlet;


public class BookAction extends BaseServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String findBookById(HttpServletRequest request,HttpServletResponse response){
		String bid = request.getParameter("bid");
		BookBizImpl service = new BookBizImpl();
		Book bookBean = service.findBookById(bid);
		request.setAttribute("book", bookBean);
		return "f:/jsps/book/desc.jsp";
	}
	/**
	 * 按照出版社查询
	 * @param request
	 * @param response
	 * @return
	 */
	public String findBookByCom(HttpServletRequest request,HttpServletResponse response){
		QueryInfo queryInfo = BeanKit.toBean(request.getParameterMap(),QueryInfo.class);
		BookBizImpl service = new BookBizImpl();
		PageBean pageBean = service.findBookByCom(queryInfo); 
		setUrlToPageBean(request,pageBean);
		return "f:/jsps/book/list.jsp";
	}
	/**
	 * 按照出版社查询
	 * @param request
	 * @param response
	 * @return
	 */
	public String findBookByAuthor(HttpServletRequest request,HttpServletResponse response){
		QueryInfo queryInfo = BeanKit.toBean(request.getParameterMap(),QueryInfo.class);
		BookBizImpl service = new BookBizImpl();
		PageBean pageBean = service.findBookByAuthor(queryInfo); 
		setUrlToPageBean(request,pageBean);
		return "f:/jsps/book/list.jsp";
	}
	/**
	 * 按照出版社查询
	 * @param request
	 * @param response
	 * @return
	 */
	public String findBookByPress(HttpServletRequest request,HttpServletResponse response){
		QueryInfo queryInfo = BeanKit.toBean(request.getParameterMap(),QueryInfo.class);
		BookBizImpl service = new BookBizImpl();
		PageBean pageBean = service.findBookByPress(queryInfo); 
		setUrlToPageBean(request,pageBean);
		return "f:/jsps/book/list.jsp";
	}
	/**
	 * 根据bookname查询数据（分页），模糊查询
	 * @param request
	 * @param response
	 * @return
	 */
	public String findBookByName(HttpServletRequest request,HttpServletResponse response){
		QueryInfo queryInfo = BeanKit.toBean(request.getParameterMap(),QueryInfo.class);
		BookBizImpl service = new BookBizImpl();
		PageBean pageBean = service.findBookByName(queryInfo); 
		setUrlToPageBean(request,pageBean);
		return "f:/jsps/book/list.jsp";
	}
	public String findBookByCid(HttpServletRequest request,HttpServletResponse response){
		/**
		 * 1.获取前台参数
		 *   前台如何把数据传给后台：1）form表单
		 *                     2）ajax
		 *                     3）<a></a>
		 */
		QueryInfo queryInfo = BeanKit.toBean(request.getParameterMap(),QueryInfo.class);
		/**
		 * 2.根据前台数据查询数据库
		 */
		BookBizImpl service = new BookBizImpl();
		PageBean pageBean = service.findBookByCid(queryInfo);
		setUrlToPageBean(request,pageBean);
		return "f:/jsps/book/list.jsp";
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
	/*
	 * 模糊查询
	 */
	public String findBookByQuery(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String[]> map = request.getParameterMap();
		QueryInfo queryInfo = BeanKit.toBean(map, QueryInfo.class);
		BookBizImpl service = new BookBizImpl();
		PageBean pageBean = service.findBookByQuery(queryInfo);
		this.setUrlToPageBean(request, pageBean);

		return "f:/jsps/book/list.jsp";

	}
}
