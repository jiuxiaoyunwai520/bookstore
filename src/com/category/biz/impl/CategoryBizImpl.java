package com.category.biz.impl;

import java.util.List;

import com.category.dao.impl.CategorDaoImpl;
import com.category.model.Category;


public class CategoryBizImpl {

	CategorDaoImpl dao = new CategorDaoImpl();
	public List<Category> findAll(){
		return dao.findAll();
	}
			
}
