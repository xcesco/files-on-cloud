package org.abubusoft.foc.web.controllers;

import javax.validation.Valid;

import org.abubusoft.foc.services.ConsumerServiceFacade;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestAPIV1Controller
//@RestController
public class ConsumerController {

	private ConsumerServiceFacade service;

	@Autowired
	public void setService(ConsumerServiceFacade service) {
		this.service = service;
	}

	@PatchMapping("/consumers/{id}")
	public ResponseEntity<ConsumerWto> modify(@PathVariable("id") long id, @RequestBody @Valid ConsumerWto value) {
		value.setId(id);		
		return ResponseEntity.ok(service.updateConsumerById(value));
	}
	
	@PatchMapping("/consumers/{id}/change-password")
	public ResponseEntity<String> modify(@PathVariable("id") long id) {
		
		return ResponseEntity.ok(service.getChangePasswordUrlByUsername(null));
	}
		
		
}
