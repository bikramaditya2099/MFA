package com.bikram.bean;

import org.springframework.stereotype.Component;

@Component
public class MFARequestBean {
	private String email;
	private String companyName;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
