package com.bikram.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class S3BucketUtil {
	static AWSCredentials credentials = new BasicAWSCredentials(
			  "XX", 
			  "XX"
			);
	
	static AmazonS3 s3client = AmazonS3ClientBuilder
			  .standard()
			  .withCredentials(new AWSStaticCredentialsProvider(credentials))
			  .withRegion(Regions.US_EAST_1)
			  .build();
	public static void uploadFile(String fileName,String fileUrl) {
		s3client.putObject(
		  "tcnmfa", 
		  "picture/"+fileName, 
		  new File(fileUrl)
		);
	}
	
	public static InputStream downloadFile(String fileName) {
		S3Object s3object = s3client.getObject("tcnmfa", "picture/"+fileName);
		S3ObjectInputStream inputStream = s3object.getObjectContent();
		return inputStream;
	}
	
	public static String convertInputStreamToFile(InputStream inputStream,String fileLocation) {
		try {
			File targetFile = new File(fileLocation);
			    OutputStream outStream = new FileOutputStream(targetFile);
			 
			    byte[] buffer = new byte[8 * 1024];
			    int bytesRead;
			    while ((bytesRead = inputStream.read(buffer)) != -1) {
			        outStream.write(buffer, 0, bytesRead);
			    }
			    IOUtils.closeQuietly(inputStream);
			    IOUtils.closeQuietly(outStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileLocation;
	}
	
}
