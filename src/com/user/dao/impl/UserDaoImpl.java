package com.user.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.commons.StrKit;
import com.dbutils.TxQueryRunner;
import com.user.model.User;


public class UserDaoImpl {
	TxQueryRunner runner = new TxQueryRunner();

	public boolean insertUser(User user) {
		try {
			String sql = "insert t_user (uid,loginname,loginpass,email,status,activationCode) value(?,?,?,?,?,?)";
			Object[] params = {StrKit.uuid(), user.getLoginname(), user.getLoginpass(), user.getEmail(),
					"0", StrKit.uuid() };
			int i = runner.update(sql, params);
			if(i>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public User findUserByLoginname(String loginName) {
		try {
			String sql = "select * from t_user where loginName=?";
			Object[] param = { loginName };
			 User user = runner.query(sql, new BeanHandler<User>(User.class), param);
			
				return user;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * 查找用户名是否存在
	 */
	public boolean findIsUserByLoginname(String loginName) {
		try {
			String sql = "select count(*) from t_user where loginName=?";
			Object[] param = { loginName };
		
			long count = (long) runner.query(sql, new ScalarHandler(), param);
			if (count > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * 查找邮箱是否存在
	 */
	public boolean findIsUserByEmail(String email) {
		try {
			String sql = "select count(*) from t_user where email=?";
			Object[] param = { email };

			long count = (long) runner.query(sql, new ScalarHandler(), param);
			if (count > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/*
	 * 判断是否可以登录
	 */
	/**
	 * 根据用户名和密码，查询用户
	 * @param loginname
	 * @param loginpass
	 * @return
	 */
	public User findUserByUserNameAndPass(String loginname, String loginpass) {
		String sql = "select * from t_user where loginname=? and loginpass=?";
		Object[] params = {loginname,loginpass};
		try {
			return runner.query(sql,  new BeanHandler<User>(User.class), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 根据激活码，获取用户
	 * @param activationCode
	 * @return
	 */
	public User findUserByActivationCode(String activationCode) {
		User user  = null;
		try {
			String sql = "select * from t_user where activationCode=?";
			Object[] params = {activationCode};
			user = runner.query(sql, new BeanHandler<User>(User.class),params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	/**
	 * 根据激活码，激活账户
	 * @param activationCode
	 * @param i
	 */
	public void updateStatusByActivationCode(String activationCode, int i) {
		try {
			String sql = "update t_user set status=? where activationCode=?";
			Object[] params = {i,activationCode};
			runner.update(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改密码
	 * @param uid
	 * @param newpass
	 * @return
	 */
	public boolean updatePass(String uid, String newpass) {
		String sql = "update t_user set loginpass=? where uid=?";
		Object[] params = {newpass,uid};
		try {
		   runner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	public boolean findUserByLoginpasse(String loginpass, String uid) {
		try {
			
			String sql = "select loginpass from t_user where uid=?";
			Object[] param = { uid };
			String pass = (String) runner.query(sql, new ScalarHandler(), param);
		
			if (pass.trim().equals(loginpass) ) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
