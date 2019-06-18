package org.abubusoft.foc.web.controllers;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.services.ConsumerServiceFacade;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping("/consumers/{id}/uploaders-summary")
	public ResponseEntity<List<UploaderDetailSummary>> consumerGetUploaderWithFiles(
			@PathVariable("id") long consumerId) {
		return ResponseEntity.ok(service.findUploadersWithFileByConsumerId(consumerId));
	}

	@GetMapping("/consumers/{id}/uploaders/{uploaderId}/files")
	public ResponseEntity<List<CloudFileWto>> consumerGetFiles(@PathVariable("id") long id,
			@PathVariable("uploaderId") long uploaderId, @RequestParam Set<String> tags) {
		return ResponseEntity.ok(service.findFilesByConsumerAndUploader(id, uploaderId, tags));
	}

	@PatchMapping("/consumers/{id}/change-password")
	public ResponseEntity<String> modify(@PathVariable("id") long id) {
		return ResponseEntity.ok(service.getChangePasswordUrlByUsername(null));
	}

}
