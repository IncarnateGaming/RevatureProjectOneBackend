package com.revature.expenses.services.helpers;

import com.lambdaworks.crypto.SCryptUtil;

public class PasswordHelper {
	private PasswordHelper() {
	}
	public static String encryptPassword(String unencryptedPassword) {
		return SCryptUtil.scrypt(unencryptedPassword, 16384, 8, 1);
	}
	public static boolean checkPassword(String unencryptedPassword, String passwordHash) {
		return SCryptUtil.check(unencryptedPassword, passwordHash);
	}
}
