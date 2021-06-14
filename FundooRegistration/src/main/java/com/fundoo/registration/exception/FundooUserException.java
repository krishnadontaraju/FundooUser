package com.fundoo.registration.exception;

@SuppressWarnings("serial")
public class FundooUserException extends Exception {

	private int code;
	public FundooUserException(int code, String message) {
	
		super(message);
		this.setCode(code);
		
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
