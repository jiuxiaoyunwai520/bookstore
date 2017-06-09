package com.user.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commons.BeanKit;
import com.servlet.BaseServlet;
import com.user.biz.impl.UserBizImpl;
import com.user.model.User;


public class UserAction extends BaseServlet {

	private static final long serialVersionUID = 1L;
	UserBizImpl uBiz = new UserBizImpl();

	/*
	 * 注册用户名信息
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) {

		Map<String, String[]> map = request.getParameterMap();
		User user = BeanKit.toBean(map, User.class);

		Map<String, String> validateRegist = this.validateRegist(request, response, user);
		if (validateRegist == null || validateRegist.size() > 0) {
			request.setAttribute("errors", validateRegist);
			request.setAttribute("user", user);
			return "f:/jsps/user/regist.jsp";
		}

		boolean flag = uBiz.insertUser(user);
		if (flag) {
			request.setAttribute("code", "success");
			request.setAttribute("msg", "恭喜您，注册成功");
		} else {
			request.setAttribute("code", "error");
			request.setAttribute("msg", "注册错误，请稍后重试");
		}

		return "f:/jsps/msg.jsp";
	}

	public Map<String, String> validateRegist(HttpServletRequest request, HttpServletResponse response, User user) {
		Map<String, String> map = new HashMap<String, String>();
		/**
		 * 用户名校验
		 */
		if (user.getLoginname() == null || user.getLoginname().equals("")) {
			map.put("loginname", "用户名不能为空");
		} else if (user.getLoginname().length() < 2 || user.getLoginname().length() > 20) {
			map.put("loginname", "用户名长度必须在2 ~ 20之间");
		} else if (uBiz.findIsUserByLoginname(user)) {
			map.put("loginname", "用户名已被注册");
		}
		/**
		 * 登录密码校验
		 */

		if (user.getLoginpass() == null || user.getLoginpass().trim().equals("")) {
			map.put("loginpass", "密码不能为空");
		} else if (user.getLoginpass().length() < 3 || user.getLoginpass().length() > 10) {
			map.put("loginpass", "密码长度必须在3 ~ 10之间");
		}
		/**
		 * 确认密码校验
		 */

