package org.abubusoft.foc.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.abubusoft.foc.services.UploaderServiceFacade;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.UploaderWto;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestAPIV1Controller
//@RestController
public class UploaderController {

	private UploaderServiceFacade service;

	@Autowired
	public void setService(UploaderServiceFacade service) {
		this.service = service;
	}

	/*
	 * @GetMapping("/administrators") public ResponseEntity<Boolean>
	 * greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
	 * return ResponseEntity.ok(Boolean.TRUE); }
	 */

	@GetMapping("/uploaders")
	public ResponseEntity<List<UploaderWto>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/uploaders")
	public ResponseEntity<UploaderWto> create(@RequestBody @Valid UploaderWto value) {
		return ResponseEntity.ok(service.createUploader(value));
	}

	@PatchMapping("/uploaders/{id}")
	public ResponseEntity<UploaderWto> modify(@PathVariable("id") long uploaderId, @RequestBody @Valid UploaderWto value) {
		value.setId(uploaderId);
		return ResponseEntity.ok(service.updateById(value));
	}
	
	@GetMapping("/uploaders/{id}/logo")
	public ResponseEntity<ByteArrayResource> getLogo(@PathVariable("id") long uploaderId) {
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

	@DeleteMapping("/uploaders/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") long uploaderId) {
		return ResponseEntity.ok(service.deleteById(uploaderId));
	}
		
}
