package org.abubusoft.foc.admin;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.abubusoft.foc.BaseTest;
import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.services.AdminService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.firebase.auth.FirebaseAuthException;

public class TestAdmin extends BaseTest {
	
	@Autowired
	protected AdminService service;
	

	@Test
	public void test0ChangePassword() throws FirebaseAuthException {
		Administrator user = createUser();
		
		String urlToApply=service.getChangePasswordUrlByUsername(user.getUsername());
		logger.info(urlToApply);
	}

	private Administrator createUser() {
		String displayName="Tonino Carino Da Ascoli";
		String username="admin-" + System.currentTimeMillis() + "@gmail.com";
		String email="uxcesco@gmail.com";
		String password="password";
		
		Administrator user=new Administrator();
		user.setDisplayName(displayName);
		user.setEmail(email);
		user.setUsername(username);
		
		return service.createUser(user, password);
	}
	
	@Test
	public void test1Update() throws FirebaseAuthException {
		String display="XXXX";
		
		Administrator user = createUser();		
		user.setDisplayName(display);
		
		service.updateByUsername(user);
		
		Administrator user1 = service.findByUsername(user.getUsername());
		
		assertEquals(user1.getDisplayName(), display);
	}
	
	@Transactional
	@Test	
	public void test2Delete() throws FirebaseAuthException {
		Administrator user = createUser();		
		
		int result=adminService.deleteByUsername(user.getUsername());
		
		logger.info("deleted "+result);
	}

	
	@Test
	public void test3Create() throws FirebaseAuthException {
		createUser();
	}

}
