package org.abubusoft.foc.services;

import java.io.InputStream;

import org.abubusoft.foc.model.Uploader;

import com.google.firebase.auth.FirebaseAuthException;

public interface UploaderService {
	public void update(String email, String displayName);
	
	public void updatePassword(String email);

	public Uploader create(String email, String username, String password, InputStream inputStream) throws FirebaseAuthException;

}
