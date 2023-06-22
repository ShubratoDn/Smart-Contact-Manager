package com.scm.controllers;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.scm.dao.ContactRepository;
import com.scm.dao.UserRepository;
import com.scm.entities.Contact;
import com.scm.entities.User;

@RestController
public class SearchController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ContactRepository conRepo;
	
	
	@GetMapping("/search/{query}")
	public ResponseEntity<List> searchHandler(@PathVariable("query") String query, Principal principal) {
		try {
			
			System.out.println(query);
			
			User user = userRepo.getUserByEmail(principal.getName());
			
			List<Contact> contacts = conRepo.findByNameContainingAndUser(query, user);
			
			
			return ResponseEntity.of(Optional.of(contacts));
			
		}catch (Exception e) {
			System.out.println("error is " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}
