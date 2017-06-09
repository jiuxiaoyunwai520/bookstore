package com.book.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.book.model.Book;
import com.dbutils.TxQueryRunner;
import com.page.Expression;
import com.page.QueryInfo;
import com.page.QueryResult;


public class BookDaoImpl {

	QueryRunner runner = new TxQueryRunner();
	/**
	 * 
	 * @param cid
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public QueryResult findBookByCid(QueryInfo info) {
		List<Expression> list = new ArrayList<Expression>();
		list.add(new Expression("cid", "=", info.getCid()));
		return  queryByPage(list,info.getStartIndex(),info.getPageSize());
	}
	//通过书名
	public QueryResult findBookByName(QueryInfo info) {
		List<Expression> list = new ArrayList<Expression>();
		list.add(new Expression("bname", "like", "%"+info.getBname()+"%"));//直接拼接sql，需要加''，但是作为参数，不能加
		return  queryByPage(list,info.getStartIndex(),info.getPageSize());
	}

	public QueryResult queryByPage(List<Expression> list, int startIndex, int pageSize) {
		QueryResult result = new QueryResult();
		try {
			//sql语句  有？
			StringBuilder whereSql = new StringBuilder(" where 1=1 ");
			//获取参数的list
			List<Object> params = new ArrayList<Object>();
			for (Expression expression : list) {
				whereSql.append(" and ").append(expression.getKey())
				.append(" ")
				.append(expression.getOperation())
				.append(" ")
				.append("?");
				//拼接参数
				params.add(expression.getValue());
			}
			
			String sql = "select count(*) from t_book "+whereSql;
			//满足条件的数据的总数
			Long count = (Long) runner.query(sql, new ScalarHandler(),params.toArray());
			result.setTotalRecord(Integer.parseInt(count+""));
			
			params.add(startIndex);
			params.add(pageSize);
			
			sql = "select * from t_book "+whereSql+" limit ?,?";
			//sql = "select * from t_book where 1=1  and bname like '%javascript%' limit 0,5";
			List<Book> books = runner.query(sql, new BeanListHandler<Book>(Book.class),params.toArray());
		
			result.setBeanList(books);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 按照出版社查询
	 * @param info
	 * @return
	 */
	//通过出版社
	public QueryResult findBookByPress(QueryInfo info) {
		List<Expression> list = new ArrayList<Expression>();
		list.add(new Expression("press", "=", info.getPress()));
		return  queryByPage(list,info.getStartIndex(),info.getPageSize());
	}
	//通过作者查询
	public QueryResult findBookByAuthor(QueryInfo info) {
		List<Expression> list = new ArrayList<Expression>();
		list.add(new Expression("author", "=", info.getAuthor()));
		return  queryByPage(list,info.getStartIndex(),info.getPageSize());
	}
	public QueryResult findBookByCom(QueryInfo info) {
		List<Expression> list = new ArrayList<Expression>();
		if(!(null==info.getAuthor()||"".equals(info.getAuthor().trim()))){
			list.add(new Expression("author", "like", "%"+info.getAuthor()+"%"));
		}
		if(!(null==info.getBname()||"".equals(info.getBname().trim()))){
			list.add(new Expression("bname", "like", "%"+info.getBname()+"%"));
		}
		if(!(null==info.getPress()||"".equals(info.getPress().trim()))){
			list.add(new Expression("press", "like", "%"+info.getPress()+"%"));
		}
		return  queryByPage(list,info.getStartIndex(),info.getPageSize());
	}
	public Book findBookById(String bid) {
		Book book = null;
		try {
			String sql = "select * from t_book where bid = ?";
			book = runner.query(sql, new BeanHandler<Book>(Book.class),bid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return book;
	}
	/*
	 * 模糊查询
	 */
	public QueryResult findBookByQuery(QueryInfo queryInfo) {
		ArrayList<Expression> list = new ArrayList<Expression>();
		String bname = queryInfo.getBname();
		String author = queryInfo.getAuthor();
		String press = queryInfo.getPress();
	
		if (!(null == bname || "".equals(bname.trim()))) {
			list.add(new Expression("bname", "like", "%" + bname + "%"));
		}
		if (!(null == author || "".equals(author.trim()))) {
			list.add(new Expression("author", "like", "%" + author + "%"));
		}
		if (!(null == press || "".equals(press.trim()))) {
			list.add(new Expression("press", "like", "%" + press + "%"));
		}
		return finInfo(list, queryInfo.getStartIndex(), queryInfo.getPageSize());
	}
	
	public QueryResult finInfo(List<Expression> list, int startIndex, int pageSize) {
		QueryResult queryResult = new QueryResult();
		StringBuilder wheresql = new StringBuilder(" where 1=1 ");
		ArrayList<Object> params = new ArrayList<>();
		for (Expression expression : list) {
			wheresql.append(" and ").append(expression.getKey()).append(" ").append(expression.getOperation())
					.append(" ").append("?");

			params.add(expression.getValue());
		}
		try {
			String sqlcount = "select count(*) from t_book " + wheresql;

			long totalRecord = (long) runner.query(sqlcount, new ScalarHandler(), params.toArray());
			queryResult.setTotalRecord(Integer.parseInt(totalRecord + ""));

			String sql = "select * from t_book " + wheresql + " limit ?,?";
			params.add(startIndex);
			params.add(pageSize);
			List<Book> BookBean = runner.query(sql, new BeanListHandler<Book>(Book.class),
					params.toArray());
			queryResult.setBeanList(BookBean);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return queryResult;

	}

}
