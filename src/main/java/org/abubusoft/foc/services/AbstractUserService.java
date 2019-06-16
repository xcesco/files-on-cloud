package org.abubusoft.foc.services;

import org.abubusoft.foc.model.User;

public interface AbstractUserService<U extends User> {
		
	U createUser(U user, String password);
		
	int deleteByUsername(String username);

	String generateChangePasswordUrl(String username);

	Iterable<U> findAll();
	
	U findByUsername(String username);

	U updateByUsername(U user);
	
}

