package com.scm.entities;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="password_recover")
public class PasswordRecover {
	
	@Id
	private int id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	private int OTP;
	private String token;
	private int expired;
	private Timestamp created;
	


	public PasswordRecover() {
		super();
	}
	
	
	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}


	@Override
	public String toString() {
		return "PasswordRecover [id=" + id + ", user=" + user + ", OTP=" + OTP + ", token=" + token + ", expired="
				+ expired + ", created=" + created + "]";
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getOTP() {
		return OTP;
	}
	public void setOTP(int oTP) {
		OTP = oTP;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getExpired() {
		return expired;
	}
	public void setExpired(int expired) {
		this.expired = expired;
	}
	
	
	
}
