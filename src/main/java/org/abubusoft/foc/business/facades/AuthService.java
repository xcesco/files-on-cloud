package org.abubusoft.foc.business.facades;

import org.abubusoft.foc.repositories.model.User;

public interface AuthService {

	User findByUsername(String username);

	void deleteByUsername(String username);

	void deleteAllUsers();

	String generateToken(String username);
	
}