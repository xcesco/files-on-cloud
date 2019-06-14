package org.abubusoft.foc.exception;

import com.google.firebase.auth.FirebaseAuthException;

public class AppRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -7086843476630605765L;

	public static AppRuntimeException create(FirebaseAuthException e) {
		return new AppRuntimeException(e);
	}
		
	public AppRuntimeException(Exception e) {
		super(e);
	}

}
