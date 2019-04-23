package org.abubusoft.foc.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.abubusoft.foc.web.V1APIController;
import org.springframework.security.crypto.bcrypt.BCrypt;

@V1APIController
@RestController
public class UserController {

	@GetMapping("/users")
	public String generateUser() {
		String pw_hash = BCrypt.hashpw("ciao", BCrypt.gensalt());
		
		return pw_hash;			
	}
	
}
