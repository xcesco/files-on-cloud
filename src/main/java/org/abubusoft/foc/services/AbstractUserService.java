package org.abubusoft.foc.services;

import java.util.Optional;

import org.abubusoft.foc.model.User;

public interface AbstractUserService<U extends User> {
		
	U createUser(U user, String password);
		
	int deleteByUsername(String username);
	
	boolean deleteById(long id);

	String generateChangePasswordUrl(String username);

	Iterable<U> findAll();
	
	Optional<U> findById(long id);
	
	U findByUsername(String username);

	U updateByUsername(U user);
	
	U updateById(U user);		
	
}

