package com.revature.expenses.api.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.revature.expenses.api.FrontCommand;
import com.revature.expenses.api.templates.LoginTemplate;
import com.revature.expenses.exceptions.PasswordMatchFailed;
import com.revature.expenses.exceptions.UserNotFound;
import com.revature.expenses.models.User;
import com.revature.expenses.services.business.LoginService;
import com.revature.expenses.services.helpers.LoggerSingleton;

public class LoginCommand extends FrontCommand {
	@Override
	public void process() throws ServletException, IOException {
		if (type.equals("POST")) {
			LoginTemplate template = om.readValue(body,  LoginTemplate.class);
			User user = null;
			try {
				user = LoginService.login(template.getUsername(), template.getPassword());
				user.setPassword("****");
			}catch(PasswordMatchFailed e) {
				LoggerSingleton.getAccessLog().warn("INVALID PASSWORD login fail: " + template.getUsername());
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				out.println("{\"error\":\"User login failed\"}");
			}catch(UserNotFound e) {
				LoggerSingleton.getAccessLog().warn("USER NOT FOUND login fail: " + template.getUsername());
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				out.println("{\"error\":\"User login failed\"}");
			}
			if(user != null) {
				res.setStatus(HttpServletResponse.SC_ACCEPTED);
				out.println(om.writeValueAsString(user));
			}
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}
}
