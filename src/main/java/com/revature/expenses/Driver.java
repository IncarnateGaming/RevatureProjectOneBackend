package com.revature.expenses;

import com.revature.expenses.services.helpers.LoggerSingleton;

public class Driver {
	public static void main(String[] args) {
		LoggerSingleton.getBusinessLog().trace("Test Message4");
	}
}
