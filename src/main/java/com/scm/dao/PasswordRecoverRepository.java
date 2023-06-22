package com.scm.dao;

import org.springframework.data.repository.CrudRepository;

import com.scm.entities.PasswordRecover;
import com.scm.entities.User;

public interface PasswordRecoverRepository extends CrudRepository<PasswordRecover, Integer> {

	public PasswordRecover findByUser(User user);
	
}
