package com.revature.expenses.services.handlers;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.expenses.models.UserRole;

public class UserRoleHandlerTest {

	private static UserRoleHandler userRoleHandler;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userRoleHandler = new UserRoleHandler();
		UserRole role1 = new UserRole("Employee");
		UserRole role2 = new UserRole("Admin");
		userRoleHandler.create(role1);
		userRoleHandler.create(role2);
	}

	@Test
	public void test() {
		assertNotNull(userRoleHandler.get("Employee"));
		assertNotNull(userRoleHandler.get("Admin"));
	}

}
