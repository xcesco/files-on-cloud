package org.abubusoft.foc.business.services;

import java.util.Optional;

import org.abubusoft.foc.repositories.model.User;

public interface AbstractUserService<U extends User> {
		
	U insertUser(U user, String password);
		
	boolean deleteById(long id);
	
	void deleteAll();
	
	String getChangePasswordUrlById(long id);

	Iterable<U> findAll();
	
	Optional<U> findById(long id);
	
	U findByUsername(String username);

	U updateById(U user);		
	
}

