package com.revature.expenses.services.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerSingleton {
	private static org.apache.logging.log4j.Logger exceptionLog;
	private static Logger businessLog;
	private static Logger accessDeniedLog;
	private LoggerSingleton() {
	}
	public static Logger getExceptionLogger() {
		if (exceptionLog == null) {
			exceptionLog =  LogManager.getLogger("rolling");//"errorLog"
		}
		return exceptionLog;
	}
	public static Logger getBusinessLog() {
		if(businessLog == null) {
			businessLog = LogManager.getLogger("businessLog");
		}
		return businessLog;
	}
	public static Logger getAccessLog() {
		if(accessDeniedLog == null) {
			accessDeniedLog = LogManager.getLogger("accessLog");
		}
		return accessDeniedLog;
	}
}
