package com.bikram.bean;

import org.springframework.stereotype.Component;

@Component
public class ValidateMFARequest {
private String email;
private String code;
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}

}
