package com.revature.expenses.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class FrontCommand {
	protected ServletContext context;
	protected HttpServletRequest req;
	protected HttpServletResponse res;
	protected String body;
	protected PrintWriter out;
	public void init(ServletContext context, HttpServletRequest req, HttpServletResponse res) throws IOException {
		this.context = context;
		this.req = req;
		this.res = res;
		this.out = res.getWriter();
		BufferedReader reader = req.getReader();
		StringBuilder sb = new StringBuilder();
		String line = "";
		while((line = reader.readLine()) != null) {
			sb.append(line);
		}
		this.body = sb.toString();
	}
	public abstract void process() throws ServletException, IOException;
	
	protected void forward (String target) throws ServletException, IOException{
		target = String.format("/WEB-INF/%s.html",target);
		RequestDispatcher dispatcher = context.getRequestDispatcher(target);
		dispatcher.forward(req, res);
	}

}
