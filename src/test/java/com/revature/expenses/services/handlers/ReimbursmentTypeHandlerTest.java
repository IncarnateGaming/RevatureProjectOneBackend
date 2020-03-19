package com.revature.expenses.services.handlers;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.expenses.models.ReimbursmentType;

public class ReimbursmentTypeHandlerTest {
	private static ReimbursmentTypeHandler reimbursmentTypeHandler;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		reimbursmentTypeHandler = new ReimbursmentTypeHandler();
		ReimbursmentType type1 = new ReimbursmentType("Equipment");
		ReimbursmentType type2 = new ReimbursmentType("Food");
		ReimbursmentType type3 = new ReimbursmentType("Lodging");
		ReimbursmentType type4 = new ReimbursmentType("Other");
		ReimbursmentType type5 = new ReimbursmentType("Training");
		ReimbursmentType type6 = new ReimbursmentType("Travel");
		reimbursmentTypeHandler.create(type1);
		reimbursmentTypeHandler.create(type2);
		reimbursmentTypeHandler.create(type3);
		reimbursmentTypeHandler.create(type4);
		reimbursmentTypeHandler.create(type5);
		reimbursmentTypeHandler.create(type6);
	}
	@Test
	public void test() {
		assertNotNull(reimbursmentTypeHandler.get("Equipment"));
		assertNotNull(reimbursmentTypeHandler.get("Food"));
		assertNotNull(reimbursmentTypeHandler.get("Lodging"));
		assertNotNull(reimbursmentTypeHandler.get("Other"));
		assertNotNull(reimbursmentTypeHandler.get("Training"));
		assertNotNull(reimbursmentTypeHandler.get("Travel"));
	}
}
