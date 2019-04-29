package org.abubusoft.foc.services;

import java.util.List;

import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.model.User;

public interface UserService {

	List<Administrator> findAll();

	void add(Administrator value);

	User findByEmail(String email);
	
}