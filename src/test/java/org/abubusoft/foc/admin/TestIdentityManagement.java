package org.abubusoft.foc.admin;

import javax.transaction.Transactional;

import org.abubusoft.foc.BaseTest;
import org.abubusoft.foc.business.services.CloudFileService;
import org.abubusoft.foc.business.services.IdentityService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.google.firebase.auth.FirebaseAuthException;

@Transactional
public class TestIdentityManagement extends BaseTest {

	@Autowired
	protected IdentityService service;

	@Autowired
	protected CloudFileService cloudFileService;

	@Rollback(value = false)
	@Test
	public void testDeleteAllUsers() throws FirebaseAuthException {
		service.deleteAllUsers();

	}

	@Rollback(false)
	@Test
	@Transactional
	public void testDeleteAllFiles() {
		cloudFileService.deleteAllFiles();
	}

}
