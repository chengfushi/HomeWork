package com.chengfu.shopping.controller;


import com.chengfu.shopping.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	private static final String ADMIN_NAME = "admin";
	private static final String ADMIN_PASSWORD = "123456";
	private static final String USER_NAME = "user";
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
		HttpSession session = req.getSession();
		if (userName.equals(ADMIN_NAME) && userPassword.equals(ADMIN_PASSWORD)) {
			user.setUserName(userName);
			user.setUserPassword(userPassword);
			session.setAttribute(USER_LOGIN, user);
			session.setAttribute("userType", 1); // 1代表管理员
			//重定向到留言板管理页面
			resp.sendRedirect(req.getContextPath() + "/viewMessage.jsp");
		} else if (userName.equals(USER_NAME) && userPassword.equals(USER_PASSWORD)) {
			user.setUserName(userName);
			user.setUserPassword(userPassword);
			session.setAttribute(USER_LOGIN, user);
			session.setAttribute("userType", 0); // 0代表普通用户
			//重定向到购物页面
			resp.sendRedirect(req.getContextPath() + "/shopping.html");
		} else {
			//登录失败，重定向到login登录页面
			resp.sendRedirect(req.getContextPath() + "/login.html");
		}
	}


	public void viewMessages(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<String> messages = (List<String>) getServletContext().getAttribute("messages");
		if (messages == null) {
			messages = new ArrayList<>();
			getServletContext().setAttribute("messages", messages);
		}
		req.setAttribute("messages", messages);
		req.getRequestDispatcher("/viewMessage.jsp").forward(req, resp);
	}

	public void postMessage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String message = req.getParameter("message");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(USER_LOGIN);

		if (user != null && message != null && !message.trim().isEmpty()) {
			List<String> messages = (List<String>) getServletContext().getAttribute("messages");
			if (messages == null) {
				messages = new ArrayList<>();
			}
			messages.add(user.getUserName() + ": " + message);
			getServletContext().setAttribute("messages", messages);
		}
		resp.sendRedirect(req.getContextPath() + "/viewMessage.jsp");
	}

	public void deleteMessage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String indexStr = req.getParameter("index");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(USER_LOGIN);

		if (user != null && ADMIN_NAME.equals(user.getUserName()) && indexStr != null) {
			List<String> messages = (List<String>) getServletContext().getAttribute("messages");
			if (messages != null) {
				try {
					int index = Integer.parseInt(indexStr);
					if (index >= 0 && index < messages.size()) {
						messages.remove(index);
					}
				} catch (NumberFormatException e) {
					// Handle exception
				}
			}
		}
		resp.sendRedirect(req.getContextPath() + "/viewMessage.jsp");
	}
}
