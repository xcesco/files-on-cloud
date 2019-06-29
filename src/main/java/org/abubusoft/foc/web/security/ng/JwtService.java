package org.abubusoft.foc.web.security.ng;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

	String generateToken(UserDetails userDetails);

	UserDetails getUserDetails(String authToken);

	boolean validateToken(String authToken, UserDetails userDetails);

}