package com.revature.expenses.services.helpers;

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.core.Logger;
import org.junit.Test;

public class LoggerSingletonTest {
	@Test
	public void accessTest() {
		assertTrue(LoggerSingleton.getAccessLog() instanceof Logger);
	}
	@Test
	public void businessTest() {
		assertTrue(LoggerSingleton.getBusinessLog() instanceof Logger);
	}
	@Test
	public void exceptionTest() {
		assertTrue(LoggerSingleton.getExceptionLogger() instanceof Logger);
	}
}
