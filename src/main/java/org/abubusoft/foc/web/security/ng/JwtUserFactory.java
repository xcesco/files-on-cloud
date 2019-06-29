package org.abubusoft.foc.web.security.ng;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.abubusoft.foc.repositories.model.User;
import org.abubusoft.foc.web.security.AuthUserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class JwtUserFactory {

	private JwtUserFactory() {
	}
	
	public static JwtUser create(User user) {	
		String roleName=user.getClass(). getSimpleName().toUpperCase();
		AuthUserRole role = AuthUserRole.valueOf(roleName);
		
		return new JwtUser(user.getId(), user.getUsername(), user.getDisplayName(), user.getEmail(), true, mapToGrantedAuthorities(Arrays.asList(role)));
	}


	private static List<GrantedAuthority> mapToGrantedAuthorities(List<AuthUserRole> authorities) {
		return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.name()))
				.collect(Collectors.toList());
	}
}