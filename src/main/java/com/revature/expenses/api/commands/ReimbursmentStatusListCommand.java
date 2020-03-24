package com.revature.expenses.api.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.revature.expenses.api.FrontCommand;
import com.revature.expenses.models.ReimbursmentStatus;
import com.revature.expenses.services.handlers.ReimbursmentStatusHandler;
import com.revature.expenses.services.helpers.LoggerSingleton;

public class ReimbursmentStatusListCommand extends FrontCommand {
	private static ReimbursmentStatusHandler reimbursmentStatusHandler = new ReimbursmentStatusHandler();
	@Override
	public void process() throws ServletException, IOException {
		if (type.equals("GET")) {
			List<ReimbursmentStatus> result = reimbursmentStatusHandler.list();
			if(result != null) {
				out.println(om.writeValueAsString(result));
				LoggerSingleton.getBusinessLog().trace("ReimbursmentStatusListCommand: types retrieved.");
			}else {
				out.println("{\"status\":\"failure\"}");
				LoggerSingleton.getExceptionLogger().warn("ReimbursmentStatusListCommand: retrieval failed.");
			}
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}
}
