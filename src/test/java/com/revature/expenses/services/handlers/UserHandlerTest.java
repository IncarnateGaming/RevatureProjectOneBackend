package com.revature.expenses.services.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.expenses.models.User;
import com.revature.expenses.services.helpers.PasswordHelper;

public class UserHandlerTest {
	private static UserHandler userHandler;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userHandler = new UserHandler();
		User user1 = new User("bugsBunny",PasswordHelper.encryptPassword("password"),"bugs@gmail.com");
		User userToDelete = userHandler.get(user1.getUsername());
		if(userToDelete != null) {
			userHandler.delete(userToDelete);
		}
		userHandler.create(user1);
		User user2 = new User("rudolphRed",PasswordHelper.encryptPassword("password"),"rudolph@gmail.com");
		userToDelete = userHandler.get(user2.getUsername());
		if(userToDelete != null) {
			userHandler.delete(userToDelete);
		}
		userHandler.create(user2);
		User user3 = new User("apple",PasswordHelper.encryptPassword("password"),"botany@gmail.com");
		userToDelete = userHandler.get(user3.getUsername());
		if(userToDelete != null) {
			userHandler.delete(userToDelete);
		}
		userHandler.create(user3);
		User user4 = new User("brownEyes",PasswordHelper.encryptPassword("password"),"brownX@gmail.com");
		userToDelete = userHandler.get(user4.getUsername());
		if(userToDelete != null) {
			userHandler.delete(userToDelete);
		}
		userHandler.create(user4);
		User user5 = new User("orangeSpin",PasswordHelper.encryptPassword("password"),"tropical@gmail.com");
		userToDelete = userHandler.get(user5.getUsername());
		if(userToDelete != null) {
			userHandler.delete(userToDelete);
		}
		userHandler.create(user5);
	}

	@Test
	public void test() {
		assertNotNull(userHandler.get("bugsBunny"));
		assertNotNull(userHandler.get("rudolphRed"));
		assertNotNull(userHandler.get("apple"));
		assertNotNull(userHandler.get("brownEyes"));
		assertNotNull(userHandler.get("orangeSpin"));
	}
	@Test
	public void testNames() {
		User bugs = userHandler.get("bugsBunny");
		bugs.setFirstName("Bugs");
		bugs.setLastName("Bunny");
		userHandler.update(bugs);
		User gotten = userHandler.get("bugsBunny");
		assertEquals("Bugs",gotten.getFirstName());
		assertEquals("Bunny",gotten.getLastName());
	}
	@Test
	public void assignRoles() {
		UserRoleHandler userRoleHandler = new UserRoleHandler();
		User bugs = userHandler.get("bugsBunny");
		User rudolph = userHandler.get("rudolphRed");
		User apple = userHandler.get("apple");
		User brown = userHandler.get("brownEyes");
		User orange = userHandler.get("orangeSpin");
		bugs.setRole(userRoleHandler.getEmployee());
		rudolph.setRole(userRoleHandler.getEmployee());
		apple.setRole(userRoleHandler.getEmployee());
		brown.setRole(userRoleHandler.getAdmin());
		orange.setRole(userRoleHandler.getAdmin());
		userHandler.update(bugs);
		userHandler.update(rudolph);
		userHandler.update(apple);
		userHandler.update(brown);
		userHandler.update(orange);
		assertEquals(userRoleHandler.getEmployee(),userHandler.get("bugsBunny").getRole());
		assertEquals(userRoleHandler.getEmployee(),userHandler.get("rudolphRed").getRole());
		assertEquals(userRoleHandler.getEmployee(),userHandler.get("apple").getRole());
		assertEquals(userRoleHandler.getAdmin(),userHandler.get("brownEyes").getRole());
		assertEquals(userRoleHandler.getAdmin(),userHandler.get("orangeSpin").getRole());
	}
}
