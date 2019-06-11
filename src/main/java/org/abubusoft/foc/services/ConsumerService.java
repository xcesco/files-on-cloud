package org.abubusoft.foc.services;

import org.abubusoft.foc.model.Consumer;

import com.google.firebase.auth.FirebaseAuthException;

public interface ConsumerService {
	public void update(String email, String displayName);
	
	public void updatePassword(String email);

	public Consumer create(String email, String username, String password, String codiceFiscale) throws FirebaseAuthException;

}
