package org.abubusoft.foc.web.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtServiceImpl implements Serializable, JwtService {

	static final String CLAIM_KEY_AUTHORITIES = "roles";

	static final String CLAIM_KEY_CREATED = "iat";
	static final String CLAIM_KEY_IS_ENABLED = "isEnabled";
	static final String CLAIM_KEY_USERNAME = "sub";

	static final String CLAIM_KEY_DISPLAY_NAME = "displayName";
	static final String CLAIM_KEY_ID = "id";
	static final String CLAIM_KEY_EMAIL = "email";

	private static final long serialVersionUID = -3301605591108950415L;

	@Value("${jwt.expiration}")
	private Long expiration;

	@Value("${jwt.secret}")
	private String secret;

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token));
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	@Override
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_CREATED, new Date());
		List<String> auth = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());
		claims.put(CLAIM_KEY_AUTHORITIES, auth);
		claims.put(CLAIM_KEY_IS_ENABLED, userDetails.isEnabled());

		if (userDetails instanceof JwtUser) {
			JwtUser jwtUser=(JwtUser)userDetails;
			claims.put(CLAIM_KEY_DISPLAY_NAME, jwtUser.getDiplayName());
			claims.put(CLAIM_KEY_ID, jwtUser.getId());
			claims.put(CLAIM_KEY_EMAIL, jwtUser.getEmail());
		}

		return generateToken(claims);
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = getClaimsFromToken(token);
			created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}
	
//	(String id, String username, String displayName, String email, boolean enabled,
//			Collection<? extends GrantedAuthority> authorities) {

	@SuppressWarnings("unchecked")
	public JwtUser getUserDetails(String token) {
		if (token == null) {
			return null;
		}
		try {
			final Claims claims = getClaimsFromToken(token);
			List<SimpleGrantedAuthority> authorities = null;
			if (claims.get(CLAIM_KEY_AUTHORITIES) != null) {
				authorities = ((List<String>) claims.get(CLAIM_KEY_AUTHORITIES)).stream()
						.map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
			}

			JwtUser user=new JwtUser(
					(int)claims.get(CLAIM_KEY_ID),
					claims.getSubject(), 
					(String)claims.get(CLAIM_KEY_DISPLAY_NAME), 
					(String)claims.get(CLAIM_KEY_EMAIL), 
					(boolean) claims.get(CLAIM_KEY_IS_ENABLED),
					authorities);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		JwtUser user = (JwtUser) userDetails;
		final String username = getUsernameFromToken(token);
		return (username.equals(user.getUsername()) && !isTokenExpired(token));
	}
}