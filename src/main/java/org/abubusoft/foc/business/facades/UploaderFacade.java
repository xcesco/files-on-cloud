package org.abubusoft.foc.business.facades;

import org.abubusoft.foc.web.model.UploaderWto;

public interface UploaderFacade extends AbstractUserFacade<UploaderWto>  {
	
	byte[] getLogoById(long id);

	
}
