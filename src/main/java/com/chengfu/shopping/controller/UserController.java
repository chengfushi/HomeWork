package com.chengfu.shopping.controller;


import com.chengfu.shopping.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Cheng Fu
 * @description: TODO
 * @date 2025/5/9 10:32
 */
@WebServlet("/user/*")
public class UserController extends BasicController{
	//创建一个user对象
	private final User user = new User();

	//创建一个常量表示用户登录态
	private static final String USER_LOGIN = "userLogin";

	//创建两个常量，假设这是正确的用户名和密码
	private static final String USER_NAME = "admin";
	private static final String USER_PASSWORD = "123456";

	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userName = req.getParameter("userName");
		String userPassword = req.getParameter("userPassword");

		//验证逻辑是否合法
		if (userName == null || userPassword == null) {
			//重定向到登录页面
			resp.sendRedirect(req.getContextPath() +"/login.html");
			return;
		}

		if (userName.equals(USER_NAME) && userPassword.equals(USER_PASSWORD)){
			user.setUserName(userName);
			user.setUserPassword(userPassword);
			//表示登录成功，将用户信息存储到session中
			req.getSession().setAttribute(USER_LOGIN, user);

			//重定向到shopping页面
			resp.sendRedirect(req.getContextPath() + "/shopping");

		}else {
			//登录失败，重定向到login登录页面
			resp.sendRedirect(req.getContextPath() + "/login.html");
		}
	}
}
