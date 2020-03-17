package com.revature.expenses;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Application extends HttpServlet{
	private static final long serialVersionUID = 7302326277418684325L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		PrintWriter out = res.getWriter();
		System.out.println("Application: test working");
		out.println("<h1>Hello World</h1>");
//		
//		String configParam = getServletConfig().getInitParameter("ConfigParam");
//		System.out.println(configParam);
//		String initParam = getServletContext().getInitParameter("SpecificParam");
//		System.out.println(initParam);
//		out.println("<h1>"+configParam+"</h1>");
//		out.println("<h1>"+initParam+"</h1>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
	}

}
