package org.abubusoft.foc.web.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.model.UploaderSummary;
import org.abubusoft.foc.services.AdminServiceFacade;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.AdminWto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
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
public class AdminController {

	private AdminServiceFacade service;

	@Autowired
	public void setAdminServiceFacade(AdminServiceFacade adminService) {
		this.service = adminService;
	}

	/*
	 * @GetMapping("/administrators") public ResponseEntity<Boolean>
	 * greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
	 * return ResponseEntity.ok(Boolean.TRUE); }
	 */

	@GetMapping("/administrators")
	public ResponseEntity<List<AdminWto>> findAll() {
		return ResponseEntity.ok(service.findAllAdmin());
	}

	@PostMapping("/administrators")
	public ResponseEntity<AdminWto> create(@RequestBody @Valid AdminWto value) {
		return ResponseEntity.ok(service.createAdmin(value));
	}

	@PatchMapping("/administrators/{id}")
	public ResponseEntity<AdminWto> modify(@PathVariable("id") long id, @RequestBody @Valid AdminWto value) {
		value.setId(id);
		return ResponseEntity.ok(service.updateAdmin(value));
	}

	@DeleteMapping("/administrators/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
		return ResponseEntity.ok(service.deleteAdminById(id));
	}

	@GetMapping("/summary")
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

	@GetMapping("/summary-detail")
	public ResponseEntity<List<UploaderDetailSummary>> greportConsumerForAllUploaderset(
			@DateTimeFormat(iso = ISO.DATE) @RequestParam(name = "dataDal", required = false) LocalDate dataDal,
			@DateTimeFormat(iso = ISO.DATE) @RequestParam(name = "dataAl", required = false) LocalDate dataAl) {
		LocalDate now = LocalDate.now();
		if (dataDal == null || dataAl == null) {
			dataDal = now.withDayOfMonth(1);
			dataAl = now.withDayOfMonth(now.lengthOfMonth());
		}
		
		return ResponseEntity.ok(service.reportConsumerForAllUploaders(dataDal, dataAl));

	}

}
