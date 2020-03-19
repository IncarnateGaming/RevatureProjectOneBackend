package com.revature.expenses.exceptions;

public class UserNotFound extends RuntimeException {
	private static final long serialVersionUID = -4775069086931734809L;

	public UserNotFound() {
		super();
	}

	public UserNotFound(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public UserNotFound(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UserNotFound(String arg0) {
		super(arg0);
	}

	public UserNotFound(Throwable arg0) {
		super(arg0);
	}
}
