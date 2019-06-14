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
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

public class TestAdmin extends BaseTest {
	
	@Autowired
	protected AdminService adminService;
	
	protected GenericUserRepository userRepository;
	
	@Autowired
	public void setUserRepository(GenericUserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Test
	public void testAdmin0ChangePassword() throws FirebaseAuthException {
		String displayName="Tonino Carino Da Ascoli";
		String username="admin-" + System.currentTimeMillis() + "@gmail.com";
		String email="uxcesco@gmail.com";
		String password="password";
		
		adminService.createAdministrator(username, email, displayName, password);
		
		String urlToApply=adminService.generateChangePasswordUrl(email);
		log.info(urlToApply);
	}
	
	@Test
	public void testAdmin1Update() throws FirebaseAuthException {
		String displayName="Tonino Carino Da Ascoli";
		String username="admin-" + System.currentTimeMillis() + "@gmail.com";
		String email="uxcesco@gmail.com";
		String password="password";
		
		Administrator admin = adminService.createAdministrator(username, email, displayName, password);
		
		admin.setDisplayName("Provolone");
		adminService.updateAdministratorByUsername(admin.getUsername(), admin.getEmail(), admin.getDisplayName());
		
		String urlToApply=adminService.generateChangePasswordUrl(email);
		log.info(urlToApply);
	}
	
	@Transactional
	@Test	
	public void testAdmin2Delete() throws FirebaseAuthException {
		String displayName="Tonino Carino Da Ascoli";
		String username="admin-" + System.currentTimeMillis() + "@gmail.com";
		String email="uxcesco@gmail.com";
		String password="password";
		
		adminService.createAdministrator(username, email, displayName, password);
		
		int result=adminService.deleteByUsername(username);
		
		log.info("deleted "+result);
	}

	@Test
	public void testDeleteAllUsers() throws FirebaseAuthException {
		FirebaseAuth firebase = FirebaseAuth.getInstance();			

		Set<String> uidToDelete=new HashSet<>();
		ListUsersPage list = firebase.listUsers(null);
								
		 while(list!=null) {					
			for (ExportedUserRecord item: list.getValues()) {
				log.info(String.format("Select %s (%s) to delete ", item.getEmail(), item.getUid()));				
				uidToDelete.add(item.getUid());
			}			
			
			list=list.getNextPage();
		}
		
		for (String uid: uidToDelete) {
			firebase.deleteUser(uid);
		}
		
		userRepository.deleteAll();
		
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
	public void testAdmin3Create() throws FirebaseAuthException {
		String displayName="Tonino Carino Da Ascoli";
		String username="admin-" + System.currentTimeMillis() + "@gmail.com";
		String email="uxcesco@gmail.com";
		String password="password";
		
		adminService.createAdministrator(username, email, displayName, password);
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
	public void testCreateFile() throws FirebaseAuthException, IOException {
		 Consumer consumer = consumerCreate();
		 Uploader uploader=uploaderCreate();
		 
		 UUID.randomUUID().toString();
		 
		 File image=new File("src/test/resources/images/user.png");
		 
		 image.getName();
		 
		 cloudStorageService.uploadFile(uploader, consumer, "user.png", new FileInputStream(image));
	}

}
