package com.revature.expenses.api.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.revature.expenses.api.FrontCommand;

public class ReimbursmentListCommand extends FrontCommand {

	@Override
	public void process() throws ServletException, IOException {
		if (type.equals("POST")) {
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}

}
