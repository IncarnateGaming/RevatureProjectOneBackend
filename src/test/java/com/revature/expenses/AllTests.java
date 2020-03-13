package com.revature.expenses;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.revature.expenses.services.helpers.LoggerSingletonTest;
import com.revature.expenses.services.helpers.MathHelperTest;
import com.revature.expenses.services.helpers.PasswordHelperTest;

@RunWith(Suite.class)
@SuiteClasses({
	LoggerSingletonTest.class,
	MathHelperTest.class,
	PasswordHelperTest.class
})
public class AllTests {

}
