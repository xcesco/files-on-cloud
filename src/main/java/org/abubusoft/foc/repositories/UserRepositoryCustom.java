package org.abubusoft.foc.repositories;

import org.abubusoft.foc.model.User;

public interface UserRepositoryCustom {
	User findByEmail(String email);
		
}