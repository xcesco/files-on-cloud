package org.abubusoft.foc.admin;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord.CreateRequest;

@RunWith(SpringRunner.class)
public class TestAdmin {
	
	@Before
	public void setup() throws IOException {
		FirebaseOptions options;

		options = new FirebaseOptions.Builder()
				// prende le credenziali da GOOGLE_APPLICATION_CREDENTIALS
				.setCredentials(GoogleCredentials.getApplicationDefault())
				//.setCredentials(GoogleCredentials.fromStream(serviceAccount))					
				.setDatabaseUrl("https://programmazione-web-238419.firebaseio.com").build();

		FirebaseApp.initializeApp(options);
	}
	
	@Test
	public void test() throws FirebaseAuthException {
		ListUsersPage list = FirebaseAuth.getInstance().listUsers(null);
		
		CreateRequest request=new CreateRequest();
		request.setDisplayName("Toninj");
		request.setEmail("a@gmail.com");
		request.setPassword("password");
		
		FirebaseAuth.getInstance().createUser(request);
		// See the UserRecord reference doc for the contents of userRecord.
		
		for (ExportedUserRecord item :list.getValues()) {
			System.out.println("Successfully fetched user data: " + item.getEmail());	
		}
		
	}
	
	@Test
	public void testCreate() throws FirebaseAuthException {				
		CreateRequest request=new CreateRequest();
		request.setDisplayName("Toninj");
		request.setEmail("aa@gmail.com");
		request.setPassword("password");
		
		//FirebaseAuth.getInstance().createUser(request);
		
		String url=FirebaseAuth.getInstance().generatePasswordResetLink("xcesco@gmail.com");
		System.out.println(url);
		// See the UserRecord reference doc for the contents of userRecord.						
	}

}
