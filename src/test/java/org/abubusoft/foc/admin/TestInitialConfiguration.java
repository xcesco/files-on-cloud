package org.abubusoft.foc.admin;

import javax.transaction.Transactional;

import org.abubusoft.foc.BaseTest;
import org.abubusoft.foc.business.facades.AdminFacade;
import org.abubusoft.foc.web.model.AdminWto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

@Transactional
public class TestInitialConfiguration extends BaseTest {
	
	protected AdminFacade adminFacade;

	@Autowired
	public void setAdminFacade(AdminFacade adminFacade) {
		this.adminFacade = adminFacade;
	}

	@Rollback(false)
	@Test
	@Transactional
	public void testDeleteAllFiles() {
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
