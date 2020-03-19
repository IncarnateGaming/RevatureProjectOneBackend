package com.revature.expenses.services.business;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.expenses.exceptions.PasswordMatchFailed;
import com.revature.expenses.exceptions.UserNotFound;
import com.revature.expenses.models.User;
import com.revature.expenses.services.handlers.UserHandler;
import com.revature.expenses.services.helpers.PasswordHelper;

public class LoginServiceTest {

	private static User testUser;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		UserHandler userHandler = new UserHandler();
		testUser = new User("bobTheGreat", PasswordHelper.encryptPassword("strongPassword"), "bob@great.com");
		User userToDelete = userHandler.get(testUser.getUsername());
		if(userToDelete != null) {
			userHandler.delete(userToDelete);
		}
		userToDelete = userHandler.get("bobTheFoolish");
		if(userToDelete != null) {
			userHandler.delete(userToDelete);
		}
		testUser = userHandler.create(testUser);
	}

	@Test
	public void success() {
		assertEquals(testUser,LoginService.login("bobTheGreat", "strongPassword"));
	}
	@Test(expected = UserNotFound.class)
	public void noUser() {
		LoginService.login("bobTheFoolish", "strongPassword");
	}
	@Test(expected = PasswordMatchFailed.class)
	public void wrongPassword() {
		LoginService.login("bobTheGreat", "strongpassword");
	}
}
