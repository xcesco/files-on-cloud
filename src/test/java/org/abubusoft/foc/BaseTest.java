package org.abubusoft.foc;

import java.io.IOException;

import org.abubusoft.foc.business.services.AdminService;
import org.abubusoft.foc.business.services.CloudFileService;
import org.abubusoft.foc.business.services.ConsumerService;
import org.abubusoft.foc.business.services.IdentityManagementService;
import org.abubusoft.foc.business.services.UploaderService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServletInitializer.class)
public abstract class BaseTest {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected IdentityManagementService identityManagementService;
	
	@Autowired
	public void setIdentityManagementService(IdentityManagementService identityManagementService) {
		this.identityManagementService = identityManagementService;
	}

	protected AdminService adminService;
	
	protected UploaderService uploaderService;
	
	protected ConsumerService consumerService;
	
	protected CloudFileService cloudFileService;

	@Autowired
	public void setCloudFileService(CloudFileService cloudFileService) {
		this.cloudFileService = cloudFileService;
	}

	@Autowired
	public void setConsumerService(ConsumerService consumerService) {
		this.consumerService = consumerService;
	}

	@Autowired
	public void setUploaderService(UploaderService uploaderService) {
		this.uploaderService = uploaderService;
	}

	@Autowired
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	@Before
	public void setup() throws IOException {
		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseOptions options;

			options = new FirebaseOptions.Builder()
					// prende le credenziali da GOOGLE_APPLICATION_CREDENTIALS
					.setCredentials(GoogleCredentials.getApplicationDefault())
					// .setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://programmazione-web-238419.firebaseio.com").build();

			FirebaseApp.initializeApp(options);
		}

	}

}
