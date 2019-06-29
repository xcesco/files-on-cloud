package org.abubusoft.foc.web.security;

public class AuthUser {
	private String displayName;
	private String email;
	private String id;
	private AuthUserRole role;
	private String username;

	public String getDisplayName() {
		return displayName;
	}

	public String getEmail() {
		return email;
	}

	public String getId() {
		return id;
	}

	public AuthUserRole getRole() {
		return role;
	}

	public String getUsername() {
		return username;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRole(AuthUserRole role) {
		this.role = role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
