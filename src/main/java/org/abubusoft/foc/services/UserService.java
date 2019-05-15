package org.abubusoft.foc.services;

import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.model.User;

public interface UserService {

	Iterable<Administrator> findAll();

	void add(Administrator value);

	User findByEmail(String email);
	
}