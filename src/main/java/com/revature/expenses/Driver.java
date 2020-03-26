package com.revature.expenses;

import com.revature.expenses.services.handlers.ReimbursmentHandler;

public class Driver {
	public static void main(String[] args) {
		ReimbursmentHandler reimbursmentHandler = new ReimbursmentHandler();
		System.out.println(reimbursmentHandler.list(10, 5));
	}
}
