package com.revature.expenses.services.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.expenses.exceptions.PasswordMatchFailed;
import com.revature.expenses.exceptions.UserNotFound;
import com.revature.expenses.models.User;
import com.revature.expenses.services.handlers.UserHandler;
import com.revature.expenses.services.helpers.PasswordHelper;

public class PasswordUpdateServiceTest {
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

	@Test(expected = UserNotFound.class)
	public void noUser() {
		PasswordUpdateService.updatePassword("strongPassword","newPassword","bobTheFoolish");
	}
	@Test(expected = PasswordMatchFailed.class)
	public void wrongPassword() {
		PasswordUpdateService.updatePassword("strongpassword","newPassword","bobTheGreat");
	}
	@Test
	public void success() {
		assertEquals(testUser,LoginService.login("bobTheGreat", "strongPassword"));
		PasswordUpdateService.updatePassword("strongPassword", "newPassword", "bobTheGreat");
		assertNotNull(LoginService.login("bobTheGreat", "newPassword"));
	}
}
