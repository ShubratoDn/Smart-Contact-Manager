package com.scm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactRepository extends CrudRepository< Contact, Integer > {

	@Query(value="select * from contact where user_id = :userId ", nativeQuery = true )
	public List<Contact> getContactsByUserId(@Param("userId")Integer userId);	
	
	
	@Query(value="select * from contact where user_id = :userId limit :startsFrom, :row", nativeQuery = true )
	public List<Contact> getContactsByUserIdForPagination(@Param("userId")Integer userId, @Param("startsFrom") Integer startsFrom,  @Param("row") Integer rowPerPage);
	
	@Query(value= "SELECT * FROM `contact` WHERE user_id= :userId and id= :contactId", nativeQuery = true)	
	public Contact getContactWhereUseridAndIdMatches(@Param("userId") int userId, @Param("contactId") int contactId);
	
	
	public List<Contact> findByNameContainingAndUser(String name, User user);
	
	
	
}
