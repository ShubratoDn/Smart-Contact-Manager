package com.scm.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.scm.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	@Query(value= "select * from user where email= :email", nativeQuery = true )
	public User getUserByEmail(@Param("email") String email);
}
