package org.abubusoft.foc.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

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

	@Transactional
	@PostMapping("/administrators")
	public Administrator addNew(@RequestBody @Valid Administrator value) {
		service.add(value);
		return value;
	}
	
	@Autowired
	private AdministratorService service;

	public void setService(AdministratorService service) {
		this.service = service;
	} 
	
}
