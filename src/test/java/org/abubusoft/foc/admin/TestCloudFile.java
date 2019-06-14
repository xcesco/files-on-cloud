package org.abubusoft.foc.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.abubusoft.foc.BaseTest;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.services.CloudFileService;
import org.abubusoft.foc.services.ConsumersService;
import org.abubusoft.foc.services.UploaderService;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.firebase.auth.FirebaseAuthException;

public class TestCloudFile extends BaseTest {
	
	@Autowired
	protected CloudFileService cloudFileService;

	@Autowired
	protected ConsumersService consumerService;
	
	@Autowired
	protected UploaderService uploaderService;
	
	private Consumer createConsumer() {
		String displayName="Tonino Carino Da Ascoli";
		String username="consumer-" + System.currentTimeMillis() + "@gmail.com";
		String email="uxcesco@gmail.com";
		String password="password";
		
		Consumer user=new Consumer();
		user.setDisplayName(displayName);
		user.setEmail(email);
		user.setUsername(username);
		user.setCodiceFiscale(""+System.currentTimeMillis());
		
		return consumerService.createUser(user, password);
	}
	
	private Uploader createUploader() throws FileNotFoundException, IOException {
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
		
		return uploaderService.createUser(user, password);
	}
	
	@Test
	public void testCreateFile() throws FirebaseAuthException, IOException {
		Consumer consumer = createConsumer();
		Uploader uploader = createUploader();
		
		File file=new File("src/test/resources/images/user.png");
		byte[] content = IOUtils.toByteArray(new FileInputStream(file));
		Set<String> tags=new HashSet<String>();
		tags.add("test");
		tags.add("image");
		
		
		cloudFileService.uploadFile(uploader.getUsername(), consumer, file.getName(), content, tags);
	}

}
