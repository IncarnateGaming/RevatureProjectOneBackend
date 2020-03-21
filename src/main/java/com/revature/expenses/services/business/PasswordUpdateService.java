package com.revature.expenses.services.business;

import com.revature.expenses.models.User;
import com.revature.expenses.services.handlers.UserHandler;
import com.revature.expenses.services.helpers.LoggerSingleton;
import com.revature.expenses.services.helpers.PasswordHelper;

public class PasswordUpdateService {
	private static UserHandler userHandler = new UserHandler();
	private PasswordUpdateService() {
	}
	public static boolean updatePassword(String oldPassword, String newPassword,String username) {
		boolean result = false;
		User checkUser = LoginService.login(username, oldPassword);
		if (checkUser == null) {
			LoggerSingleton.getAccessLog().warn("Attempt to change user: " + username + " with faulty credentials.");
		}else {
			checkUser.setPassword(PasswordHelper.encryptPassword(newPassword));
			if(userHandler.update(checkUser) != null) {
				result = true;
			}
		}
		return result;
	}
}
