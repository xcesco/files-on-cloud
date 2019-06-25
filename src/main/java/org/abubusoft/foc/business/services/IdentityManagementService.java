package org.abubusoft.foc.business.services;

import org.abubusoft.foc.model.User;

public interface IdentityManagementService {

	User findByUsername(String username);

	void deleteByUsername(String username);

	void deleteAllUsers();
	
}