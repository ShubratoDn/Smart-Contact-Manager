package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.scm.dao.UserRepository;
import com.scm.entities.User;

public class UserDetailsServiceImple implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepo.getUserByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("user Not Founds");
		}
		
		//eta user info validate korar jonno....din seshe User e return korbe
		CustomUserDetails cusObject = new CustomUserDetails(user);
		
		return cusObject;
	}

}
