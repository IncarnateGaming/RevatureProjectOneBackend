package com.revature.expenses.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.expenses.api.commands.HomeCommand;
import com.revature.expenses.api.commands.UnknownCommand;

public class FrontController extends HttpServlet{
	private static final long serialVersionUID = 7302326277418684325L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		FrontCommand command = getCommand(req);
		command.setType("GET");
		command.init(getServletContext(),req,res);
		command.process();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		FrontCommand command = getCommand(req);
		command.setType("POST");
		command.init(getServletContext(),req,res);
		command.process();
	}
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		FrontCommand command = getCommand(req);
		command.setType("PUT");
		command.init(getServletContext(),req,res);
		command.process();
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		FrontCommand command = getCommand(req);
		command.setType("DELETE");
		command.init(getServletContext(),req,res);
		command.process();
	}
	private FrontCommand getCommand(HttpServletRequest req) {
		try {
			final String URI = req.getRequestURI();
			String[] uriArray = URI.split("/");
			if(uriArray.length > 2) {
				String firstUri = uriArray[2];
				String classPath = "com.revature.expenses.api.commands." + firstUri + "Command";
				Class<?> type = Class.forName(classPath);
				return (FrontCommand) type.asSubclass(FrontCommand.class).newInstance();
			}else {
				return new HomeCommand();
			}
		}catch (Exception e) {
			return new UnknownCommand();
		}
	}

}
