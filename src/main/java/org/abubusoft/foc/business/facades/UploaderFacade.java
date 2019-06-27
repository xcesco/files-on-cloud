package org.abubusoft.foc.business.facades;

import java.io.InputStream;

import org.abubusoft.foc.web.model.UploaderWto;

public interface UploaderFacade extends AbstractUserFacade<UploaderWto>  {
	
	byte[] getLogoById(long id);

	boolean saveLogo(long id, InputStream stream);

	
}
