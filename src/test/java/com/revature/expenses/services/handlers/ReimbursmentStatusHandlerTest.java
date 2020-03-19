package com.revature.expenses.services.handlers;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.expenses.models.ReimbursmentStatus;

public class ReimbursmentStatusHandlerTest {

	private static ReimbursmentStatusHandler reimbursmentStatusHandler;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		reimbursmentStatusHandler = new ReimbursmentStatusHandler();
		ReimbursmentStatus status1 = new ReimbursmentStatus("Pending");
		ReimbursmentStatus status2 = new ReimbursmentStatus("Approved");
		ReimbursmentStatus status3 = new ReimbursmentStatus("Denied");
		reimbursmentStatusHandler.create(status1);
		reimbursmentStatusHandler.create(status2);
		reimbursmentStatusHandler.create(status3);
	}

	@Test
	public void test() {
		assertNotNull(reimbursmentStatusHandler.get("Pending"));
		assertNotNull(reimbursmentStatusHandler.get("Approved"));
		assertNotNull(reimbursmentStatusHandler.get("Denied"));
	}

}
