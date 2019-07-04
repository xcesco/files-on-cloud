package org.abubusoft.foc.auth;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.transaction.Transactional;

import org.abubusoft.foc.BaseTest;
import org.abubusoft.foc.business.facades.AuthService;
import org.abubusoft.foc.repositories.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
public class TestGenerateToken extends BaseTest {

	@Autowired
	AuthService authService;

	
	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}

	@Test
	public void testAdminToken() throws FileNotFoundException, IOException {
		String username="uxcesco@gmail.com";
		
		User user = authService.findByUsername(username);
		String token=authService.generateToken(user.getUsername());
		
		logger.info(String.format("Token for %s=%s", username, token));

	}

	
	@Test
	public void testConsumerToken() throws FileNotFoundException, IOException {
		String username="uxcesco-consumer@gmail.com";
		
		User user = authService.findByUsername(username);
		String token=authService.generateToken(user.getUsername());
		
		logger.info(String.format("Token for %s=%s", username, token));

	}
	
	@Test
	public void testUploaderToken() throws FileNotFoundException, IOException {
		String username="uxcesco-uploader@gmail.com";
		
		User user = authService.findByUsername(username);
		String token=authService.generateToken(user.getUsername());
		
		logger.info(String.format("Token for %s=%s", username, token));

	}
}
