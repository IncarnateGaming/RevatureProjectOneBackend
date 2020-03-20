package com.revature.expenses.api.commands;

import java.io.IOException;

import javax.servlet.ServletException;

import com.revature.expenses.api.FrontCommand;
import com.revature.expenses.api.templates.LoginTemplate;
import com.revature.expenses.exceptions.PasswordMatchFailed;
import com.revature.expenses.exceptions.UserNotFound;
import com.revature.expenses.models.User;
import com.revature.expenses.services.business.LoginService;

public class LoginCommand extends FrontCommand {
	@Override
	public void process() throws ServletException, IOException {
		LoginTemplate template = om.readValue(body,  LoginTemplate.class);
		User user = null;
		try {
			user = LoginService.login(template.getUsername(), template.getPassword());
		}catch(PasswordMatchFailed e) {
			//using error 1 and error 2 to make it harder to tell whether it is bad username or password
			//while still allowing initial debug, full deployment should
			//TODO: make the two error messages match exactly.
			out.println("{\"error\":\"User login failed, error 1\"}");
			//TODO: logger 4j2
		}catch(UserNotFound e) {
			out.println("{\"error\":\"User login failed, error 2\"}");
			//TODO: logger 4j2
		}
		if(user != null) {
			out.println(om.writeValueAsString(user));
		}
	}
}
