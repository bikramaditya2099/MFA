package com.bikram.util;

public class Response {

	int code;
	String status;
	public int getCode() {
		return code;
	}
	public String getStatus() {
		return status;
	}
	
	public Response(StatusCode statusCode) {
		this.code=statusCode.getCode();
		this.status=statusCode.getStatus();
	}
}
