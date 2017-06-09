package com.order.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.book.model.Book;
import com.commons.BeanKit;
import com.dbutils.TxQueryRunner;
import com.order.model.Order;
import com.order.model.Orderitem;
import com.page.Expression;
import com.page.QueryInfo;
import com.page.QueryResult;

public class OrderDaoImpl {

	QueryRunner runner = new TxQueryRunner();

	public QueryResult findOrderByUid(String uid, QueryInfo queryInfo) {
		List<Expression> list = new ArrayList<Expression>();
		list.add(new Expression("uid", "=", uid));
		return queryByPage(list, queryInfo.getStartIndex(), queryInfo.getPageSize());
	}

	public QueryResult queryByPage(List<Expression> list, int startIndex, int pageSize) {
		QueryResult result = new QueryResult();
		try {
			// sql语句 有？
			StringBuilder whereSql = new StringBuilder(" where 1=1 ");
			// 获取参数的list
			List<Object> params = new ArrayList<Object>();
			for (Expression expression : list) {
				whereSql.append(" and ").append(expression.getKey()).append(" ").append(expression.getOperation())
						.append(" ").append("?");
				// 拼接参数
				params.add(expression.getValue());
			}

			String sql = "select count(*) from t_order " + whereSql;
			// 满足条件的数据的总数
			Long count = (Long) runner.query(sql, new ScalarHandler(), params.toArray());
			result.setTotalRecord(Integer.parseInt(count + ""));

			params.add(startIndex);
			params.add(pageSize);

			sql = "select * from t_order " + whereSql + " limit ?,?";
			// sql = "select * from t_book where 1=1 and bname like
			// '%javascript%' limit 0,5";
			List<Order> orderBeans = runner.query(sql, new BeanListHandler<Order>(Order.class), params.toArray());

			for (Order orderBean : orderBeans) {
				List<Orderitem> orderitemBeans = findOrderItemByOid(orderBean.getOid());
				orderBean.setOrderitemBeans(orderitemBeans);
			}
			result.setBeanList(orderBeans);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<Orderitem> findOrderItemByOid(String oid) {
		List<Orderitem> orderitemBeans = new ArrayList<Orderitem>();
		try {
			String sql = "select * from t_orderitem where oid=?";
			List<Map<String, Object>> maps = runner.query(sql, new MapListHandler(), oid);

			for (Map<String, Object> map : maps) {
				Orderitem orderitemBean = BeanKit.toBean(map, Orderitem.class);
				Book book = BeanKit.toBean(map, Book.class);
				orderitemBean.setBook(book);
				orderitemBeans.add(orderitemBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderitemBeans;
	}

	public void add(Order orderBean) {

		try {
			// 1.插入订单信息到订单表
			String sql = "insert into t_order(oid,ordertime,total,status,address,uid) values(?,?,?,?,?,?)";
			Object[] params = { orderBean.getOid(), orderBean.getOrdertime(), orderBean.getTotal(),
					orderBean.getStatus(), orderBean.getAddress(), orderBean.getOwner().getUid() };
			runner.update(sql, params);

			// 2.把订单项信息插入订单项表
			List<Orderitem> orderitemBeans = orderBean.getOrderitemBeans();
			sql = "insert into t_orderitem(orderItemId,quantity,subtotal,bid,bname,currPrice,image_b,oid) values(?,?,?,?,?,?,?,?)";
			for (Orderitem orderitemBean : orderitemBeans) {
				params = new Object[] { orderitemBean.getOrderItemId(), orderitemBean.getQuantity(),
						orderitemBean.getSubtotal(), orderitemBean.getBook().getBid(),
						orderitemBean.getBook().getBname(), orderitemBean.getBook().getCurrPrice(),
						orderitemBean.getBook().getImage_b(), orderBean.getOid() };
				runner.update(sql, params);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Order findOrderById(String oid) {
		Order orderBean = null;
		try {
			// 1.order基本信息
			String sql = "select * from t_order where oid=?";
			orderBean = runner.query(sql, new BeanHandler<Order>(Order.class), oid);

			// 2.orderItem表
			sql = "select * from t_orderitem where oid=?";
			List<Map<String, Object>> listItems = runner.query(sql, new MapListHandler(), oid);

			List<Orderitem> orderitemBeans = new ArrayList<Orderitem>();
			for (Map<String, Object> map : listItems) {
				Orderitem orderitemBean = BeanKit.toBean(map, Orderitem.class);
				Book book = BeanKit.toBean(map, Book.class);
				orderitemBean.setBook(book);

				orderitemBeans.add(orderitemBean);
			}
			orderBean.setOrderitemBeans(orderitemBeans);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return orderBean;
	}

	public void updateStatus(String oid) {
		try {
			String sql = "update t_order set status=2  where oid=?";
			Object[] params = { oid };
			runner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cance(String oid) {
		try {
			String sql = "delete from t_orderitem where oid = ?";
			Object[] params = { oid };
			int update = runner.update(sql, params); 
			if(update>0){
			sql = "delete from t_order where oid = ?";
			Object[] param = { oid };
			runner.update(sql, param);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void buy(String oid) {
		try {
			String sql = "update t_order set status=5  where oid=?";
			Object[] params = { oid };
			runner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
