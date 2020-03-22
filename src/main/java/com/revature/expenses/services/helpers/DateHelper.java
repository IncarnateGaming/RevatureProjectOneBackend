package com.revature.expenses.services.helpers;

import java.sql.Date;
import java.util.Random;

public class DateHelper {
	private DateHelper() {
	}
	public static Date now() {
		return new Date(new java.util.Date().getTime());
	}
	public static Date future() {
		long date = new java.util.Date().getTime() + (long) new Random().nextInt(1604800000);
		return new Date(date);
	}
}
