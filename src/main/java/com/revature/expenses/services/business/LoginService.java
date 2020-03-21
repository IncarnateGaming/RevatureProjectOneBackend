package com.revature.expenses.services.business;

import com.revature.expenses.exceptions.PasswordMatchFailed;
import com.revature.expenses.exceptions.UserNotFound;
import com.revature.expenses.models.User;
import com.revature.expenses.services.handlers.UserHandler;
import com.revature.expenses.services.helpers.LoggerSingleton;
import com.revature.expenses.services.helpers.PasswordHelper;

public class LoginService {
	private LoginService() {
	}
	public static User login(String username, String password) {
		User result = null;
		User checkUser = new UserHandler().get(username);
		if (checkUser == null) {
			LoggerSingleton.getExceptionLogger().warn("Login Failed, invalid user: "+ username);
			throw new UserNotFound();
		}else if(PasswordHelper.checkPassword(password, checkUser.getPassword())) {
			result = checkUser;
		}else {
			LoggerSingleton.getExceptionLogger().warn("Login Failed, wrong password: "+ username);
			throw new PasswordMatchFailed();
		}
		return result;
	}
}
