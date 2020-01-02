package com.bikram.dao;

import java.util.List;

import com.bikram.bean.MFABean;

public interface MFADao {

	 void insertOrUdateMFA(String email,String companyName,String qrPath,String code,String secretKey,String fileName);
	 public List<MFABean> getAllMFA();
	 public String validateMFA(String email,String code);
}
