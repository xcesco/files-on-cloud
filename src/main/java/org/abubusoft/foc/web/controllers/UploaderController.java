package org.abubusoft.foc.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.abubusoft.foc.model.CloudFileTag;
import org.abubusoft.foc.services.UploaderServiceFacade;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerAndCloudFileWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestAPIV1Controller
//@RestController
public class UploaderController {

	private UploaderServiceFacade service;
	
	@GetMapping("/consumers/create")
	public ResponseEntity<ConsumerWto> consumerCreate() {		
		return ResponseEntity.ok(service.createConsumer());
	}
	
	@GetMapping("/consumers")
	public ResponseEntity<List<ConsumerWto>> consumerFindAll() {
		return ResponseEntity.ok(service.findAllConsumers());
	}

	@PostMapping("/consumers")
	public ResponseEntity<ConsumerWto> consumerCreate(@RequestBody @Valid ConsumerWto value) {
		return ResponseEntity.ok(service.saveConsumer(value));
	}	
	
	@DeleteMapping("/consumers/{consumerId}")
	public ResponseEntity<Boolean> consumerDelete(@PathVariable("consumerId") long consumerId) {		
		return ResponseEntity.ok(service.deleteConsumerById(consumerId));
	}
	
	@GetMapping("/consumers/{consumerId}/change-password")
	public ResponseEntity<String> consumerGetChangePasswordUrl(@PathVariable("consumerId") long consumerId) {
		return ResponseEntity.ok(service.uploaderGetChangePasswordUrlByUsername(null));
	}	
	
	@PatchMapping("/consumers/{consumerId}")
	public ResponseEntity<ConsumerWto> consumerModify(@PathVariable("consumerId") long consumerId, @RequestBody @Valid ConsumerWto value) {
		value.setId(consumerId);
		return ResponseEntity.ok(service.saveConsumer(value));
	}

	@DeleteMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files/{fileId}")
	public ResponseEntity<Boolean> fileDelete(@PathVariable("uploaderId") long uploaderId, @PathVariable("consumerId") long consumerId, @PathVariable("fileId") long fileId) {
		return ResponseEntity.ok(service.deleteCloudFileByUploaderAndConsumerAndFile(uploaderId, consumerId, fileId));
	}
	
	@PostMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files")
	public ResponseEntity<Long> fileCreate(@PathVariable("uploaderId") long uploaderId, @PathVariable("consumerId") long consumerId, @RequestBody CloudFileWto cloudFile) {
		return ResponseEntity.ok(service.createCloudFile(uploaderId, consumerId, cloudFile));
	}
	
	@PostMapping("/uploaders/{uploaderId}/consumer-file")
	public ResponseEntity<Boolean> fileCreate(@PathVariable("uploaderId") long uploaderId, @RequestBody ConsumerAndCloudFileWto consumerCloudFile) {
		return ResponseEntity.ok(service.createCloudFile(uploaderId, consumerCloudFile));
	}
	
	@GetMapping("/tags")
	public ResponseEntity<List<CloudFileTag>> findFileTagsByUploaderAndConsumer(@RequestParam(value="uploaderId", required=true) long uploaderId, @RequestParam(value="consumerId", required=true) long consumerId) {
		return ResponseEntity.ok(service.findTagsByUploaderAndConsumer(uploaderId, consumerId));
	}
	
	@GetMapping("/files")
	public ResponseEntity<List<CloudFileWto>> tagsFindByUploaderAndConsumer(@RequestParam(value="uploaderId", required=true) long uploaderId, @RequestParam(value="consumerId", required=true) long consumerId) {
		return ResponseEntity.ok(service.findCloudFilesByUploaderAndConsumer(uploaderId, consumerId));
	}
	
	@GetMapping("/files/new")
	public ResponseEntity<CloudFileWto> fileCreate(
			@RequestParam(value="uploaderId", required=true) long uploaderId, 
			@RequestParam(value="consumerId", required=true) long consumerId) {
		return ResponseEntity.ok(service.newCloudFile(uploaderId, consumerId));
	}
	
	@GetMapping("/files/{fileId}")
	public ResponseEntity<CloudFileWto> fileGetById(@RequestParam(value="uploaderId", required=true) long uploaderId, @RequestParam(value="consumerId", required=true) long consumerId, @PathVariable("fileId") long fileId) {
		return ResponseEntity.ok(service.findCloudFilesByUploaderAndConsumerAndFile(uploaderId, consumerId, fileId));
	}
	
	@GetMapping("/uploaders/{id}/logo")
	public ResponseEntity<ByteArrayResource> getUploaderLogo(@PathVariable("id") long uploaderId) {
		byte[] content=service.getLogoById(uploaderId);
		ByteArrayResource resource = new ByteArrayResource(content);
				
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=logo"+uploaderId+".png");
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
		
		 return ResponseEntity.ok()
		            .headers(headers)		            
		            .contentLength(content.length)
		            .contentType(MediaType.IMAGE_PNG)
		            .body(resource);
	}
		
	@Autowired
	public void setService(UploaderServiceFacade service) {
		this.service = service;
	}
		
}
