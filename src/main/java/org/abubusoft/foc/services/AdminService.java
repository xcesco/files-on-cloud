package org.abubusoft.foc.services;

public interface AdminService {
	
	public void create(String email, String username, String password);
	
	public void updateAdmin(String email, String displayName);
	
	public void updatePassword(String email);

}
