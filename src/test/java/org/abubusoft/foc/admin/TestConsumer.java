package org.abubusoft.foc.admin;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.abubusoft.foc.BaseTest;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.services.ConsumersService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.firebase.auth.FirebaseAuthException;

public class TestConsumer extends BaseTest {
	
	@Autowired
	protected ConsumersService service;
	

	@Test
	public void test0ChangePassword() throws FirebaseAuthException {
		Consumer user = createUser();
		
		String urlToApply=service.getChangePasswordUrlByUsername(user.getUsername());
		logger.info(urlToApply);
	}

	private Consumer createUser() {
		String displayName="Tonino Carino Da Ascoli";
		String username="consumer-" + System.currentTimeMillis() + "@gmail.com";
		String email="uxcesco@gmail.com";
		String password="password";
		
		Consumer user=new Consumer();
		user.setUsername(username);
		user.setDisplayName(displayName);
		user.setEmail(email);		
		user.setCodiceFiscale(""+System.currentTimeMillis());
		
		return service.createUser(user, password);
	}
	
	@Test
	public void test1Update() throws FirebaseAuthException {
		String display="XXXX";
		
		Consumer user = createUser();		
		user.setDisplayName(display);
		
		service.updateByUsername(user);
		
		Consumer user1 = service.findByUsername(user.getUsername());
		
		assertEquals(user1.getDisplayName(), display);
	}
	
	@Transactional
	@Test	
	public void test2Delete() throws FirebaseAuthException {
		Consumer user = createUser();		
		
		int result=adminService.deleteByUsername(user.getUsername());
		
		logger.info("deleted "+result);
	}

	
	@Test
	public void testAdmin3Create() throws FirebaseAuthException {
		createUser();
	}

}
