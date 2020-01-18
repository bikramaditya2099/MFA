package com.bikram.util;

public enum StatusCode {

	SUCCESS(000,"SUCCESS"),
	FAIL(999,"FAIL");
	
	int code;
	String status;
	private StatusCode(int code, String status) {
		this.code = code;
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public String getStatus() {
		return status;
	}
	
	
}
