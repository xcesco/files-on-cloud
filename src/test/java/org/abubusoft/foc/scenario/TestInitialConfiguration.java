package org.abubusoft.foc.scenario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.abubusoft.foc.BaseTest;
import org.abubusoft.foc.business.facades.AdminFacade;
import org.abubusoft.foc.business.facades.CloudFileFacade;
import org.abubusoft.foc.business.facades.ConsumerFacade;
import org.abubusoft.foc.business.facades.UploaderFacade;
import org.abubusoft.foc.business.services.AuthService;
import org.abubusoft.foc.business.services.CloudFileService;
import org.abubusoft.foc.repositories.support.MimeTypeUtils;
import org.abubusoft.foc.web.model.AdminWto;
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

	protected CloudFileFacade cloudFileFacade;

	@Autowired
	public void setCloudFileFacade(CloudFileFacade cloudFileFacade) {
		this.cloudFileFacade = cloudFileFacade;
	}

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

	/**
	 * Scenario:
	 * Tutti gli account hanno pwd=password
	 *     Viene creato uno user admin0@gmail.com.
	 *     
	 *     Vengono creati n uploader<n>@gmail.com.
	 *     Vengono creati n consumer<n>@gmail.com.
	 *     
	 *     Il consumer 0 e l'uploader 0 non hanno alcun file.
	 *     Il consumer 1 e l'uploader 1 hanno 1 file.
	 *     
	 *     Gli altri utenti vengono creati con alcuni file gia' inseriti.
	 *     
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Rollback(false)
	@Test
	@Transactional
	public void testScenarioIniziale() throws FileNotFoundException, IOException {
		// cancelliamo tutti i file ed utenti
		cloudFileService.deleteAllFiles();
		identityManagementService.deleteAllUsers();

		List<ConsumerWto> consumers = new ArrayList<>();
		List<UploaderWto> uploaders = new ArrayList<>();

		{
			// creazione admin
			AdminWto user = adminFacade.create();
			user.setDisplayName("Mario Admin");
			user.setUsername("admin0@gmail.com");
			user.setEmail("uxcesco@gmail.com");
			user.setPassword("password");
			adminFacade.save(user);
		}

		// creiamo gli uploader ed i consumer
		for (int i = 0; i < 3; i++) {
			uploaders.add(createUploader(i));
		}

		for (int i = 0; i < 3; i++) {
			consumers.add(createConsumer(i));
		}
		
		{
			File image = new File("src/test/resources/images/wallhaven0.jpg");
			cloudFileFacade.save(uploaders.get(1).getId(), LocalDateTime.now(), consumers.get(1),
					"wallpaper0", image.getName(),
					MimeTypeUtils.getFromFileName(image.getName()), image.length(),
					new FileInputStream(image));
		}
		
		
		
        // creiamo gli altri file
		{
			long f = 1;
			long imageCounter = -1;

			for (int u = 2; u < uploaders.size(); u++) {
				for (int c = 2; c < consumers.size(); c++) {
					for (int i = 0; i < f; i++) {
						imageCounter = (imageCounter + 1) % 3;
						File image = new File("src/test/resources/images/wallhaven" + imageCounter + ".jpg");
						cloudFileFacade.save(uploaders.get(u).getId(), LocalDateTime.now(), consumers.get(c),
								"wallpaper, image"+imageCounter+", nice", image.getName(),
								MimeTypeUtils.getFromFileName(image.getName()), image.length(),
								new FileInputStream(image));

					}					
				}
				f++;
			}
		}

	}

	private ConsumerWto createConsumer(int index) {
		// creazione consumer
		ConsumerWto user = consumerFacade.create();
		user.setDisplayName("Mario Consumer " + index);
		user.setUsername("consumer" + index + "@gmail.com");
		user.setEmail("uxcesco@gmail.com");
		user.setPassword("password");
		user.setCodiceFiscale("NSSMRA80A01L424" + index);
		user = consumerFacade.save(user);
		return user;
	}

	private UploaderWto createUploader(int index) throws FileNotFoundException {
		// creazione uploader
		UploaderWto user = uploaderFacade.create();
		user.setDisplayName("Mario Uploader " + index);
		user.setUsername("uploader" + index + "@gmail.com");
		user.setEmail("uxcesco@gmail.com");
		user.setPassword("password");
		user = uploaderFacade.save(user);

		File image = new File("src/test/resources/avatars/avatar"+index+".png");
		uploaderFacade.saveLogo(user.getId(), new FileInputStream(image));

		return user;

	}

	@Autowired
	protected AuthService service;

	@Autowired
	protected CloudFileService cloudFileService;

//	@Rollback(value = false)
//	@Test
//	public void testDeleteAll() throws FirebaseAuthException {
//		service.deleteAllUsers();
//
//	}

//	@Rollback(false)
//	@Test
//	@Transactional
//	public void testDeleteAllFiles() {
//		cloudFileService.deleteAllFiles();
//	}

}
