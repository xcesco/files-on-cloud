package org.abubusoft.foc.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.abubusoft.foc.business.facades.UploaderFacade;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.ChangePasswordWto;
import org.abubusoft.foc.web.model.UploaderWto;
import org.abubusoft.foc.web.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
//@Secured({UserRoles.ROLE_ADMINISTRATOR_VALUE, UserRoles.ROLE_UPLOADER_VALUE})
//@PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_UPLOADER')")
@RequestMapping(value = "${api.v1.base-url}/secured/uploaders", produces = "application/json; charset=utf-8")
public class UploaderController {

	private UploaderFacade service;

	@Autowired
	public void setService(UploaderFacade service) {
		this.service = service;
	}

	@PatchMapping(value="/{id}/logo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Boolean> uploaderSaveLogo(@PathVariable("id") long uploaderId,
			@RequestParam("file") MultipartFile file) throws IOException {
		service.saveLogo(uploaderId, file.getInputStream());

		return ResponseEntity.ok(true);
	}

	@PostMapping
	public ResponseEntity<UploaderWto> uploaderSave(@RequestBody @Valid UploaderWto value) {
		return ResponseEntity.ok(service.save(value));
	}

	@GetMapping("/new")
	public ResponseEntity<UploaderWto> uploaderNew() {
		return ResponseEntity.ok(service.create());
	}

	@GetMapping("/{id}/change-password")
	public ResponseEntity<ChangePasswordWto> getChangePasswordUrl(@PathVariable("id") long id) {
		return ResponseEntity.ok(service.getChangePasswordUrlById(id));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UploaderWto> uploaderFindById(@PathVariable("id") long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> uploaderDelete(@PathVariable("id") long uploaderId) {
		return ResponseEntity.ok(service.deleteById(uploaderId));
	}

	/**
	 * Se è un consumer, vede solo quelli che gli hanno caricato dei file.
	 * 
	 * @param user user authenticato
	 * @return lista degli uploader
	 */
	@GetMapping
	public ResponseEntity<List<UploaderWto>> uploaderFindAll(@AuthenticationPrincipal final JwtUser user) {
		if (user.isConsumer()) {			
			return ResponseEntity.ok(service.findByConsumer(user.getId()));
		}
		return ResponseEntity.ok(service.findAll());
	}

	@PutMapping("/{id}")
	public ResponseEntity<UploaderWto> uploaderModify(@PathVariable("id") long uploaderId,
			@RequestBody @Valid UploaderWto value) {
		value.setId(uploaderId);
		return ResponseEntity.ok(service.save(value));
	}

}
