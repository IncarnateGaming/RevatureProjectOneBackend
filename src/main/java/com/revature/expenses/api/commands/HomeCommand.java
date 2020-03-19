package com.revature.expenses.api.commands;

import java.io.IOException;

import javax.servlet.ServletException;

import com.revature.expenses.api.FrontCommand;

public class HomeCommand extends FrontCommand {

	@Override
	public void process() throws ServletException, IOException {
		forward("unkown");
	}

}
