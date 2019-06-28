package org.abubusoft.foc.web.security;

public class AuthUser {
	private String id;
	private String username;
	private String displayName;
	private String email;
	private AuthUserRole type;

	public void setId(String id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AuthUserRole getType() {
		return type;
	}

	public void setType(AuthUserRole type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getEmail() {
		return email;
	}

	public AuthUserRole getRole() {
		return type;
	}

}
