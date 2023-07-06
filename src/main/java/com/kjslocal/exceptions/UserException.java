package com.kjslocal.exceptions;

public class UserException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private final String errorMessage;

	public UserException(String errMessage) {
		super();
		this.errorMessage = errMessage;
	}

	public String getErrMessage() {
		return errorMessage;
	}

}
