package org.abubusoft.foc.web.model;

public class ChangePasswordWto {
	protected String url;

	public ChangePasswordWto(String value) {
		url=value;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
