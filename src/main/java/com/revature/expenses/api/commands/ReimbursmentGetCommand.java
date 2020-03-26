package com.revature.expenses.api.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.revature.expenses.api.FrontCommand;
import com.revature.expenses.api.templates.ReimbursmentTemplate;
import com.revature.expenses.models.Reimbursment;
import com.revature.expenses.services.handlers.ReimbursmentHandler;
import com.revature.expenses.services.handlers.UserRoleHandler;
import com.revature.expenses.services.helpers.LoggerSingleton;

public class ReimbursmentGetCommand extends FrontCommand{

	private ReimbursmentHandler reimbursmentHandler = new ReimbursmentHandler();
	private UserRoleHandler userRoleHandler = new UserRoleHandler();
	@Override
	public void process() throws ServletException, IOException {
		ReimbursmentTemplate template = om.readValue(body, ReimbursmentTemplate.class);
		if(template.getSubmitter() == null || template.getSubmitter().getId() == 0) {
			LoggerSingleton.getAccessLog().warn("ReimbursmentGetCommand: attempt to access without being logged in. Body: " + body);
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}else if (template.getReimbursment() == null) {
			LoggerSingleton.getExceptionLogger().warn("ReimbursmentGetCommand: attempt to submit without attachning a reimbursment. Body: " + body);
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else {
			if (type.equals("POST")) {
				Reimbursment retrieved = reimbursmentHandler.get(template.getReimbursment().getId());
				if(retrieved == null) {
					res.setStatus(HttpServletResponse.SC_NO_CONTENT);
					out.println("{\"status\":\"failure\"}");
					LoggerSingleton.getExceptionLogger().warn("ReimbursmentGetCommand: Failed to retrieve requested reimbursment. Body: " + body);
				}else if(retrieved.getAuthor().getId() == template.getSubmitter().getId() | template.getSubmitter().getRole().equals(userRoleHandler.getAdmin())) {
					res.setStatus(HttpServletResponse.SC_ACCEPTED);
					out.println(om.writeValueAsString(retrieved));
					LoggerSingleton.getBusinessLog().trace("ReimbursmentGetCommand: User: " + template.getSubmitter().toString() + " retrieved reimbursment: " + retrieved.toString());
				}else {
					res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					out.println("{\"status\":\"failure\"}");
					LoggerSingleton.getAccessLog().warn("ReimbursmentGetCommand: User: " + template.getSubmitter().toString() + " attempted to retrieve reimbursment: " + retrieved.toString());
				}
			}else {
				res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				LoggerSingleton.getAccessLog().warn("ReimbursmentGetCommand: Attempt to perform unallowed method: " + type + " Body: " + body);
			}
		}
	}
}