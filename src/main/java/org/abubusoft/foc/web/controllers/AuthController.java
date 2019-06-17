package org.abubusoft.foc.web.controllers;

import java.util.HashMap;
import java.util.Map;

import org.abubusoft.foc.model.User;
import org.abubusoft.foc.services.IdentityManagementService;
import org.abubusoft.foc.web.RestAPIV1Controller;
import org.abubusoft.foc.web.model.LoginStatus;
import org.abubusoft.foc.web.model.LoginStatus.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

//@RestController
@RestAPIV1Controller
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
	
	@GetMapping(value="/token")
	public ResponseEntity<LoginStatus> generateToken(@RequestParam(value = "token", required = true) String token) {
		FirebaseToken fireToken;
		try {
			fireToken = FirebaseAuth.getInstance().verifyIdToken(token);
			
			User user = userService.findByUsername(fireToken.getEmail());
			Map<String, Object> claims=new HashMap<>();
			claims.put("username", fireToken.getEmail()); 
			StatusType status = LoginStatus.StatusType.TO_COMPLETE;
			if (user!=null) {
				claims.put("displayName", user.getDisplayName());
				status = LoginStatus.StatusType.READY;
			}			
			
			String accessToken=FirebaseAuth.getInstance().createCustomToken(fireToken.getUid(), claims);
			LoginStatus loginStatus=new LoginStatus(accessToken, status);
			return ResponseEntity.ok(loginStatus);	
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		
		
	}
	
}
