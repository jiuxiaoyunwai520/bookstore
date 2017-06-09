package com.category.web;

import java.util.List;

import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.biz.impl.CategoryBizImpl;
import com.category.model.Category;
import com.servlet.BaseServlet;



public class CategoryAction extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public String findAll(HttpServletRequest request,HttpServletResponse response){
		/**
		 * 1.查询所有分类
		 */
		CategoryBizImpl service = new CategoryBizImpl();
		List<Category> parents = service.findAll();
		request.setAttribute("parents", parents);

		request.setAttribute("isFirst", "isFirst");
		return "f:/jsps/section/left.jsp";
	}
	
}
