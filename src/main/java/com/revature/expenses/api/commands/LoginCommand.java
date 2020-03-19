package com.revature.expenses.api.commands;

import java.io.IOException;

import javax.servlet.ServletException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.expenses.api.FrontCommand;
import com.revature.expenses.api.templates.LoginTemplate;
import com.revature.expenses.exceptions.PasswordMatchFailed;
import com.revature.expenses.exceptions.UserNotFound;
import com.revature.expenses.models.User;
import com.revature.expenses.services.business.LoginService;

public class LoginCommand extends FrontCommand {
	private static final ObjectMapper om = new ObjectMapper();
	@Override
	public void process() throws ServletException, IOException {
		LoginTemplate template = om.readValue(body,  LoginTemplate.class);
		User user = null;
		try {
			user = LoginService.login(template.getUsername(), template.getPassword());
		}catch(PasswordMatchFailed e) {
			out.println("<h1>Password Match Failed</h1>");
		}catch(UserNotFound e) {
			out.println("<h1>No such user.</h1>");
		}
		if(user != null) {
			out.println(om.writeValueAsString(user));
		}
	}
}
