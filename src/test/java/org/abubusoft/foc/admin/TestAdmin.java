package org.abubusoft.foc.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.abubusoft.foc.BaseTest;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.Uploader;
import org.junit.Test;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

public class TestAdmin extends BaseTest {
	
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
	public void testAdminCreate() throws FirebaseAuthException {
		String displayName="Toninj";
		String email="test-" + System.currentTimeMillis() + "@gmail.com";
		String password="password";
		
		adminService.create(email, email, password);
	}
	
	@Test
	public void testConsumerCreate() throws FirebaseAuthException {
		consumerCreate();
	}
	
	public Consumer consumerCreate() throws FirebaseAuthException {
		String displayName="Toninj";
		String email="test-consumer-" + System.currentTimeMillis() + "@gmail.com";
		String password="password";
		String codiceFiscale=""+System.currentTimeMillis();
		
		return consumerService.create(email, email, password, codiceFiscale);
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
		
		return uploaderService.create(email, email, password, new FileInputStream(image));
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
	public void testCreateFile() throws FirebaseAuthException, FileNotFoundException {
		 Consumer consumer = consumerCreate();
		 Uploader uploader=uploaderCreate();
	}

}
