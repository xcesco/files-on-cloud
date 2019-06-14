package org.abubusoft.foc.web.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.services.AdminService;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestAPIV1Controller
//@RestController
public class AdminController {

	private AdminService service;
	
	@Autowired
	public void setUserService(AdminService adminService) {
		this.service = adminService;
	}

	@GetMapping("/admin")
	public ResponseEntity<Boolean> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return ResponseEntity.ok(Boolean.TRUE);
	}
	
	@GetMapping("/administrators")
	public ResponseEntity<Iterable<Administrator>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@Transactional
	@PostMapping("/administrators")
	public ResponseEntity<Administrator> addNew(@RequestBody @Valid Administrator value) {
		//service.add(value);
		return ResponseEntity.ok(value);
	}

}
