package com.chengfu.shopping.controller;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author Cheng Fu
 * @version 1.0
 * @description: 控制器基类
 * @date 2025/5/9 10:57
 */
public class BasicController extends HttpServlet {

	/**
	 * @description: 这是一个基类，通过反射来调用子类中的方法
	 * @author Chrng Fu
	 * @date 2025/5/9 11:00
	 * @version 1.0
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		String url = req.getRequestURI();
		// 打印请求URL，便于调试
		System.out.println("请求URL: " + url);
		
		// 处理URL，获取方法名
		String[] split = url.split("/");
		String methodName = split[split.length - 1];
		// 打印方法名，便于调试
		System.out.println("调用方法: " + methodName);

		try {

			Class<? extends BasicController> clazz = this.getClass();

			Method method = clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, req, resp);

		}catch (NoSuchMethodException e) {
			// 找不到方法时，重定向到登录页面而不是显示500错误
			System.out.println("找不到方法: " + methodName + "，重定向到登录页面");
			resp.sendRedirect(req.getContextPath() + "/login.html");
		} catch (Exception e) {
			// 其他异常仍然显示500错误
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器内部错误: " + e.getMessage());
		}

	}
}

