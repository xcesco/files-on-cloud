package org.abubusoft.foc.scenario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.transaction.Transactional;

import org.abubusoft.foc.BaseTest;
import org.abubusoft.foc.business.facades.AdminFacade;
import org.abubusoft.foc.business.facades.ConsumerFacade;
import org.abubusoft.foc.business.facades.UploaderFacade;
import org.abubusoft.foc.web.model.AdminWto;
import org.abubusoft.foc.web.model.CloudFileInfoWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.abubusoft.foc.web.model.UploaderWto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

@Transactional
public class TestInitialConfiguration extends BaseTest {

	protected AdminFacade adminFacade;

	protected ConsumerFacade consumerFacade;

	protected UploaderFacade uploaderFacade;

	@Autowired
	public void setAdminFacade(AdminFacade adminFacade) {
		this.adminFacade = adminFacade;
	}

	@Autowired
	public void setConsumerFacade(ConsumerFacade consumerFacade) {
		this.consumerFacade = consumerFacade;
	}

	@Autowired
	public void setUploaderFacade(UploaderFacade uploaderFacade) {
		this.uploaderFacade = uploaderFacade;
	}

	@Rollback(false)
	@Test
	@Transactional
	public void testScenarioIniziale() throws FileNotFoundException, IOException {
		// cancelliamo tutti i file
		cloudFileService.deleteAllFiles();

		identityManagementService.deleteAllUsers();

		{
			// creazione admin
			AdminWto user = adminFacade.create();
			user.setDisplayName("Root admin");
			user.setUsername("uxcesco@gmail.com");
			user.setEmail("uxcesco@gmail.com");
			user.setPassword("password");
			adminFacade.save(user);
		}

		{
			// creazione uploader
			UploaderWto user = uploaderFacade.create();
			user.setDisplayName("Default uploader");
			user.setUsername("uxcesco-uploader@gmail.com");
			user.setEmail("uxcesco@gmail.com");
			user.setPassword("password");
			user = uploaderFacade.save(user);

			File image = new File("src/test/resources/images/user.png");
			uploaderFacade.saveLogo(user.getId(), new FileInputStream(image));
		}

		{
			// creazione consumer
			ConsumerWto user = consumerFacade.create();
			user.setDisplayName("Mario Rossi");
			user.setUsername("uxcesco-consumer@gmail.com");
			user.setEmail("uxcesco@gmail.com");
			user.setPassword("password");
			user.setCodiceFiscale("RSSMRA80A01L424F");
			user = consumerFacade.save(user);		
		}
		
		{
			// creazione file
			CloudFileInfoWto file=new CloudFileInfoWto();
			//file.set
			
		}

	}

}
