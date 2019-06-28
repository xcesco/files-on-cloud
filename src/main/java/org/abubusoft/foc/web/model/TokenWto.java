package org.abubusoft.foc.web.model;

public class TokenWto {
	private String token;

	public TokenWto(String accessToken) {
		token = accessToken;
	}

	public String getToken() {
		return token;
	}

	public static TokenWto of(String accessToken) {
		return new TokenWto(accessToken);
	}

}
