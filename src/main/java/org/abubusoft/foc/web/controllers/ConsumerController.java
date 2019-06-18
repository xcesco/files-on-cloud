package org.abubusoft.foc.web.controllers;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.services.ConsumerServiceFacade;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

	/*
	@PatchMapping("/consumers/{consumerId}")
	public ResponseEntity<ConsumerWto> modify(@PathVariable("consumerId") long consumerId, @RequestBody @Valid ConsumerWto value) {
		value.setId(consumerId);
		return ResponseEntity.ok(service.updateConsumerById(value));
	}*/

	@GetMapping("/consumers/{consumerId}/uploaders-summary")
	public ResponseEntity<List<UploaderDetailSummary>> consumerGetUploaderWithFiles(
			@PathVariable("consumerId") long consumerId) {
		return ResponseEntity.ok(service.findUploadersWithFileByConsumerId(consumerId));
	}

	@GetMapping("/files")
	public ResponseEntity<List<CloudFileWto>> consumerGetFiles(
			@RequestParam(value="consumerId", required = true) long consumerId,
			@RequestParam(value="uploaderId", required = true) long uploaderId, 
			@RequestParam(value="tags", required = true) Set<String> tags) {
		return ResponseEntity.ok(service.findFilesByConsumerAndUploader(consumerId, uploaderId, tags));
	}
	
	@GetMapping("/download/{fileUUID}")
	public ResponseEntity<ByteArrayResource> consumerGetFiles(@PathVariable("fileUUID") String fileUUID) {
		ResponseEntity.ok();
		
		Pair<CloudFile, byte[]> file = service.getFile(fileUUID);
		ByteArrayResource resource = new ByteArrayResource(file.getSecond());
				
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+file.getFirst().getFileName());
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
		
		 return ResponseEntity.ok()
		            .headers(headers)		            
		            .contentLength(file.getFirst().getContentLength())
		            .contentType(MediaType.valueOf(file.getFirst().getMimeType()))
		            .body(resource);
	}

	@PatchMapping("/consumers/{consumerId}/change-password")
	public ResponseEntity<String> modify(@PathVariable("consumerId") long consumerId) {
		return ResponseEntity.ok(service.getChangePasswordUrlByUsername(null));
	}

}
