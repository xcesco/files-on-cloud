package org.abubusoft.foc.web.model;

public abstract class AbstractUserWto {
	protected String displayName;

	protected String email;

	protected long id;

	protected String password;
	
	protected String username;
	
	public String getDisplayName() {
		return displayName;
	}

	public String getEmail() {
		return email;
	}

	public long getId() {
		return id;
	}

	public String getPassword() {
		return password;
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

	public void setId(long id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
