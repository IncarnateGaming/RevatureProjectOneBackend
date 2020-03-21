package com.revature.expenses.services.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.expenses.models.Reimbursment;
import com.revature.expenses.models.ReimbursmentStatus;
import com.revature.expenses.models.ReimbursmentType;
import com.revature.expenses.models.User;
import com.revature.expenses.services.helpers.DateHelper;

public class ReimbursmentHandlerTest {
	private static ReimbursmentHandler reimbursmentHandler;
	private static ReimbursmentStatusHandler reimbursmentStatusHandler;
	private static ReimbursmentTypeHandler reimbursmentTypeHandler;
	private static UserHandler userHandler;
	private static User bugs;
	private static User rudolph;
	private static User apple;
	private static User brown;
	private static User orange;
	private static ReimbursmentType equipment;
	private static ReimbursmentType food;
	private static ReimbursmentType lodging;
	private static ReimbursmentType other;
	private static ReimbursmentType training;
	private static ReimbursmentType travel;
	private static ReimbursmentStatus pending;
	private static ReimbursmentStatus approved;
	private static ReimbursmentStatus denied;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		reimbursmentHandler = new ReimbursmentHandler();
		reimbursmentStatusHandler = new ReimbursmentStatusHandler();
		reimbursmentTypeHandler = new ReimbursmentTypeHandler();
		userHandler = new UserHandler();
		bugs = userHandler.get("bugsBunny");//employee
		rudolph = userHandler.get("rudolphRed");//employee
		apple = userHandler.get("apple");//employee
		brown = userHandler.get("brownEyes");//admin
		orange = userHandler.get("orangeSpin");//admin
		equipment = reimbursmentTypeHandler.get("Equipment");
		food = reimbursmentTypeHandler.get("Food");
		lodging = reimbursmentTypeHandler.get("Lodging");
		other = reimbursmentTypeHandler.get("Other");
		training = reimbursmentTypeHandler.get("Training");
		travel = reimbursmentTypeHandler.get("Travel");
		pending = reimbursmentStatusHandler.get("Pending");
		approved = reimbursmentStatusHandler.get("Approved");
		denied = reimbursmentStatusHandler.get("Denied");
	}
	@Test
	public void reimb1() {
		Reimbursment reimbursment = process(19.3587591,bugs,pending,food);
		Reimbursment reimbursmentCheck = reimbursmentHandler.get(reimbursment.getId());
		assertEquals(19.35,reimbursmentCheck.getAmount(),0.0001);
		assertEquals(bugs,reimbursmentCheck.getAuthor());
		assertEquals(pending,reimbursmentCheck.getStatus());
		assertEquals(food,reimbursmentCheck.getType());
		assertNotNull(reimbursmentCheck.getSubmitted());
	}
	@Test
	public void reimb2() {
		Reimbursment reimbursment = process(198_387,rudolph,pending,training);
		Reimbursment reimbursmentCheck = reimbursmentHandler.get(reimbursment.getId());
		assertEquals(198387,reimbursmentCheck.getAmount(),0.0001);
		assertEquals(rudolph,reimbursmentCheck.getAuthor());
		assertEquals(pending,reimbursmentCheck.getStatus());
		assertEquals(training,reimbursmentCheck.getType());
		assertNotNull(reimbursmentCheck.getSubmitted());
	}
	@Test
	public void reimb3() {
		Reimbursment reimbursment = process(-132297.98157643,rudolph,pending,training,  DateHelper.now(), brown, denied);
		Reimbursment reimbursmentCheck = reimbursmentHandler.get(reimbursment.getId());
		assertEquals(132297.98,reimbursmentCheck.getAmount(),0.0001);
		assertEquals(rudolph,reimbursmentCheck.getAuthor());
		assertEquals(training,reimbursmentCheck.getType());
		assertNotNull(reimbursmentCheck.getSubmitted());
		assertNotNull(reimbursmentCheck.getResolved());
		assertEquals(brown,reimbursmentCheck.getResolver());
		assertEquals(denied,reimbursmentCheck.getStatus());
	}
	@Test
	public void reimb4() {
		for (int a = 0; a< 30; a++) {
			Reimbursment reimbursment = process(randomValue(),randomEmployee(),pending, randomType(),  DateHelper.future(), randomAdmin(), randomStatus());
			Reimbursment reimbursmentCheck = reimbursmentHandler.get(reimbursment.getId());
			assertNotNull(reimbursmentCheck);
			reimbursmentCheck = null;
		}
	}
	private Reimbursment process(double amount, User author, ReimbursmentStatus status, ReimbursmentType type) {
		Reimbursment reimbursment = new Reimbursment(amount,author,status,type);
		reimbursment = reimbursmentHandler.create(reimbursment);
		return reimbursment;
	}
	private Reimbursment process(double amount, User author, ReimbursmentStatus status, ReimbursmentType type, Date resolvedDate, User resolver, ReimbursmentStatus changeStatus) {
		Reimbursment reimbursment = process(amount, author, status, type);
		reimbursment.setStatus(changeStatus);
		reimbursment.setResolved(resolvedDate);
		reimbursment.setResolver(resolver);
		reimbursment = reimbursmentHandler.update(reimbursment);
		return reimbursment;
	}
	private double randomValue() {
		return Math.random() * 100;
	}
	private ReimbursmentType randomType() {
		int rand = new Random().nextInt(6);
		ReimbursmentType result = null;
		switch(rand) {
		case 1: 
			result = equipment;
			break;
		case 2:
			result = food;
			break;
		case 3:
			result = lodging;
			break;
		case 4:
			result = other;
			break;
		case 5:
			result = training;
			break;
		default:
			result = travel;
		}
		return result;
	}
	private ReimbursmentStatus randomStatus() {
		int rand = new Random().nextInt(2);
		ReimbursmentStatus result = null;
		switch(rand) {
		case 1: 
			result = approved;
			break;
		default:
			result = denied;
		}
		return result;
	}
	private User randomAdmin() {
		int rand = new Random().nextInt(2);
		User result = null;
		switch(rand) {
		case 1: 
			result = brown;
			break;
		default:
			result = orange;
		}
		return result;
	}
	private User randomEmployee() {
		int rand = new Random().nextInt(3);
		User result = null;
		switch(rand) {
		case 1:
			result = bugs;
			break;
		case 2:
			result = rudolph;
			break;
		default:
			result = apple;
		}
		return result;
	}
}
