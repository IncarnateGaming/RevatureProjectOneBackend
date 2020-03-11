package com.revature.expenses.services.helpers;

import org.apache.log4j.Logger;

public class LoggerSingleton {
	private static Logger exceptionLog;
	private static Logger businessLog;
	private static Logger accessDeniedLog;
	private LoggerSingleton() {
	}
	public static Logger getExceptionLogger() {
		if (exceptionLog == null) {
			exceptionLog =  Logger.getLogger("errorLog");
		}
		return exceptionLog;
	}
	public static Logger getBusinessLog() {
		if(businessLog == null) {
			businessLog = Logger.getLogger("businessLog");
		}
		return businessLog;
	}
	public static Logger getAccessLog() {
		if(accessDeniedLog == null) {
			accessDeniedLog = Logger.getLogger("accessDeniedLog");
		}
		return accessDeniedLog;
	}
}
