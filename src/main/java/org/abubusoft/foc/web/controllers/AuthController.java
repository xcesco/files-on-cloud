package org.abubusoft.foc.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.abubusoft.foc.business.services.IdentityManagementService;
import org.abubusoft.foc.repositories.model.Administrator;
import org.abubusoft.foc.repositories.model.Uploader;
import org.abubusoft.foc.repositories.model.User;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.TokenWto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@RestAPIV1Controller
@RequestMapping(value = "${api.v1.base-url}/auth", produces = "application/json; charset=utf-8")
public class AuthController {

	private IdentityManagementService userService;

	@Autowired
	public void setUserService(IdentityManagementService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public String generateUser() {
		String pw_hash = BCrypt.hashpw("ciao", BCrypt.gensalt());

		return pw_hash;
	}

	@GetMapping(value = "/token")
	public ResponseEntity<TokenWto> generateToken(@RequestParam(value = "token", required = true) String token) {
		FirebaseToken fireToken;
		try {
			fireToken = FirebaseAuth.getInstance().verifyIdToken(token);

			User user = userService.findByUsername(fireToken.getEmail());
	
			Map<String, Object> claims = new HashMap<>();
			claims.put("username", fireToken.getEmail());
			claims.put("displayName", user.getDisplayName());
			claims.put("id", user.getId());
			claims.put("email", user.getUsername());
			
			if (user instanceof Administrator) {
				claims.put("ROLE", "admin");
			} else if (user instanceof Uploader) {
				claims.put("ROLE", "uploader");
			} else {
				claims.put("ROLE", "consumer");
			}

			String accessToken = FirebaseAuth.getInstance().createCustomToken(fireToken.getUid(), claims);

			return ResponseEntity.ok(TokenWto.of(accessToken));
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

	}

}
