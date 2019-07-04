package org.abubusoft.foc.exceptions;

public class AppRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -7086843476630605765L;

	public static AppRuntimeException create(Throwable e) {
		return new AppRuntimeException(e);
	}
	
	public AppRuntimeException() {		
	}
		
	public AppRuntimeException(Throwable e) {
		super(e);
	}

	public static AppRuntimeException create(Class<? extends AppRuntimeException> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			
			return new AppRuntimeException(e);
		}		
	}

}
