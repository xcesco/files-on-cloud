package org.abubusoft.foc.web.controllers;

import org.abubusoft.foc.services.AdminService;
import org.abubusoft.foc.web.V1APIController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@V1APIController
@RestController
public class AdminController {

	private AdminService adminService;
	
	@Autowired
	public void setUserService(AdminService adminService) {
		this.adminService = adminService;
	}
}
