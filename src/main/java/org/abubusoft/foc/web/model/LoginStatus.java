package org.abubusoft.foc.web.model;

public class LoginStatus {
	
	public enum StatusType {
		TO_COMPLETE,
		READY
	}

	private StatusType status;

	public StatusType getStatus() {
		return status;
	}

	public LoginStatus(String token, StatusType status) {
		super();
		this.token = token;
		this.status=status;
	}

	private String token;
	
	public String getToken() {
		return token;
	}
}
