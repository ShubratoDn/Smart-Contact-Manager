package com.scm.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.dao.UserRepository;
import com.scm.entities.User;
import com.scm.helpers.ServerMessage;

import jakarta.validation.Valid;

@Controller()
public class MainController {

	private String classActive = "";
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
//	home Handler
	@RequestMapping(value = {"/", "/home"})
	public String homeHandler(Model model) {	
		classActive = "/home";
		model.addAttribute("classActive", classActive);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toArray()[0].toString();
		
		//checking for if user is logged in or not
		if(role.equals("ROLE_USER")) {
			return "redirect:/user/home";
		}


		
		return "home";
	}	
	
	
	@GetMapping("/signup")
	public String signupHandler(Model model) {
		classActive = "/signup";
		model.addAttribute("classActive", classActive);
		
		//eta korlam jeno form e jei user object neya setate error na dekhay
		model.addAttribute("user", new User());
		return "signup";
	}

	
	//user registration
	@PostMapping("/signup")
	public String registerHandler(
			@Valid @ModelAttribute("user") User user,
			BindingResult res,
			@RequestParam(value="agreement", defaultValue = "false") boolean agreement,			
			Model model) {

		//nav bar a active class add kora
		classActive = "/signup";
		model.addAttribute("classActive", classActive);
		
		try {			
			
			//jodi agreement check na kore tahole
			if(!agreement) {
//				System.out.println("Please, Accept the terms and Conditons");
				model.addAttribute("msg", new ServerMessage("Please, Accept the terms and Conditons", "error", "alert-danger"));
				model.addAttribute("agreement", "Please, Accept the terms and Conditons now");
				return "signup";
			}			
			
			//validation input
			if(res.hasErrors()) {
				System.out.println(res);				
				return "signup";
			}
			
			//check if email already exist
			User checkAvailableUser = userRepo.getUserByEmail(user.getEmail());
			if(checkAvailableUser != null) {
				System.out.println(checkAvailableUser);
				model.addAttribute("msg", new ServerMessage("This email is already used", "error", "alert-danger"));
				return "signup";
			}
			
			//CREATING DYNAMIC ERROR FOR NAME FIELD 
//			res.rejectValue("name", "user.name" , "Demo error");
//			res.rejectValue("name", "demo.name" , "Demo error");
			
			
			//setting default values
			user.setRole("ROLE_USER");
			user.setStatus("active");
			user.setImage("user.jpg");		
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			if(user.getAbout().isBlank()) {
				user.setAbout("I'm the user of Smart Contact Manager");			
			}			
			
			//inserting data to database
			User userResult = userRepo.save(user);
			if(userResult != null) {
				model.addAttribute("msg", new ServerMessage("Registration Successfull", "success", "alert-success"));
				model.addAttribute("user", new User());
			}else {
				throw new Exception("FILE UPLOAD FAILED");
			}
			
			
			return "signup";
		}catch (Exception e) {
			System.out.println("error in registerHandler " + e);
			model.addAttribute("msg", new ServerMessage("Enternal Server Error", "error", "alert-error"));
			return "signup";
		}		
	}
	
	
	//sign in page
	@GetMapping("/signin")
	public String signinreqHandler(Model model) {
		classActive = "/signin";
		model.addAttribute("classActive", classActive);
		
		return "signin";
	}
	
}
