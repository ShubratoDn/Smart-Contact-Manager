package com.scm.helpers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHandler {

	private String fileName = null;
	
	public String uploadFile(String path, MultipartFile file) {
				
		try {
			
			//Random text generate
			SecureRandom random = new SecureRandom();
	        byte[] randomBytes = new byte[10];
	        random.nextBytes(randomBytes);

	        StringBuilder sb = new StringBuilder();
	        for (byte b : randomBytes) {
	            sb.append(String.format("%02x", b));
	        }
	        String randomHexCode = sb.toString();
	        
	        
	        
	        
	        fileName = "_"+randomHexCode+file.getOriginalFilename() ;	        
	        

	        //uploading file
			InputStream is = file.getInputStream();
			byte fileData [] = new byte[is.available()];
			is.read(fileData);
			
			FileOutputStream fos = new FileOutputStream(path+fileName);
			fos.write(fileData);
			
			fos.flush();
			fos.close();
			return fileName;
			
		}catch (Exception e) {
			System.out.println("Error in file uploading : " + e);
			return null;
		}
		
	}
	
}
