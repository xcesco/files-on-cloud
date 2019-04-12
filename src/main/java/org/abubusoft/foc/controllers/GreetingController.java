package org.abubusoft.foc.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.abubusoft.foc.controllers.model.Greeting;
import org.abubusoft.foc.web.V1APIController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@V1APIController
@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public ResponseEntity<Greeting> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return ResponseEntity.ok(new Greeting(counter.incrementAndGet(), String.format(template, name)));
	}
	
	@GetMapping("/greeting2")
	public ResponseEntity<Greeting> greeting2(@RequestParam(value = "name", defaultValue = "World") String name) {
		return  ResponseEntity.ok(new Greeting(counter.incrementAndGet(), String.format(template, name)));
	}
}