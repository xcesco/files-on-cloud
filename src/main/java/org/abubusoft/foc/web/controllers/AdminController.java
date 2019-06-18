package org.abubusoft.foc.web.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.model.UploaderSummary;
import org.abubusoft.foc.services.AdminServiceFacade;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.AdminWto;
import org.abubusoft.foc.web.model.UploaderWto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestAPIV1Controller
//@RestController
public class AdminController {

	private AdminServiceFacade service;
	
	@GetMapping("/administrators")
	public ResponseEntity<List<AdminWto>> adminFindAll() {
		return ResponseEntity.ok(service.findAllAdmin());
	}

	@PostMapping("/administrators")
	public ResponseEntity<AdminWto> adminCreate(@RequestBody @Valid AdminWto value) {
		return ResponseEntity.ok(service.saveAdmin(value));
	}
	
	@GetMapping("/administrators/new")
	public ResponseEntity<AdminWto> adminNew() {		
		return ResponseEntity.ok(service.createAdministrator());
	}

	@GetMapping("/administrators/{id}")
	public ResponseEntity<AdminWto> adminGet(@PathVariable("id") long id) {
		return ResponseEntity.ok(service.findAdminById(id));
	}

	@DeleteMapping("/administrators/{id}")
	public ResponseEntity<Boolean> adminDelete(@PathVariable("id") long id) {
		return ResponseEntity.ok(service.deleteAdminById(id));
	}
	
	@PutMapping("/administrators/{id}")
	public ResponseEntity<AdminWto> adminModify(@PathVariable("id") long id, @RequestBody @Valid AdminWto value) {
		value.setId(id);
		return ResponseEntity.ok(service.saveAdmin(value));
	}

	@GetMapping("/administrators/{id}/change-password")
	public ResponseEntity<String> adminGetChangePasswordUrl(@PathVariable("id") long id) {
		
		return ResponseEntity.ok(service.getChangePasswordUrlByUsername(null));
	}	

	@GetMapping("/administrators/summary")
	public ResponseEntity<List<UploaderSummary>> reportCloudFileForAllUploaders(
			@DateTimeFormat(iso = ISO.DATE) @RequestParam(name = "dataDal", required = false) LocalDate dataDal,
			@DateTimeFormat(iso = ISO.DATE) @RequestParam(name = "dataAl", required = false) LocalDate dataAl) {
		LocalDate now = LocalDate.now();
		if (dataDal == null || dataAl == null) {
			dataDal = now.withDayOfMonth(1);
			dataAl = now.withDayOfMonth(now.lengthOfMonth());
		}

		return ResponseEntity.ok(service.reportCloudFileForAllUploaders(dataDal, dataAl));
	}

	@GetMapping("/administrators/detailed-summary")
	public ResponseEntity<List<UploaderDetailSummary>> reportConsumerForAllUploaderset(
			@DateTimeFormat(iso = ISO.DATE) @RequestParam(name = "dataDal", required = false) LocalDate dataDal,
			@DateTimeFormat(iso = ISO.DATE) @RequestParam(name = "dataAl", required = false) LocalDate dataAl) {
		LocalDate now = LocalDate.now();
		if (dataDal == null || dataAl == null) {
			dataDal = now.withDayOfMonth(1);
			dataAl = now.withDayOfMonth(now.lengthOfMonth());
		}
		
		return ResponseEntity.ok(service.reportConsumerForAllUploaders(dataDal, dataAl));

	}

	@Autowired
	public void setAdminServiceFacade(AdminServiceFacade adminService) {
		this.service = adminService;
	}
		
	@PostMapping("/uploaders")
	public ResponseEntity<UploaderWto> uploaderCreate(@RequestBody @Valid UploaderWto value) {
		return ResponseEntity.ok(service.updateUploader(value));
	}
	
	@GetMapping("/uploaders/new")
	public ResponseEntity<UploaderWto> uploaderCreate() {		
		return ResponseEntity.ok(service.createUploader());
	}
	

	@DeleteMapping("/uploaders/{id}")
	public ResponseEntity<Boolean> uploaderDelete(@PathVariable("id") long uploaderId) {
		return ResponseEntity.ok(service.deleteUploaderById(uploaderId));
	}
	
	@GetMapping("/uploaders")
	public ResponseEntity<List<UploaderWto>> uploaderFindAll() {
		return ResponseEntity.ok(service.findAllUploaders());
	}

	@PutMapping("/uploaders/{id}")
	public ResponseEntity<UploaderWto> uploaderModify(@PathVariable("id") long uploaderId, @RequestBody @Valid UploaderWto value) {
		value.setId(uploaderId);
		return ResponseEntity.ok(service.updateUploader(value));
	}

}
