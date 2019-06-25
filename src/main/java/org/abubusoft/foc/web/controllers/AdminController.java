package org.abubusoft.foc.web.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;

import org.abubusoft.foc.business.facades.AdminFacade;
import org.abubusoft.foc.repositories.model.UploaderDetailSummary;
import org.abubusoft.foc.repositories.model.UploaderSummary;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.AdminWto;
import org.abubusoft.foc.web.model.ChangePasswordWto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestAPIV1Controller
//@RestController
public class AdminController {

	private AdminFacade service;
	
	@GetMapping("/administrators")
	public ResponseEntity<List<AdminWto>> adminFindAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/administrators/new")
	public ResponseEntity<AdminWto> create() {		
		return ResponseEntity.ok(service.create());
	}
	
	@DeleteMapping("/administrators/{id}")
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") long id) {
		return ResponseEntity.ok(service.deleteById(id));
	}

	@GetMapping("/administrators/{id}")
	public ResponseEntity<AdminWto> findById(@PathVariable("id") long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping("/administrators/{id}/change-password")
	public ResponseEntity<ChangePasswordWto> getChangePasswordUrl(@PathVariable("id") long id) {		
		return ResponseEntity.ok(service.getChangePasswordUrlById(id));
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
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	@PostMapping("/administrators")
	public ResponseEntity<AdminWto> save(@RequestBody @Valid AdminWto value) {
		return ResponseEntity.ok(service.save(value));
	}

	@Autowired
	public void setAdminServiceFacade(AdminFacade adminService) {
		this.service = adminService;
	}

	@PutMapping("/administrators/{id}")
	public ResponseEntity<AdminWto> update(@PathVariable("id") long id, @RequestBody @Valid AdminWto value) {
		value.setId(id);
		return ResponseEntity.ok(service.save(value));
	}
			

}
