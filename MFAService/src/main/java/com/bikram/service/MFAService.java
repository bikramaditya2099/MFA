package com.bikram.service;

public interface MFAService {

	public Object generateMfacode(String email,String companyName);
	public String validateMFA(String email,String code);
}
