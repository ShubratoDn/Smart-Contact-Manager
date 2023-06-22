package com.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //eta lekhsi karon amra @Bean annotation use korbo
@EnableWebSecurity
public class SecurityConfig{

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImple(); //Imple class ta return korsi kintu eikhane
	}
	
	@Bean
	public BCryptPasswordEncoder myPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider myAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(myPasswordEncoder());
		
		return daoAuthenticationProvider; 
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		/*GOOGLE THEKE NISI*/
//		 http.cors().and().csrf().disable().authorizeHttpRequests(authorize -> authorize
//                 .requestMatchers("/, /login, /signup, /logout").permitAll()
//         .requestMatchers("/api").hasRole("ADMIN")
//         .requestMatchers("/user").hasRole("USER")
//         .anyRequest().authenticated())
//         .logout().logoutUrl("/logout").logoutSuccessUrl("/").and()
//         .formLogin().loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/user").failureUrl("/login?error");
//		
		 
		 
		 http.cors().and().csrf().disable()
		 .authorizeHttpRequests(auth ->
				 	auth.
				 	requestMatchers("/admin/**").hasRole("ADMIN")
					.requestMatchers("/user/**").hasRole("USER")
					.requestMatchers("/signin").anonymous() //login thakle access korte parbe na
					.requestMatchers("/**").permitAll())
		 .logout().logoutUrl("/signout").logoutSuccessUrl("/signin?logout")
		 .and()
		 .formLogin()
			.loginPage("/signin")
			.loginProcessingUrl("/doSignin")
			.defaultSuccessUrl("/user/home");
		 
		 
//		http.authorizeRequests()
//		.requestMatchers("/admin/**").hasRole("ADMIN")
//		.requestMatchers("/user/**").hasRole("USER")
//		.requestMatchers("/signin").anonymous() //login thakle access korte parbe na
//		.requestMatchers("/**").permitAll()
//		.and().formLogin()
//		.loginPage("/signin")
//		.loginProcessingUrl("/doSignin")
//		.defaultSuccessUrl("/user/home")
//		//.failureForwardUrl("/fail_url")
//		.and().csrf().disable();
//		
		
		
		http.authenticationProvider(myAuthenticationProvider());
		
		DefaultSecurityFilterChain build = http.build();
		return build;
	}

	
}
