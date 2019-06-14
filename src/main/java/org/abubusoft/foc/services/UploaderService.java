package org.abubusoft.foc.services;

import java.io.InputStream;

import org.abubusoft.foc.model.Uploader;

public interface UploaderService extends AbstractUserService<Uploader> {
	
	public Uploader updateUploaderLogo(String username, InputStream inputStream);

	
}
