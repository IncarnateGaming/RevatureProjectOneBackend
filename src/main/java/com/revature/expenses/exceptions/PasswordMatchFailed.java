package com.revature.expenses.exceptions;

public class PasswordMatchFailed extends RuntimeException {
	private static final long serialVersionUID = -5736884587328697920L;

	public PasswordMatchFailed() {
		super();
	}

	public PasswordMatchFailed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PasswordMatchFailed(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordMatchFailed(String message) {
		super(message);
	}

	public PasswordMatchFailed(Throwable cause) {
		super(cause);
	}

}
