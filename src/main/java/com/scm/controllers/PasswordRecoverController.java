package com.scm.controllers;

import java.sql.Timestamp;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scm.dao.PasswordRecoverRepository;
import com.scm.dao.UserRepository;
import com.scm.entities.PasswordRecover;
import com.scm.entities.User;
import com.scm.helpers.SendEmail;
import com.scm.helpers.ServerMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/recover")
public class PasswordRecoverController {	
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordRecoverRepository passRepo;
	@Autowired
	private SendEmail sendEmail ;
	
	
	@GetMapping("/forgettext")
	@ResponseBody
	public String test() {
		
		String email = "shubratodn44985@gmail.com";
		
		User user = userRepo.getUserByEmail(email);
		
		PasswordRecover passRecover = new PasswordRecover();
		passRecover.setUser(user);
		passRecover.setOTP(1234);
		passRecover.setToken("abcd");
		passRecover.setExpired(0);
		
		PasswordRecover save = passRepo.save(passRecover);
		
		
		return "Added" + save;
	}
	
	
	//page tay anar jonno
	@GetMapping("/forget-password")
	public String forgetReqHandler(HttpServletRequest req) {		
		//logout kore dilam
		try {
			req.getSession().invalidate();
			req.logout();
			System.out.println("Loggged Out");
			
		} catch (Exception e) {
			System.out.println("err " + e);
		}	
				
		return "recoverPassword/forget_password";
	}
	
	
	
	//
	@PostMapping("/forget-password")
	public String forgetPasswordInfoHandler(@RequestParam("email") String email, Model model, RedirectAttributes redireAttributes, HttpSession session) {
		
		
		
		//otp ar token  generate kortesi
		Random rand = new Random();		
		int otp = 1000 + rand.nextInt( 9999 - 1000);		
		
		String token = "";
		for (int i = 0; i < 50; i++) {
		      char randomLetter = (char) (rand.nextInt(26) + 'a');
		      token+=randomLetter;
		}		
		
		
		//checking if the email is exist or not
		User user = userRepo.getUserByEmail(email);		
		
		
		System.out.println("OTP " + otp);
		System.out.println("Token  " + token);
		
		
		//email ta registered ki na check korlam
		if(user == null) {			
			//jeno eitar value onno method a o use korte pari
			redireAttributes.addFlashAttribute("msg", new ServerMessage("Invalid user!", "error", "alert-danger"));
			return "redirect:/recover/forget-password?invalid";
		}
		
		//jeno onno method thekeo user ta pai
		session.setAttribute("userOfOTPsender", user);	
		
		
		//ager kono data ache ki na dekhtesi
		PasswordRecover oldOtp = passRepo.findByUser(user);
		
		
		boolean sendOTP = false;
		boolean isExpired = false;
		
		
		
		//jodi data na thake
		if(oldOtp == null) {
			sendOTP = true;
		}else {
			//jehetu otp exist kore tai ami eibar check korbo EXPIRED ki na otp ta
			System.out.println("Crated time is " + oldOtp.getCreated());
			System.out.println("Converting time " + oldOtp.getCreated().getTime());
			
			long dbTime = oldOtp.getCreated().getTime();
			
			long timeGap = 1 * 60 * 1000;
			long created = (dbTime - dbTime % 1000) / 1000;
			long expired = ((dbTime + timeGap) - (created + timeGap) % 1000) / 1000;

			long currentTimeMili = new Timestamp(System.currentTimeMillis()).getTime();
			long currentTime = (currentTimeMili - currentTimeMili % 1000) / 1000;
			
			System.out.println( "Created at : " + created);
			System.out.println( "Expired at : " + expired);
			System.out.println( "time is    : " + currentTime);
			
			
			
			if(expired < currentTime) {
				//ei condition a expire hoye gese time ta
				model.addAttribute("msg", new ServerMessage("Expired!", "info", "alert-primary"));
				sendOTP = true;	
				isExpired = true;
			}else {
				//expire na hole msg dekhabo j OTP mail a send korsi
				model.addAttribute("msg", new ServerMessage("Already sent OTP in your Mail!", "info", "alert-primary"));
				return "recoverPassword/verify_otp";
			}
			
		}
		
		
		
		//OTP send kortesi
		if(sendOTP) {
			boolean isSent = sendEmail.sendOTP(email, otp, token);
			
			//jodi email send hoy tahole database e upload kore dicchi data gula
			if(isSent) {
				PasswordRecover pr =  new PasswordRecover();
				pr.setUser(user);
				pr.setOTP(otp);
				pr.setToken(token);
				pr.setExpired(0);
				
				PasswordRecover prRes;
				if(isExpired) {
					oldOtp.setOTP(otp);
					oldOtp.setCreated(new Timestamp(System.currentTimeMillis()));
					prRes = passRepo.save(oldOtp);
				}else {					
					prRes = passRepo.save(pr);
				}
				
				if(prRes != null) {
					model.addAttribute("msg", new ServerMessage("<b>Success!</b> Please check your Email for OTP", "success", "alert-success"));
				}else {
					model.addAttribute("msg", new ServerMessage("<b>Error!</b> Failed to upload OTP in DB", "error", "alert-danger"));
				}
				
			}else {
				//kono error asle
				model.addAttribute("msg", new ServerMessage("<b>Failed to send Email!</b>", "error", "alert-danger"));
			}
		}
		
			
		
		return "recoverPassword/verify_otp";
	}
	
	
	
	
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam("otp") String otp, Model model, HttpSession session) {
		
		User user = (User) session.getAttribute("userOfOTPsender");
		
		if(user == null) {
			System.out.println("Session faka");
			return "redirect:/recover/forget-password";
		}
		
		PasswordRecover pr = passRepo.findByUser(user);
		
		if(otp.equals(pr.getOTP()+"")) {
//			System.out.println("OTP match korse");			
			return "recoverPassword/change_password";
		}else {
			model.addAttribute("msg", new ServerMessage("<b>OTP not matching</b>", "error", "alert-danger"));
			return "recoverPassword/verify_otp";
		}
	}
	
	
	@Autowired
	private BCryptPasswordEncoder passencode;
	
	@PostMapping("/change-password")
	public String changePass(@RequestParam("password") String password, RedirectAttributes redirectAttributes,HttpSession session) {
		
		String newPass = passencode.encode(password);
		
		User user = (User) session.getAttribute("userOfOTPsender");
		if(user == null) {
			return "redirect:/recover/forget-password";
		}
		user.setPassword(newPass);
		
		userRepo.save(user);
		
		session.removeAttribute("userOfOTPsender");
		
		redirectAttributes.addFlashAttribute("msg", new ServerMessage("Password changed successfully", "success", "alert-success"));		
		
		return "redirect:/signin";
	}
	
	
}
