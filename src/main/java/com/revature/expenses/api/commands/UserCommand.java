package com.revature.expenses.api.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.revature.expenses.api.FrontCommand;

public class UserCommand extends FrontCommand {

	@Override
	public void process() throws ServletException, IOException {
		if (type.equals("DELETE")) {
		}else if (type.equals("GET")) {
		}else if (type.equals("POST")) {
		}else if (type.equals("PUT")) {
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}
}
