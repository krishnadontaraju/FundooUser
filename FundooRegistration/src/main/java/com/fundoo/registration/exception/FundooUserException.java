package com.fundoo.registration.exception;

public class FundooUserException extends Exception {

	private int code;
	public FundooUserException(int code, String message) {
	
		super(message);
		this.code = code;
		
	}
}
