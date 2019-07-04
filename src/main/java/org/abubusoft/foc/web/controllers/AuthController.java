package org.abubusoft.foc.web.controllers;

import org.abubusoft.foc.business.facades.AuthService;
import org.abubusoft.foc.repositories.model.User;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.TokenWto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@RestAPIV1Controller
@RequestMapping(value = "${api.v1.base-url}/public", produces = "application/json; charset=utf-8")
public class AuthController {

	private AuthService authService;

	@Autowired
	public void setAuthService(AuthService userService) {
		this.authService = userService;
	}

	@GetMapping(value = "/token")
	public ResponseEntity<TokenWto> generateToken(@RequestParam(value = "token", required = true) String token) {
		FirebaseToken fireToken;
		try {
			// verifichiamo con firebase il token che ci arriva e ne generiamo uno di
			// sistema
			fireToken = FirebaseAuth.getInstance().verifyIdToken(token);

			String accessToken = authService.generateToken(fireToken.getEmail());
			return ResponseEntity.ok(TokenWto.of(accessToken));
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@GetMapping(value = "/generate-token")
	public ResponseEntity<TokenWto> generateTestToken(
			@RequestParam(value = "username", required = true) String username) {

		try {
			User user = authService.findByUsername(username);
			String token = authService.generateToken(user.getUsername());

			return ResponseEntity.ok(TokenWto.of(token));
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

	}

}
