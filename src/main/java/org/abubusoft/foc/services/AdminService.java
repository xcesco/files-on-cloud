package org.abubusoft.foc.services;

import org.abubusoft.foc.model.Administrator;

import com.google.firebase.auth.FirebaseAuthException;

public interface AdminService {
	
	public Administrator create(String email, String username, String password) throws FirebaseAuthException;
	
	public void update(String email, String displayName);
	
	public void updatePassword(String email);

}
