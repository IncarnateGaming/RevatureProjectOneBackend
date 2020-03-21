package com.revature.expenses.api.commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.revature.expenses.api.FrontCommand;
import com.revature.expenses.api.templates.ReimbursmentListTemplate;
import com.revature.expenses.models.Reimbursment;
import com.revature.expenses.services.handlers.ReimbursmentHandler;
import com.revature.expenses.services.handlers.UserRoleHandler;
import com.revature.expenses.services.helpers.LoggerSingleton;

public class ReimbursmentListCommand extends FrontCommand {

	static UserRoleHandler userRoleHandler = new UserRoleHandler();
	static ReimbursmentHandler reimbursmentHandler = new ReimbursmentHandler();
	@Override
	public void process() throws ServletException, IOException {
		if (type.equals("POST")) {
			ReimbursmentListTemplate template = om.readValue(body,  ReimbursmentListTemplate.class);
			setDefaults(template);
			List<Reimbursment> result = getList(template);
			if(result != null) {
				out.println(om.writeValueAsString(result));
				LoggerSingleton.getExceptionLogger().warn("ReimbursmentListCommand: data retrieved by user: " + template.getSubmitter()
					+ " with parameters- filter by user: " + template.getFilterBy() 
					+ " limit: " + template.getLimit() + " offset: " + template.getOffset() + " status: " + template.getStatus());
			}else {
				out.println("{\"status\":\"failure\"}");
				LoggerSingleton.getExceptionLogger().warn("ReimbursmentListCommand: retrieval failed with parameters- user: " + template.getFilterBy() 
					+ " limit: " + template.getLimit() + " offset: " + template.getOffset() + " status: " + template.getStatus());
			}
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}
	private List<Reimbursment> getList(ReimbursmentListTemplate template){
		List<Reimbursment> result = null;
		if(template.getFilterBy() != null & template.getFilterBy().getId() > 0) {
			if(template.getStatus() != null & template.getStatus().getId() > 0) {
				result = reimbursmentHandler.list(template.getFilterBy(), template.getLimit(), template.getOffset(), template.getStatus());
			}else {
				result = reimbursmentHandler.list(template.getFilterBy(), template.getLimit(), template.getOffset());
			}
		}else {
			if(template.getStatus() != null & template.getStatus().getId() > 0) {
				result = reimbursmentHandler.list(template.getLimit(), template.getOffset(), template.getStatus());
			}else {
				result = reimbursmentHandler.list(template.getLimit(), template.getOffset());
			}
		}
		return result;
	}
	private void setDefaults(ReimbursmentListTemplate template) {
		//If employee force filter by employee
		if(template.getSubmitter().getRole().equals(userRoleHandler.getEmployee())) {
			template.setFilterBy(template.getSubmitter());
		}
		//If no limit or offset, set them to defaults
		if(template.getLimit() <= 0) {
			template.setLimit(20);
		}
		if(template.getOffset() < 0) {
			template.setOffset(0);
		}
	}

}
