package com.revature.expenses.exceptions;

public class ConnectionToDatabaseFailed extends RuntimeException {

	private static final long serialVersionUID = -2333130940979138346L;

	public ConnectionToDatabaseFailed() {
		super();
	}

	public ConnectionToDatabaseFailed(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ConnectionToDatabaseFailed(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ConnectionToDatabaseFailed(String arg0) {
		super(arg0);
	}

	public ConnectionToDatabaseFailed(Throwable arg0) {
		super(arg0);
	}
}
