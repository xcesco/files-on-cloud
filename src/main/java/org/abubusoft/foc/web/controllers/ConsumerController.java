package org.abubusoft.foc.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.abubusoft.foc.business.facades.ConsumerFacade;
import org.abubusoft.foc.repositories.model.UploaderDetailSummary;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.ChangePasswordWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestAPIV1Controller
//@RestController
public class ConsumerController {

	private ConsumerFacade service;

	@Autowired
	public void setService(ConsumerFacade service) {
		this.service = service;
	}
	

	@GetMapping("/consumers/new")
	public ResponseEntity<ConsumerWto> create() {
		return ResponseEntity.ok(service.create());
	}

	@GetMapping("/consumers")
	public ResponseEntity<List<ConsumerWto>> consumerFindAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/consumers")
	public ResponseEntity<ConsumerWto> consumerInsert(@RequestBody @Valid ConsumerWto value) {
		return ResponseEntity.ok(service.save(value));
	}

	@DeleteMapping("/consumers/{consumerId}")
	public ResponseEntity<Boolean> consumerDelete(@PathVariable("consumerId") long consumerId) {
		return ResponseEntity.ok(service.deleteById(consumerId));
	}

	@PutMapping("/consumers/{consumerId}")
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

	@GetMapping("/consumers/{consumerId}/uploaders-summary")
	public ResponseEntity<List<UploaderDetailSummary>> findUploaderWithFiles(
			@PathVariable("consumerId") long consumerId) {
		return ResponseEntity.ok(service.findUploadersWithFileByConsumerId(consumerId));
	}

	
	
	@GetMapping("/consumers/{consumerId}/change-password")
	public ResponseEntity<ChangePasswordWto> getChangePasswordUrlById(@PathVariable("consumerId") long consumerId) {
		return ResponseEntity.ok(service.getChangePasswordUrlById(consumerId));
	}
	
	@GetMapping("/user/ip")
	public ResponseEntity<String> ip(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");  
        if (StringUtils.isEmpty(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (StringUtils.isEmpty(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (StringUtils.isEmpty(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (StringUtils.isEmpty(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (StringUtils.isEmpty(ip)) {  
            ip = request.getRemoteAddr();  
        }
        
        return ResponseEntity.ok(ip);
	}
	

}
