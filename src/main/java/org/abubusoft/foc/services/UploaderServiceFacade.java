package org.abubusoft.foc.services;

import java.util.List;

import org.abubusoft.foc.web.model.UploaderWto;

public interface UploaderServiceFacade  {
	
	boolean deleteById(long id);

	String generateChangePasswordUrl(String username);

	List<UploaderWto> findAll();
	
	UploaderWto updateById(UploaderWto user);
	
	byte[] getLogoById(long id);

	UploaderWto findById(long uploaderId);

	UploaderWto createUploader(UploaderWto user);
	

}
