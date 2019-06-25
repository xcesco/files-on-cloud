package org.abubusoft.foc.config;

import org.abubusoft.foc.business.facades.AdminFacade;
import org.abubusoft.foc.business.services.CloudFileService;
import org.abubusoft.foc.business.services.IdentityManagementService;
import org.abubusoft.foc.web.model.AdminWto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Inizializza il db e firebase
 * 
 * @author xcesco
 */
@Component
public class ApplicationContextStartedListener {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private CloudFileService cloudFileService;

	@Autowired
	public void setCloudFileService(CloudFileService cloudFileService) {
		this.cloudFileService = cloudFileService;
	}

	@Autowired
	public void setIdentityManagementService(IdentityManagementService identityManagementService) {
		this.identityManagementService = identityManagementService;
	}

	@Autowired
	public void setAdminFacade(AdminFacade adminFacade) {
		this.adminFacade = adminFacade;
	}

	private IdentityManagementService identityManagementService;
	private AdminFacade adminFacade;

	// @Async
	@EventListener
	public void handleContextStart(ContextStartedEvent cse) {
		logger.info("Handling context started event.");

		// cancelliamo tutti i file
		cloudFileService.deleteAllFiles();

		identityManagementService.deleteAllUsers();

		AdminWto user = adminFacade.create();
		user.setDisplayName("Root admin");
		user.setUsername("uxcesco@gmail.com");
		user.setEmail("uxcesco@gmail.com");
		user.setPassword("password");

		adminFacade.save(user);
	}
}