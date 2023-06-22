package com.scm.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


/**
 * @author SDN
 *
 */
@Entity
@Table(name ="contact")
public class Contact {

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int con_id;	
	
	@ManyToOne	
	@JsonIgnore	
	private User user;
	
	@NotBlank(message = "Name can not be blank")
	private String name;
	
	@NotBlank(message = "Enter phone number")
	private String phone;
	
	@NotBlank(message = "Enter email")
	@Email(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid Email")
	private String email;
	
	@NotBlank(message = "Enter work address")
	private String work_place;
	private String image;
	private String description;
	
	
	public Contact() {
		super();
	}
	
	
	public int getCon_id() {
		return con_id;
	}
	public void setCon_id(int con_id) {
		this.con_id = con_id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWork_place() {
		return work_place;
	}
	public void setWork_place(String work_place) {
		this.work_place = work_place;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


//	@Override
//	public String toString() {
//		return "Contact [con_id=" + con_id + ", user=" + user + ", name=" + name + ", phone=" + phone + ", email="
//				+ email + ", work_place=" + work_place + ", image=" + image + ", description=" + description + "]";
//	}

	
	
	
}
