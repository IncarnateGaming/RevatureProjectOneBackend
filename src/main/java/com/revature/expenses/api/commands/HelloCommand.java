package com.revature.expenses.api.commands;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import com.revature.expenses.api.FrontCommand;

public class HelloCommand extends FrontCommand {
	@Override
	public void process() throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		out.println("<h1>Hello World</h1>");
	}
}
