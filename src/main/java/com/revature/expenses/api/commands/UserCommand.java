package com.revature.expenses.api.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.expenses.api.FrontCommand;
import com.revature.expenses.api.templates.UserTemplate;
import com.revature.expenses.models.User;
import com.revature.expenses.services.handlers.UserHandler;
import com.revature.expenses.services.handlers.UserRoleHandler;
import com.revature.expenses.services.helpers.LoggerSingleton;
import com.revature.expenses.services.helpers.PasswordHelper;

public class UserCommand extends FrontCommand {

	private static UserRoleHandler userRoleHandler = new UserRoleHandler();
	private static UserHandler userHandler = new UserHandler();
	@Override
	public void process() throws ServletException, IOException {
		UserTemplate template = om.readValue(body, UserTemplate.class);
		if (type.equals("DELETE")) {
			doDelete(template);
		}else if (type.equals("POST")) {
			doPost(template);
		}else if (type.equals("PUT")) {
			doPut(template);
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}
	private void doDelete(UserTemplate template) {
		if(template.getSubmitter() != null & template.getSubmitter().getRole() != null & template.getSubmitter().getRole().equals(userRoleHandler.getAdmin())) {
			if(userHandler.delete(template.getUser())) {
				LoggerSingleton.getBusinessLog().trace("UserCommand: userId: " + template.getSubmitter().getId() 
						+ " username: " + template.getSubmitter().getUsername() + " deleted user: " + template.getUser());
				out.println("{\"status\":\"success\"}");
				res.setStatus(HttpServletResponse.SC_ACCEPTED);
			}else {
				LoggerSingleton.getExceptionLogger().warn("UserCommand: Failed delete user attempt. Body: " + body);
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}else {
			LoggerSingleton.getAccessLog().warn("UserCommand: Illegal attempt to delete a user. Body: " + body);
			res.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}
	private void doPost(UserTemplate template) throws JsonProcessingException {
		User user = new User(template.getUser().getUsername(), PasswordHelper.encryptPassword(template.getUser().getPassword()), template.getUser().getEmail());
		user = userHandler.create(user);
		user.setRole(userRoleHandler.getEmployee());
		user = userHandler.update(user);
		if(user != null) {
			out.println(om.writeValueAsString(user));
			LoggerSingleton.getBusinessLog().trace("User: " + user.getId() + " created: " + user.getUsername());
			res.setStatus(HttpServletResponse.SC_CREATED);
		}else {
			String msg = "{\"error\":\"User creation failed\",\"attempt\":{\"username\":\""
					+template.getUser().getUsername()+"\",\"email\":\""+
					template.getUser().getEmail()+"\"}}";
			out.println(msg);
			LoggerSingleton.getExceptionLogger().warn(msg);
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	private void doPut(UserTemplate template) {
		if((template.getSubmitter() != null & template.getSubmitter().getId() > 0 & template.getUser() != null & template.getUser().getId() > 0 & template.getSubmitter().getId() == template.getUser().getId())
				|(template.getSubmitter() != null & template.getSubmitter().getRole() != null & template.getSubmitter().getRole().equals(userRoleHandler.getAdmin()))) {
			User updatedUser = userHandler.update(template.getUser());
			if(updatedUser != null) {
				LoggerSingleton.getBusinessLog().trace("UserCommand: userId: " + template.getSubmitter().getId() 
						+ " username: " + template.getSubmitter().getUsername() + " updated user: " + template.getUser());
				out.println("{\"status\":\"success\"}");
				res.setStatus(HttpServletResponse.SC_ACCEPTED);
			}else {
				LoggerSingleton.getExceptionLogger().warn("UserCommand: Failed update user attempt. Body: " + body);
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}else {
			LoggerSingleton.getAccessLog().warn("UserCommand: Illegal attempt to update a user. Body: " + body);
			res.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}
}