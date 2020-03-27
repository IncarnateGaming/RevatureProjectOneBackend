package com.revature.expenses.api.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.expenses.api.FrontCommand;
import com.revature.expenses.api.templates.ReimbursmentTemplate;
import com.revature.expenses.models.Reimbursment;
import com.revature.expenses.services.handlers.ReimbursmentHandler;
import com.revature.expenses.services.handlers.ReimbursmentStatusHandler;
import com.revature.expenses.services.handlers.UserRoleHandler;
import com.revature.expenses.services.helpers.LoggerSingleton;

public class ReimbursmentCommand extends FrontCommand {

	private static UserRoleHandler userRoleHandler = new UserRoleHandler();
	private static ReimbursmentHandler reimbursmentHandler = new ReimbursmentHandler();
	private static ReimbursmentStatusHandler reimbursmentStatusHandler = new ReimbursmentStatusHandler();
	@Override
	public void process() throws ServletException, IOException {
		ReimbursmentTemplate template = om.readValue(body,  ReimbursmentTemplate.class);
		if(template == null | template.getSubmitter() == null | template.getSubmitter().getId() == 0) {
			LoggerSingleton.getAccessLog().warn("ReimbursmentCommand: attempt to access without being logged in. Body: " + body);
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}else if (template.getReimbursment() == null) {
			LoggerSingleton.getExceptionLogger().warn("ReimbursmentCommand: attempt to submit without attachning a reimbursment. Body: " + body);
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else if (type.equals("DELETE")) {
			doDelete(template);
		}else if (type.equals("POST")) {
			doPost(template);
		}else if (type.equals("PUT")) {
			doPut(template);
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			LoggerSingleton.getAccessLog().warn("ReimbursmentCommand: Attempt to perform unallowed method: " + type + " Body: " + body);
		}
	}
	private void doDelete(ReimbursmentTemplate template) {
		if(template.getSubmitter().getRole().equals(userRoleHandler.getAdmin())) {
			if(reimbursmentHandler.delete(template.getReimbursment())){
				out.println("{\"status\":\"success\"}");
				LoggerSingleton.getBusinessLog().trace("ReimbursmentCommand: deleted reimbursment: " + template.getReimbursment() + " Body: " + body);
			}else {
				out.println("{\"status\":\"failure\"}");
				LoggerSingleton.getExceptionLogger().warn("ReimbursmentCommand: failed to delete reimbursment. Body: " + body);
			}
		}else {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			out.println("{\"status\":\"failure\"}");
			LoggerSingleton.getAccessLog().warn("ReimbursmentCommand: Attempted to delete without being admin. Body: " + body);
		}
	}
	private void doPost(ReimbursmentTemplate template) throws JsonProcessingException {
		template.getReimbursment().setStatus(reimbursmentStatusHandler.getPending());
		Reimbursment created = reimbursmentHandler.create(template.getReimbursment());
		if(created != null) {
			LoggerSingleton.getBusinessLog().trace("ReimbursmentCommand: User: " + template.getSubmitter().toString() + " created reimbursment: " + created.toString());
			res.setStatus(HttpServletResponse.SC_CREATED);
			out.println(om.writeValueAsString(created));
		}else {
			LoggerSingleton.getExceptionLogger().warn("ReimbursmentCommand: reimbursment creation failed. Body: " + body);
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.println("{\"status\":\"failure\"}");
		}
	}
	private void doPut(ReimbursmentTemplate template) throws JsonProcessingException {
		Reimbursment retrieved = reimbursmentHandler.get(template.getReimbursment().getId());
		if(retrieved == null) {
			res.setStatus(HttpServletResponse.SC_NO_CONTENT);
			out.println("{\"status\":\"failure\"}");
			LoggerSingleton.getExceptionLogger().warn("ReimbursmentCommand: Failed to retrieve requested reimbursment for update. Body: " + body);
		}else if(retrieved.getAuthor().getId() == template.getSubmitter().getId() || template.getSubmitter().getRole().equals(userRoleHandler.getAdmin())) {
			Reimbursment updated = reimbursmentHandler.update(template.getReimbursment());
			if(updated != null) {
				res.setStatus(HttpServletResponse.SC_ACCEPTED);
				out.println(om.writeValueAsString(updated));
				LoggerSingleton.getBusinessLog().trace("ReimbursmentCommand: User: " + template.getSubmitter().toString() + " updated reimbursment: " + updated.toString());
			}else {
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				out.println("{\"status\":\"failure\"}");
				LoggerSingleton.getExceptionLogger().warn("ReimbursmentCommand: Failed to update requested reimbursment. Body: " + body);
			}
		}else {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			out.println("{\"status\":\"failure\"}");
			LoggerSingleton.getAccessLog().warn("ReimbursmentCommand: Illegal attempt to update reimbursment User: " + template.getSubmitter().toString() + " attempted to update reimbursment: " + retrieved.toString());
		}
	}
}
