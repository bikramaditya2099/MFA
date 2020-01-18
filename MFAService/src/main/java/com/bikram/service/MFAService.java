package com.bikram.service;

import com.bikram.util.Response;

public interface MFAService {

	public Object generateMfacode(String email,String companyName);
	public Response validateMFA(String email,String code);
}
