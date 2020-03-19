package com.revature.expenses;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.revature.expenses.services.business.LoginServiceTest;
import com.revature.expenses.services.business.PasswordUpdateServiceTest;
import com.revature.expenses.services.handlers.ReimbursmentStatusHandlerTest;
import com.revature.expenses.services.handlers.ReimbursmentTypeHandlerTest;
import com.revature.expenses.services.handlers.UserHandlerTest;
import com.revature.expenses.services.handlers.UserRoleHandlerTest;
import com.revature.expenses.services.helpers.MathHelperTest;
import com.revature.expenses.services.helpers.PasswordHelperTest;

@RunWith(Suite.class)
@SuiteClasses({
	LoginServiceTest.class,
//	LoggerSingletonTest.class,
	MathHelperTest.class,
	PasswordHelperTest.class,
	PasswordUpdateServiceTest.class,
	ReimbursmentStatusHandlerTest.class,
	ReimbursmentTypeHandlerTest.class,
	UserRoleHandlerTest.class,
	UserHandlerTest.class
})
public class AllTests {

}
