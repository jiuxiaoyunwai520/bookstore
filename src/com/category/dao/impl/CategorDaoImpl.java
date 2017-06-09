package com.category.dao.impl;

import java.util.List;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.category.model.Category;
import com.dbutils.TxQueryRunner;

public class CategorDaoImpl {

	QueryRunner runner = new TxQueryRunner();
/*	@Test
	public void test(){
		findAll();
	}*/
	/**
	 * 查询所有分类数据
	 * @return
	 */
	public List<Category> findAll(){
		List<Category> parents = null;
		try {
			
		
			String sql = "select * from t_category where pid is null order by orderBy";
			parents = runner.query(sql,new BeanListHandler<Category> (Category.class));
			
			for (Category categoryBean : parents) {
				sql= "select * from t_category where pid=? order by orderBy";
				List<Category> children = runner.query(sql,new BeanListHandler<Category> (Category.class),categoryBean.getCid());
				categoryBean.setChildren(children);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parents;
	}
}
