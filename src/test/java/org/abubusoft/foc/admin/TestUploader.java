package org.abubusoft.foc.admin;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.transaction.Transactional;

import org.abubusoft.foc.BaseTest;
import org.abubusoft.foc.business.services.UploaderService;
import org.abubusoft.foc.repositories.model.Uploader;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.google.firebase.auth.FirebaseAuthException;

@Rollback(value=false)
@Transactional
public class TestUploader extends BaseTest {
	
	@Autowired
	protected UploaderService service;
	

	@Test
	public void test0ChangePassword() throws FirebaseAuthException, FileNotFoundException, IOException {
		Uploader user = createUser();
		
		String urlToApply=service.getChangePasswordUrlById(user.getId());
		logger.info(urlToApply);
	}

	private Uploader createUser() throws FileNotFoundException, IOException {
		String displayName="Tonino Carino Da Ascoli";
		String username="uploader-" + System.currentTimeMillis() + "@gmail.com";
		String email="uxcesco@gmail.com";
		String password="password";
		
		File image=new File("src/test/resources/images/user.png");
		
		Uploader user=new Uploader();
		user.setDisplayName(displayName);
		user.setEmail(email);
		user.setUsername(username);
		user.setImage(IOUtils.toByteArray(new FileInputStream(image)));
		
		return service.insertUser(user, password);
	}
	
	@Test
	public void test1Update() throws FirebaseAuthException, FileNotFoundException, IOException {
		String display="XXXX";
		
		Uploader user = createUser();		
		user.setDisplayName(display);
		
		service.updateById(user);
		
		Uploader user1 = service.findByUsername(user.getUsername());
		
		assertEquals(user1.getDisplayName(), display);
	}
	
	@Test	
	public void test2Delete() throws FirebaseAuthException, FileNotFoundException, IOException {
		Uploader user = createUser();		
		
		boolean result=service.deleteById(user.getId());
		
		logger.info("deleted "+result);
	}


	@Test
	public void test3Create() throws FileNotFoundException, IOException {
		createUser();
	}
	


}
