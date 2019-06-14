package org.abubusoft.foc.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.abubusoft.foc.BaseTest;
import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.repositories.GenericUserRepository;
import org.abubusoft.foc.services.AdminService;
import org.abubusoft.foc.services.UploaderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

public class TestUploader extends BaseTest {
	
	@Autowired
	protected UploaderService uploaderService;
	
	
	@Test
	public void testUploader1ChangePassword() throws FileNotFoundException {
		String displayName="Tonino Carino Da Ascoli";
		String username="uploader-" + System.currentTimeMillis() + "@gmail.com";
		String email="uxcesco@gmail.com";
		String password="password";
		
		File image=new File("src/test/resources/images/user.png");
		System.out.print(image.getAbsolutePath());
		
		uploaderService.createUploader(username, email, displayName, password, new FileInputStream(image));	
		String urlToApply=uploaderService.generateChangePasswordUrl(email);
		log.info(urlToApply);
	}
	
	@Test
	public void testUploader2Update() throws FileNotFoundException {
		String displayName="Tonino Carino Da Ascoli";
		String username="admin-" + System.currentTimeMillis() + "@gmail.com";
		String email="uxcesco@gmail.com";
		String password="password";
		
		File image=new File("src/test/resources/images/user.png");
		System.out.print(image.getAbsolutePath());
		
		Uploader user = uploaderService.createUploader(username, email, displayName, password, null);
		
		user.setDisplayName("Provolone");
		uploaderService.updateUploaderByUsername(user.getUsername(), user.getEmail(), user.getDisplayName());		
		uploaderService.updateUploaderLogo(user.getUsername(), new FileInputStream(image));
		
		String urlToApply=adminService.generateChangePasswordUrl(email);
		log.info(urlToApply);
	}
		
	@Test
	@Transactional
	public void testUploader2Delete() throws FirebaseAuthException {
		String displayName="Tonino Carino Da Ascoli";
		String username="admin-" + System.currentTimeMillis() + "@gmail.com";
		String email="uxcesco@gmail.com";
		String password="password";
		
		Uploader user = uploaderService.createUploader(username, email, displayName, password, null);
		
		int result=uploaderService.deleteByUsername(user.getUsername());
		
		log.info("deleted "+result);
	}
		
	@Test
	public void test() throws FirebaseAuthException  {
		ListUsersPage list = FirebaseAuth.getInstance().listUsers(null);		
		
		String email="test-" + System.currentTimeMillis() + "@gmail.com";

		try {
			UserRecord userFound = FirebaseAuth.getInstance().getUserByEmail(email);
		} catch (FirebaseAuthException ax) {
			CreateRequest request = new CreateRequest();
			request.setDisplayName("Toninj");
			request.setEmail(email);
			request.setPassword("password");

			FirebaseAuth.getInstance().createUser(request);
			// See the UserRecord reference doc for the contents of userRecord.

			for (ExportedUserRecord item : list.getValues()) {
				System.out.println("Successfully fetched user data: " + item.getEmail());
			}
		}
	}
	
	@Test
	public void testUploader0Create() throws FirebaseAuthException {
		String displayName="Tonino Carino Da Ascoli";
		String username="uploader-" + System.currentTimeMillis() + "@gmail.com";
		String email="uxcesco@gmail.com";
		String password="password";
		
		uploaderService.createUploader(username, email, displayName, password, null);
	}
		
	
	@Test
	public void testUploaderCreate() throws FirebaseAuthException, FileNotFoundException {
		uploaderCreate();
	}
	
	public Uploader uploaderCreate() throws FirebaseAuthException, FileNotFoundException {
		long time=System.currentTimeMillis();
		String displayName="Uploader "+time;
		String email="test-uploader-" + time + "@gmail.com";
		String password="password";
		
		File image=new File("src/test/resources/images/user.png");
		System.out.print(image.getAbsolutePath());
		
		return uploaderService.create(email, email, displayName, password, new FileInputStream(image));
	}

	@Test
	public void testCreate() throws FirebaseAuthException {
		CreateRequest request = new CreateRequest();
		request.setDisplayName("Toninj");
		request.setEmail("test-" + System.currentTimeMillis() + "@gmail.com");
		request.setPassword("password");
		
		// FirebaseAuth.getInstance().createUser(request);

		//String url = FirebaseAuth.getInstance().generatePasswordResetLink("xcesco@gmail.com");
		//System.out.println(url);
		// See the UserRecord reference doc for the contents of userRecord.
	}
	
	@Test
	public void testCreateFile() throws FirebaseAuthException, IOException {
		 Consumer consumer = consumerCreate();
		 Uploader uploader=uploaderCreate();
		 
		 UUID.randomUUID().toString();
		 
		 File image=new File("src/test/resources/images/user.png");
		 
		 image.getName();
		 
		 cloudStorageService.uploadFile(uploader, consumer, "user.png", new FileInputStream(image));
	}

}
