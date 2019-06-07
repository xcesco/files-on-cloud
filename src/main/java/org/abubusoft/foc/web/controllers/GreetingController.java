package org.abubusoft.foc.web.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.abubusoft.foc.services.SendMailService;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestAPIV1Controller
//@RestController
public class GreetingController {

	private SendMailService sendMailService;

	@Autowired
	public void setSendMailService(SendMailService value) {
		this.sendMailService=value;
	}
	
	private static final String template = "Hello, %s!";

	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public ResponseEntity<Boolean> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		sendMailService.send();
		return ResponseEntity.ok(Boolean.TRUE);
	}
	
}