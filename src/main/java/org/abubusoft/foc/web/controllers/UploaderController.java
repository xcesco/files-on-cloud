package org.abubusoft.foc.web.controllers;

import java.io.IOException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@RestAPIV1Controller
//@RestController
public class UploaderController {

	private UploaderServiceFacade service;

	@GetMapping("/consumers/new")
	public ResponseEntity<ConsumerWto> consumerCreate() {
		return ResponseEntity.ok(service.createConsumer());
	}

	@GetMapping("/consumers")
	public ResponseEntity<List<ConsumerWto>> consumerFindAll() {
		return ResponseEntity.ok(service.findAllConsumers());
	}

	@PostMapping("/consumers")
	public ResponseEntity<ConsumerWto> consumerInsert(@RequestBody @Valid ConsumerWto value) {
		return ResponseEntity.ok(service.saveConsumer(value));
	}

	@DeleteMapping("/consumers/{consumerId}")
	public ResponseEntity<Boolean> consumerDelete(@PathVariable("consumerId") long consumerId) {
		return ResponseEntity.ok(service.deleteConsumerById(consumerId));
	}

	@PutMapping("/consumers/{consumerId}")
	public ResponseEntity<ConsumerWto> consumerUpdate(@PathVariable("consumerId") long consumerId,
			@RequestBody @Valid ConsumerWto value) {
		value.setId(consumerId);
		return ResponseEntity.ok(service.saveConsumer(value));
	}

	// gia presente in consumer
//	@GetMapping("/consumers/{consumerId}/change-password")
//	public ResponseEntity<String> consumerGetChangePasswordUrl(@PathVariable("consumerId") long consumerId) {
//		return ResponseEntity.ok(service.uploaderGetChangePasswordUrlByUsername(null));
//	}			

	@PostMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files")
	public ResponseEntity<Long> fileCreate(@PathVariable("uploaderId") long uploaderId,
			@PathVariable("consumerId") long consumerId, @RequestBody CloudFileWto cloudFile) {
		return ResponseEntity.ok(service.createCloudFile(uploaderId, consumerId, cloudFile));
	}
	
	@PostMapping(value = "/uploaders/{uploaderId}/files")
	@ResponseStatus(HttpStatus.OK)
	public void handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		service.storeFile(file.getInputStream());
	}

	@GetMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files/{fileId}/tags")
	public ResponseEntity<List<CloudFileTag>> findTagsByUploaderAndConsumer(
			@PathVariable(value = "uploaderId") long uploaderId, @PathVariable(value = "consumerId") long consumerId) {
		return ResponseEntity.ok(service.findTagsByUploaderAndConsumer(uploaderId, consumerId));
	}

	@GetMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files")
	public ResponseEntity<List<CloudFileWto>> findFilesByUploaderAndConsumer(
			@PathVariable(value = "uploaderId") long uploaderId, @PathVariable(value = "consumerId") long consumerId) {
		return ResponseEntity.ok(service.findCloudFilesByUploaderAndConsumer(uploaderId, consumerId));
	}

	@GetMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files/create")
	public ResponseEntity<CloudFileWto> fileCreate(@PathVariable(value = "uploaderId") long uploaderId,
			@PathVariable(value = "consumerId") long consumerId) {
		return ResponseEntity.ok(service.createCloudFile(uploaderId, consumerId));
	}

	@GetMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files/{fileId}")
	public ResponseEntity<CloudFileWto> fileGetById(@PathVariable(value = "uploaderId") long uploaderId,
			@PathVariable(value = "consumerId") long consumerId, @PathVariable("fileId") long fileId) {
		return ResponseEntity.ok(service.findCloudFilesByUploaderAndConsumerAndFile(uploaderId, consumerId, fileId));
	}

	@DeleteMapping("/uploaders/{uploaderId}/consumers/{consumerId}/files/{fileId}")
	public ResponseEntity<Boolean> fileDelete(@PathVariable("uploaderId") long uploaderId,
			@PathVariable("consumerId") long consumerId, @PathVariable("fileId") long fileId) {
		return ResponseEntity.ok(service.deleteCloudFileByUploaderAndConsumerAndFile(uploaderId, consumerId, fileId));
	}

	@GetMapping("/uploaders/{id}/logo")
	public ResponseEntity<ByteArrayResource> getUploaderLogo(@PathVariable("id") long uploaderId) {
		byte[] content = service.getLogoById(uploaderId);
		ByteArrayResource resource = new ByteArrayResource(content);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=logo" + uploaderId + ".png");
		headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
		headers.add(HttpHeaders.PRAGMA, "no-cache");
		headers.add(HttpHeaders.EXPIRES, "0");

		return ResponseEntity.ok().headers(headers).contentLength(content.length).contentType(MediaType.IMAGE_PNG)
				.body(resource);
	}

	@PostMapping("/cloud/uploader/{uploaderId}")
	public ResponseEntity<Boolean> fileCreate(@PathVariable("uploaderId") long uploaderId,
			@RequestBody ConsumerAndCloudFileWto consumerCloudFile) {
		return ResponseEntity.ok(service.createCloudFile(uploaderId, consumerCloudFile));
	}

	@Autowired
	public void setService(UploaderServiceFacade service) {
		this.service = service;
	}

}
