package org.abubusoft.foc.services;

import org.abubusoft.foc.model.User;

public interface LoginService {

	User findByUsername(String username);

	void deleteByUsername(String username);

	void deleteAll();
	
}