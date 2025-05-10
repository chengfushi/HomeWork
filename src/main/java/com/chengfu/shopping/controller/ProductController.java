package com.chengfu.shopping.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product/*")
public class ProductController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		getPrice(req, resp);
	}

	// 计算商品最终价格
	public void getPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//获取获取登录状态的session如果没有登录就重定向到登录页面
		Object userLogin = req.getSession().getAttribute("userLogin");
		if (userLogin == null) {
			resp.sendRedirect(req.getContextPath() + "/login.html");
			return;
		}

		// 获取商品数量和来源页面
		String sourcePage = req.getParameter("sourcePage");
		int quantity = Integer.parseInt(req.getParameter("product1") != null ? req.getParameter("product1") : req.getParameter("product2"));

		// 根据商品类型计算价格
		int pricePerUnit = "productDetail1".equals(sourcePage) ? 50 : 100;
		int totalPrice = pricePerUnit * quantity;

		// 将结果传递到前端
		req.setAttribute("totalPrice", totalPrice);
		req.getRequestDispatcher("/account.jsp").forward(req, resp);

		//重定向到account页面
		resp.sendRedirect(req.getContextPath() + "/account.jsp");

	}

}