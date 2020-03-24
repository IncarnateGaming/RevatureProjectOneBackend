package com.revature.expenses.api.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.revature.expenses.api.FrontCommand;
import com.revature.expenses.models.ReimbursmentType;
import com.revature.expenses.services.handlers.ReimbursmentTypeHandler;
import com.revature.expenses.services.helpers.LoggerSingleton;

public class ReimbursmentTypeListCommand extends FrontCommand{
	private static ReimbursmentTypeHandler reimbursmentTypeHandler = new ReimbursmentTypeHandler();
	@Override
	public void process() throws ServletException, IOException {
		if (type.equals("GET")) {
			List<ReimbursmentType> result = reimbursmentTypeHandler.list();
			if(result != null) {
				out.println(om.writeValueAsString(result));
				LoggerSingleton.getBusinessLog().trace("ReimbursmentTypeListCommand: types retrieved.");
			}else {
				out.println("{\"status\":\"failure\"}");
				LoggerSingleton.getExceptionLogger().warn("ReimbursmentTypeListCommand: retrieval failed.");
			}
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}
}