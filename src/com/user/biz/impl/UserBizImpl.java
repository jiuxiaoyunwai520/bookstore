package com.user.biz.impl;


import com.mail.Mail;
import com.mail.MailKit;
import com.user.dao.impl.UserDaoImpl;
import com.user.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;

public class UserBizImpl {

	UserDaoImpl dao = new UserDaoImpl();

	/*
	 * 注册用户名信息
	 */
	public boolean insertUser(User user) {
		boolean flag = dao.insertUser(user);
		sendMail(user);
		return flag;
	}

	/*
	 * 发送邮件
	 */
	public void sendMail(User user) {
		user = dao.findUserByLoginname(user.getLoginname());
		Properties properties = null;
		try {
			properties = new Properties();
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("email_template.properties");

			properties.load(is);

			String host = properties.getProperty("host");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");
			Session session = MailKit.createSession(host, username, password);

			String from = properties.getProperty("from");
			String subject = properties.getProperty("subject");
			String content = properties.getProperty("content");

			content = MessageFormat.format(content, user.getActivationCode());
			Mail mail = new Mail(from, user.getEmail(), subject, content);

			MailKit.send(session, mail);
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 查找用户名是否存在
	 */
	public boolean findIsUserByLoginname(User user) {
		boolean flag = dao.findIsUserByLoginname(user.getLoginname());
		return flag;
	}

	/*
	 * 查找邮箱是否存在
	 */
	public boolean findIsUserByEmail(User user) {
		boolean flag = dao.findIsUserByEmail(user.getEmail());
		return flag;
	}

	/*
	 * 登录
	 */
	public User login(User user) {
		user = dao.findUserByUserNameAndPass(user.getLoginname(), user.getLoginpass());
		return user;
	}

	public void activate(String activationCode) {
		/**
		 * 1.根据激活码，获取用户
		 */
		User user = dao.findUserByActivationCode(activationCode);
		/**
		 * 2.如果激活码，没有对应的账户，说明激活码无效
		 */
		if(user==null){ throw new RuntimeException("激活码无效"); }
		/**
		 * 3.如果账户已经激活，通知用户，您已激活成功，不要重复激活
		 */
		if(user.getStatus()==1){ throw new RuntimeException("您已激活成功，不要重复激活");}
		/**
		 * 4.需要激活
		 */
		dao.updateStatusByActivationCode(activationCode,1);
	}
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean updatePass(String uid, String newpass) {
		return dao.updatePass(uid,newpass);
	}

	public boolean findUserByLoginpasse(String loginpass, String uid) {
		// TODO Auto-generated method stub
		return  dao.findUserByLoginpasse(loginpass,uid);
	}
}
