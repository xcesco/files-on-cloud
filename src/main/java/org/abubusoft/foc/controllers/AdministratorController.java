package org.abubusoft.foc.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.services.AdministratorService;
import org.abubusoft.foc.web.V1APIController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

@V1APIController
@RestController
public class AdministratorController {

	@GetMapping("/users")
	public String generateUser() {
		String pw_hash = BCrypt.hashpw("ciao", BCrypt.gensalt());
		
		return pw_hash;			
	}
	
	@GetMapping("/administrators")
	public List<Administrator> findAll() {
		return service.findAll();
	}
	
	@PostMapping("/administrators")
	public void addNew(@RequestBody Administrator value) {
		service.add(value);
	}
	
	@Autowired
	private AdministratorService service;

	public void setService(AdministratorService service) {
		this.service = service;
	} 
	
}
