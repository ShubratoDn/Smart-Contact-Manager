package com.scm.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {

	@Id	
	@Column(name="id")
	private int user_id;
	private String role;
	private String status;
	
	
	@NotBlank(message = "Enter your name")
	@Size(min = 3, max=50, message = "Name size should be between 3 - 50 letters")
	private String name;
	
	@NotBlank(message = "Enter your email")
	@Email(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid Email")
//	@Pattern(regexp = "^(.+)@(\\\\S+)$", message = "Invalid Email")
	private String email;
	
	@NotBlank( message="Enter your password")
	@Size(min = 4, max=300, message = "Minimum Enter 4 character")
	private String password;
	private String image;
	
	private String about;
	private Timestamp register_date;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY ,mappedBy = "user")	
	private List<Contact> contacts = new ArrayList<>();	
	
	
	//eisob na korleo kaj korbe
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private PasswordRecover passwordRecover;

	public User() {
		super();
	}	
	
	public PasswordRecover getPasswordRecover() {
		return passwordRecover;
	}

	public void setPasswordRecover(PasswordRecover passwordRecover) {
		this.passwordRecover = passwordRecover;
	}

	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Timestamp getRegister_date() {
		return register_date;
	}

	public void setRegister_date(Timestamp register_date) {
		this.register_date = register_date;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	
	
}
