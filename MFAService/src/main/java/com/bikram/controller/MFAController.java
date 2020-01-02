package com.bikram.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bikram.bean.MFARequestBean;
import com.bikram.bean.ValidateMFARequest;
import com.bikram.service.MFAService;
import com.bikram.util.S3BucketUtil;


@Controller
@CrossOrigin
public class MFAController {
	@Autowired
	private MFAService mFAServiceImpl;
	

	@RequestMapping(value = "/generateMFACode", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Resource> generateMFACode(@RequestBody MFARequestBean mFARequestBean,HttpServletRequest request) {
		 InputStream in=(InputStream) mFAServiceImpl.generateMfacode(mFARequestBean.getEmail(), mFARequestBean.getCompanyName());
		 String fileName=S3BucketUtil.convertInputStreamToFile(in, System.getProperty("user.home")+File.separator+System.currentTimeMillis()+".png");
		 Resource resource=null;
		 try {
			 Path path= Paths.get(fileName).normalize();
			 
			 resource=new UrlResource(path.toUri());
			 String contentType = null;
		        try {
		            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		        } catch (IOException ex) {
		           
		        }

		        // Fallback to the default content type if type could not be determined
		        if(contentType == null) {
		            contentType = "application/octet-stream";
		        }

		        return ResponseEntity.ok()
		                .contentType(MediaType.parseMediaType(contentType))
		                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
		                .body(resource);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return null;
	}
	
	@RequestMapping(value = "/validateMFACode", method = RequestMethod.POST, produces = "text/plain")
	@ResponseBody
	public String validateMFACode(@RequestBody ValidateMFARequest validateMFARequest,HttpServletRequest request) {
		
		return mFAServiceImpl.validateMFA(validateMFARequest.getEmail(), validateMFARequest.getCode());
	}
}
