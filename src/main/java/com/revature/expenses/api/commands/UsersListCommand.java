package com.revature.expenses.api.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.revature.expenses.api.FrontCommand;
import com.revature.expenses.api.templates.UserListTemplate;
import com.revature.expenses.services.handlers.UserHandler;
import com.revature.expenses.services.handlers.UserRoleHandler;
import com.revature.expenses.services.helpers.LoggerSingleton;

public class UsersListCommand extends FrontCommand {

	static UserHandler userHandler = new UserHandler();
	static UserRoleHandler userRoleHandler = new UserRoleHandler();
	@Override
	public void process() throws ServletException, IOException {
		if (type.equals("POST")) {
			UserListTemplate template = om.readValue(body,  UserListTemplate.class);
			if(template.getSubmitter() != null & template.getSubmitter().getRole() != null & template.getSubmitter().getRole().equals(userRoleHandler.getAdmin())) {
				out.println(om.writeValueAsString(userHandler.list()));
				LoggerSingleton.getBusinessLog().trace("UserListCommand: user list sent to user id: " + template.getSubmitter().getId() + " username: " + template.getSubmitter().getUsername());
			}else {
				out.println("{\"error\":\"User login failed\"}");
				LoggerSingleton.getAccessLog().warn("UserListcommand: Denied access to list by. body:" + body);
			}
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}
}
