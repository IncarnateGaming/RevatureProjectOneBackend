package com.revature.expenses.services.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class LoggerSingleton {
	private static org.apache.logging.log4j.Logger exceptionLog;
	private static Logger businessLog;
	private static Logger accessDeniedLog;
	private LoggerSingleton() {
	}
	public static Logger getExceptionLogger() {
		if (exceptionLog == null) {
//			exceptionLog =  LogManager.getLogger(MyService.class);//"errorLog"
		}
//		return exceptionLog;
		return null;
	}
	public static Logger getBusinessLog() {
		if(businessLog == null) {
//			businessLog = Logger.getLogger("businessLog");
		}
//		return businessLog;
		return null;
	}
	public static Logger getAccessLog() {
		if(accessDeniedLog == null) {
//			accessDeniedLog = Logger.getLogger("accessDeniedLog");
		}
//		return accessDeniedLog;
		return null;
	}
}