		if (user.getReloginpass() == null || user.getReloginpass().trim().equals("")) {
			map.put("reloginpass", "确认密码不能为空");
		} else if (!(user.getReloginpass().equals(user.getLoginpass()))) {
			map.put("reloginpass", "两次密码不一致");
		}
		/**
		 * Email校验
		 */
		if (user.getEmail() == null || user.getEmail().trim().equals("")) {
			map.put("email", "Email不能为空	");
			// ^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$
		} else if (user.getEmail().matches("/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$/")){
			map.put("email", "Email格式错误");
		} /*else if (bizImpl.findUserByLoginEmail(user)) {
			map.put("email", "email已经被注册");
		}*/
		/**
		 * 验证码校验
		 */
		String vCode = (String) request.getSession().getAttribute("vCode");
		String verifyCode = request.getParameter("verifyCode");
		if (verifyCode == null || verifyCode.trim().equals("")) {
			map.put("verifyCode", "验证码不能为空");
		} else if (!(verifyCode.equalsIgnoreCase(vCode))) {
			map.put("verifyCode", "验证码错误");
		}
		return map;
	}
	/*
	 * *********************************************************************************************************
	 */
	
	public String login(HttpServletRequest request,HttpServletResponse response){
		/**
		 * 1.封装前台数据
		 */
		/*String parameter = request.getParameter("loginname");
		Map parameterMap = request.getParameterMap();*/
		User user = BeanKit.toBean(request.getParameterMap(), User.class);
		/**
		 * 2.校验（数据格式）
		 */
		Map<String, String> mapError = validateLogin(user,request);
		if(mapError!=null&&mapError.size()>0){
			request.setAttribute("errors",mapError);
			request.setAttribute("user", user);
			return "f:/jsps/user/login.jsp";
		}
		/**
		 * 3.通过用户名密码查询数据库
		 */		
		 user = uBiz.login(user);
			
		/**
		 * user的校验
		 */
		if(user==null){
			request.setAttribute("msg","用户名或密码错误");
			request.setAttribute("user",user);
			return "f:/jsps/user/login.jsp";
		}else if(user.getStatus()==0){
			uBiz.sendMail(user);
			request.setAttribute("code", "error");
			request.setAttribute("msg", "亲,请先激活，再登录！");
		    return "f:/jsps/msg.jsp";
		}else{
			request.getSession().setAttribute("user",user );
			return "r:/index.jsp";
		}
	}
	/**
	 * 激活账户
	 * @param request
	 * @param response
	 * @return
	 */
	public String activation(HttpServletRequest request,HttpServletResponse response){
		try {
			/**
			 * 1.获取激活码
			 */
			String activationCode = request.getParameter("activationCode");
			/**
			 * 2.用激活码激活
			 */
			
			uBiz.activate(activationCode);
			/**
			 * 3.保存信息，给用户
			 */
			request.setAttribute("code", "success");
			request.setAttribute("msg", "恭喜您，激活成功");
			return "f:/jsps/msg.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("code", "error");
			request.setAttribute("msg", e.getMessage());
			return "f:/jsps/msg.jsp";
		}
	}
	private Map<String, String> validateLogin(User user, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();		
		/**
		 * 1.校验用户名
		 *   用户名不能为空；
			       用户名长度必须在2 ~ 20之间；
			       用户名已被注册。
		 */
		String loginname = user.getLoginname();
		String loginpass = user.getLoginpass();
		String verifyCode = request.getParameter("verifyCode");
		String vCode = (String) request.getSession().getAttribute("vCode");
		if(loginname==null||loginname.trim().equals("")){
			map.put("loginname", "用户名不能为空");
		}else if(loginname.length()<2||loginname.length()>20){
			map.put("loginname", "用户名长度必须在2到20之间");
		}	
		else if (loginpass == null || loginpass.equals("")) {
			map.put("loginname", "密码不能为空");
		}else if (verifyCode == null || verifyCode.equals("")) {
			map.put("verifyCode", "验证不能为空");
			
		} else if (!(verifyCode.equalsIgnoreCase(vCode))) {
			map.put("verifyCode", "验证码错误！");
		} 

		return map;
	}
	/**
	 * ****************************************************************************************************
	 */
	
	/**
	 * 校验用户名是否被注册，如果已经注册，返回false
	 * @param request
	 * @param response
	 * @return
	 */	
	public String validateLoginname(HttpServletRequest request,HttpServletResponse response){
		try {
				/**
				 * 1.获取用户名参数
				 */
			Map<String, String[]> map = request.getParameterMap();
			User user = BeanKit.toBean(map, User.class);
				/**
				 * 2.校验
				 */
			    boolean flag =	uBiz.findIsUserByLoginname(user);
				/**
				 * 3.返回数据
				 */
			    response.getWriter().write(!flag+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 校验email是否被注册，如果已经注册，返回false
	 * @param request
	 * @param response
	 * @return
	 */
	public String validateEmail(HttpServletRequest request,HttpServletResponse response){
		try {
			/**
			 * 1.获取email参数
			 */
			Map<String, String[]> map = request.getParameterMap();
			User user = BeanKit.toBean(map, User.class);
			/**
			 * 2.校验
			 */
			boolean flag =	uBiz.findIsUserByEmail(user);
			/**
			 * 3.返回数据
			 */
			response.getWriter().write(!flag+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 校验验证码
	 * @param request
	 * @param response
	 * @return
	 */
	public String validateVerifyCode(HttpServletRequest request,HttpServletResponse response){
	   try {
		   //用户手输入验证码
			String verifyCode = request.getParameter("verifyCode");
			
			String vCode = (String) request.getSession().getAttribute("vCode");
			if(verifyCode.equalsIgnoreCase(vCode)){
				  response.getWriter().write(true+"");
			}else{
				 response.getWriter().write(false+"");
			}
			
	   } catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @return
	 */
	public String updatePass(HttpServletRequest request,HttpServletResponse response){
	    try {
				/**
				 * 1.获取数据，并封装之
				 */
				@SuppressWarnings("unchecked")
				User bean = BeanKit.toBean(request.getParameterMap(),User.class);
				/**
				 * 2.校验数据（格式）
				 */
				Map<String, String> mapError = validatePassData(bean,request);
				if(mapError!=null&&mapError.size()>0){
					request.setAttribute("errors",mapError);
					return "f:/jsps/user/pwd.jsp";
				}
				/**
				 * 3.校验（老密码是否输入正确）
				 */
				User sessionUser = (User) request.getSession().getAttribute("user");
				if(sessionUser==null){
					//没有登陆，禁止修改
					request.setAttribute("msg","您还没有登陆，请先登陆");
					return "f:/jsps/user/login.jsp";
				}
				
				if(!sessionUser.getLoginpass().equals(bean.getLoginpass())){
					//你输入的原密码是否正确
					mapError.put("loginpass", "原密码错误");
					request.setAttribute("errors",mapError);
					return "f:/jsps/user/pwd.jsp";
				}
				
				//允许修改密码
				UserBizImpl service = new UserBizImpl();
				boolean flag = service.updatePass(sessionUser.getUid(),bean.getNewpass());
				if(flag){
					this.quit(request, response);
					request.setAttribute("code", "success");
					request.setAttribute("msg", "密码修改成功，请重新登陆！");
				    return "f:/jsps/msg.jsp";
				}
	    } catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("code", "error");
			request.setAttribute("msg", "对不起，你不能修改密码！");
		    return "f:/jsps/msg.jsp";
		}
		return null;
	}
	/*
	 * 检验格式
	 */
private Map<String, String> validatePassData(User user, HttpServletRequest request) {
	Map<String, String> map = new HashMap<String, String>();
	/*
	 * 原密码
	 */
	String loginpass = request.getParameter("loginpass");
	if (loginpass == null || loginpass.equals("")) {
		map.put("loginpass", "密码不能为空！");
	} else if (loginpass.length() < 3 || loginpass.length() > 10) {
		map.put("loginpass", "密码的长度必须在3~10之间");
	}
	/*
	 * 新密码
	 */
	String newpass = request.getParameter("newpass");
	if (newpass == null || newpass.equals("")) {
		map.put("newpass", "新密码不能为空！");
	} else if (newpass.length() < 3 || newpass.length() > 10) {
		map.put("newpass", "新密码的长度必须在3~10之间");
	}
	/*
	 * 确认新密码
	 */
	String reloginpass = request.getParameter("reloginpass");
	if (reloginpass == null || reloginpass.equals("")) {
		map.put("reloginpass", "确认密码不能为空");
	} else if (!(reloginpass.equals(newpass))) {
		map.put("reloginpass", "两次输入不一致");
	}
	/*
	 * 验证码
	 */
	String verifyCode = request.getParameter("verifyCode");
	String vCode = (String) request.getSession().getAttribute("vCode");
	if (verifyCode == null || verifyCode.equals("")) {
		map.put("verifyCode", "验证码不能为空");
	} else if (!(verifyCode.equalsIgnoreCase(vCode))) {
		map.put("verifyCode", "验证不正确");
	}
	return map;

}			
public void validateLoginpass(HttpServletRequest request, HttpServletResponse response) {
	try {
		String loginpass = request.getParameter("loginpass");
		User sessionUser = (User) request.getSession().getAttribute("user");
		UserBizImpl service = new UserBizImpl();
		boolean flag = service.findUserByLoginpasse(loginpass, sessionUser.getUid());
		response.getWriter().write(flag + "");

	} catch (IOException e) {
		e.printStackTrace();
	}
}

public String quit(HttpServletRequest request,HttpServletResponse response){
		
		request.getSession().invalidate();
        return "r:/jsps/user/login.jsp";
	}
}