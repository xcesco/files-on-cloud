package org.abubusoft.foc.admin;

import org.abubusoft.foc.BaseTest;
import org.abubusoft.foc.services.LoginService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.firebase.auth.FirebaseAuthException;

public class TestLogin extends BaseTest {
	
	@Autowired
	protected LoginService service;
	


	@Test
	public void testDeleteAllUsers() throws FirebaseAuthException {
		
		
		service.deleteAll();
		
	}
	
	
	

}
