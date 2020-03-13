package com.revature.expenses.services.helpers;

import static org.junit.Assert.*;

import org.junit.Test;

public class PasswordHelperTest {

	@Test
	public void test() {
		String password = "T3stcTi37b18z$8@09Bt";
		String hash = PasswordHelper.encryptPassword(password);
		assertTrue(PasswordHelper.checkPassword(password, hash));
	}
	@Test
	public void test2() {
		String password = "StrongPassword";
		String hash = PasswordHelper.encryptPassword(password);
		assertTrue(PasswordHelper.checkPassword(password, hash));
	}
	@Test
	public void checkCaseSensitive() {
		String password = "StrongPassword";
		String hash = PasswordHelper.encryptPassword(password);
		assertFalse(PasswordHelper.checkPassword(password.toLowerCase(), hash));
	}
	@Test
	public void checkAlphaNumeric() {
		String password = "T3stcTi37b18z$8@09Bt";
		String hash = PasswordHelper.encryptPassword(password);
		//Removes all non alphanumeric symbols confirming that they are used as part of the password
		password = password.replaceAll("[^0-9a-zA-Z]+", "");
		assertFalse(PasswordHelper.checkPassword(password, hash));
	}

}
