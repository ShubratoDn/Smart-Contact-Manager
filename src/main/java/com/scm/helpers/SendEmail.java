package com.scm.helpers;

import java.util.Properties;

import org.springframework.stereotype.Component;
import jakarta.mail.Message;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class SendEmail {
	
	private String host = "smtp.gmail.com";
	private String port =  "587";
	private String userName = "mycomputer44985@gmail.com";
	private String password = "zjhuosmsbolavlbz";
	private Session session ;
	
	
	public SendEmail() {		
		
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", port);
		prop.put("mail.smtp.starttls.enable", true);
		prop.put("mail.smtp.auth", true);
		
		//password verifying
		 session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication(userName, password);
			}
		});
		
//		session.setDebug(true);		
	}
	

	
	public boolean sendOTP(String to, int otp, String token) {
		
		String content= "<h1>Recover Password</h1>"
				+ "<p>Your OTP is : <span style='color: royalblue; font-weight: bold; font-size: 24px'>"+otp+"</span></p>";
		
		try {
			MimeMessage mail = new MimeMessage(session);
			
			mail.setFrom(new InternetAddress(userName, "Smart Contact Manager"));			
			mail.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mail.setSubject("Recover Password - OTP");			
			mail.setContent(content, "text/html");
			
			Transport.send(mail);
			
			return true;
			
		} catch (Exception e) {
			System.out.println("Email send failed" + e);			
			return false;
		}
	}
	
}

