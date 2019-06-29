package org.abubusoft.foc.web.security;

public enum UserRoles {
	ROLE_ADMINISTRATOR, ROLE_UPLOADER, ROLE_CONSUMER;
	
	public static final String PREFIX = "ROLE_";
	
	public static final String ROLE_ADMINISTRATOR_VALUE="ROLE_ADMINISTRATOR";
	public static final String ROLE_UPLOADER_VALUE="ROLE_UPLOADER";
	public static final String ROLE_CONSUMER_VALUE="ROLE_CONSUMER";
}