package com.bikram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.bikram.service.MFAServiceImpl;
import com.bikram.util.BackupMFA;

@SpringBootApplication
public class MfaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MfaServiceApplication.class, args);
		BackupMFA impl=new BackupMFA();
		impl.generateDefaultMFA();
	}

}
