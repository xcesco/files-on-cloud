package org.abubusoft.foc.business.services;

import java.io.InputStream;
import java.util.List;

import org.abubusoft.foc.model.Uploader;

public interface UploaderService extends AbstractUserService<Uploader> {
	
	List<Uploader> findByConsumer(long consumerId);
	
	public Uploader updateUploaderLogo(String username, InputStream inputStream);
	
}
