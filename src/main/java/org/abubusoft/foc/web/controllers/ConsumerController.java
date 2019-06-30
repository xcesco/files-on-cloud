package org.abubusoft.foc.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.abubusoft.foc.business.facades.ConsumerFacade;
import org.abubusoft.foc.repositories.model.UploaderDetailSummary;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.ChangePasswordWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.abubusoft.foc.web.security.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestAPIV1Controller
@Secured({UserRoles.ROLE_ADMINISTRATOR_VALUE, UserRoles.ROLE_UPLOADER_VALUE, UserRoles.ROLE_CONSUMER_VALUE})
@RequestMapping(value="${api.v1.base-url}/secured/consumers", produces = "application/json; charset=utf-8")
public class ConsumerController {

	private ConsumerFacade service;

	@Autowired
	public void setService(ConsumerFacade service) {
		this.service = service;
	}
	

	@GetMapping("/new")
	public ResponseEntity<ConsumerWto> create() {
		return ResponseEntity.ok(service.create());
	}

	@GetMapping
	public ResponseEntity<List<ConsumerWto>> consumerFindAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping
	public ResponseEntity<ConsumerWto> consumerInsert(@RequestBody @Valid ConsumerWto value) {
		return ResponseEntity.ok(service.save(value));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ConsumerWto> findById(@PathVariable("id") long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@DeleteMapping("/{consumerId}")
	public ResponseEntity<Boolean> delete(@PathVariable("consumerId") long consumerId) {
		return ResponseEntity.ok(service.deleteById(consumerId));
	}

	@PutMapping("/{consumerId}")
	public ResponseEntity<ConsumerWto> consumerUpdate(@PathVariable("consumerId") long consumerId,
			@RequestBody @Valid ConsumerWto value) {
		value.setId(consumerId);
		return ResponseEntity.ok(service.save(value));
	}

	/*
	@PatchMapping("/consumers/{consumerId}")
	public ResponseEntity<ConsumerWto> modify(@PathVariable("consumerId") long consumerId, @RequestBody @Valid ConsumerWto value) {
		value.setId(consumerId);
		return ResponseEntity.ok(service.updateConsumerById(value));
	}*/

	@GetMapping("/{consumerId}/uploaders-summary")
	public ResponseEntity<List<UploaderDetailSummary>> findUploaderWithFiles(
			@PathVariable("consumerId") long consumerId) {
		return ResponseEntity.ok(service.findUploadersWithFileByConsumerId(consumerId));
	}

	
	
	@GetMapping("/{id}/change-password")
	public ResponseEntity<ChangePasswordWto> getChangePasswordUrlById(@PathVariable("id") long consumerId) {
		return ResponseEntity.ok(service.getChangePasswordUrlById(consumerId));
	}
		

}
