package com.bikram.util;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.bikram.bean.MFABean;
import com.bikram.dao.MFADao;
import com.bikram.dao.MFADaoImpl;

public class BackupMFA implements Runnable{
	private String email;
	private String companyName;
	private String qrPath;
	private String secretKey;
	private String lastCode;
	private String fileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	MFADao dao;
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
	public String getQrPath() {
		return qrPath;
	}
	public void setQrPath(String qrPath) {
		this.qrPath = qrPath;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getLastCode() {
		return lastCode;
	}
	public void setLastCode(String lastCode) {
		this.lastCode = lastCode;
	}
	@Override
	public void run() {
		processMFA(email, companyName, secretKey, qrPath, lastCode,fileName);
		
	}
	
	private void processMFA(String email, String companyName, String secretKey, String qrCodePath, String lastCode,String fileName) {
		
		if(dao==null)
			dao=new MFADaoImpl();
		while (true) {
		    String code = MFAUtil.getTOTPCode(secretKey);
		    if (!code.equals(lastCode)) {
		        System.out.println(code);
		        dao.insertOrUdateMFA(email, companyName, qrCodePath,code,secretKey,fileName);
		    }
		    lastCode = code;
		    try {
		        Thread.sleep(1000);
		    } catch (InterruptedException e) {};
		}
	}
	public  void generateDefaultMFA() {
		 this.dao=new MFADaoImpl();
		List<MFABean> list=dao.getAllMFA();
		if(list.size()>0)
		for(MFABean bean:list) {
			BackupMFA backupMFA=new BackupMFA();
			backupMFA.setEmail(bean.getEmail());
			backupMFA.setCompanyName(bean.getCompanyName());
			
			backupMFA.setLastCode(bean.getCode());
			backupMFA.setSecretKey(bean.getSecretKey());
			InputStream in=S3BucketUtil.downloadFile(bean.getFileName());
			String qrPath=S3BucketUtil.convertInputStreamToFile(in, System.getProperty("user.home")+File.separator+bean.getFileName());
			backupMFA.setQrPath(qrPath);
			 Thread t=new Thread(backupMFA);
		        t.start();
		}
	}

}
