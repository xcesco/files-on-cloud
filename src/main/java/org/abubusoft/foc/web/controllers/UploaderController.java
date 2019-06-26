package org.abubusoft.foc.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.abubusoft.foc.business.facades.UploaderFacade;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestAPIV1Controller
@RequestMapping(value="${api.v1.base-url}/uploaders", produces = "application/json; charset=utf-8")
public class UploaderController {

	private UploaderFacade service;


	// gia presente in consumer
//	@GetMapping("/consumers/{consumerId}/change-password")
//	public ResponseEntity<String> consumerGetChangePasswordUrl(@PathVariable("consumerId") long consumerId) {
//		return ResponseEntity.ok(service.uploaderGetChangePasswordUrlByUsername(null));
//	}			


	@GetMapping("/{id}/logo")
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
	
	@PatchMapping("/{id}/logo")
    public ResponseEntity<Boolean> handleFileUpload(@PathVariable("id") long uploaderId, @RequestParam("file") MultipartFile file) throws IOException {
		service.saveLogo(uploaderId, file.getBytes());
        
        return ResponseEntity.ok(true);
    }

	@Autowired
	public void setService(UploaderFacade service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<UploaderWto> save(@RequestBody @Valid UploaderWto value) {
		return ResponseEntity.ok(service.save(value));
	}
	
	@GetMapping("/new")
	public ResponseEntity<UploaderWto> uploaderNew() {		
		return ResponseEntity.ok(service.create());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UploaderWto> findById(@PathVariable("id") long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> uploaderDelete(@PathVariable("id") long uploaderId) {
		return ResponseEntity.ok(service.deleteById(uploaderId));
	}
	
	@GetMapping
	public ResponseEntity<List<UploaderWto>> uploaderFindAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PutMapping("/{id}")
	public ResponseEntity<UploaderWto> uploaderModify(@PathVariable("id") long uploaderId, @RequestBody @Valid UploaderWto value) {
		value.setId(uploaderId);
		return ResponseEntity.ok(service.save(value));
	}

}
