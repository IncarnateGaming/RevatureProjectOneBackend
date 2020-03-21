package com.revature.expenses.api.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.revature.expenses.api.FrontCommand;
import com.revature.expenses.api.templates.NewAccountTemplate;
import com.revature.expenses.models.User;
import com.revature.expenses.services.handlers.UserHandler;
import com.revature.expenses.services.helpers.LoggerSingleton;
import com.revature.expenses.services.helpers.PasswordHelper;

public class NewAccountCommand extends FrontCommand {

	private static UserHandler userHandler = new UserHandler();
	@Override
	public void process() throws ServletException, IOException {
		if (type.equals("POST")) {
			NewAccountTemplate template = om.readValue(body,  NewAccountTemplate.class);
			User user = null;
			user = userHandler.create(new User(template.getUsername(), PasswordHelper.encryptPassword(template.getPassword()), template.getEmail()));
			if(user != null) {
				out.println(om.writeValueAsString(user));
				LoggerSingleton.getBusinessLog().trace("User: " + user.getId() + " logged in as: " + user.getUsername());
			}else {
				String msg = "{\"error\":\"User creation failed\",\"attempt\":{\"username\":\""
						+template.getUsername()+"\",\"email\":\""+
						template.getEmail()+"\"}}";
				out.println(msg);
				LoggerSingleton.getExceptionLogger().warn(msg);
			}
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			LoggerSingleton.getAccessLog().warn("Invalid request of type: " + type + " to NewAccountCommand. Body: " + body);
		}
	}
}
