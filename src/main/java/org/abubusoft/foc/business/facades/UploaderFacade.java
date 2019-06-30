package org.abubusoft.foc.business.facades;

import java.io.InputStream;
import java.util.List;

import org.abubusoft.foc.web.model.UploaderWto;

public interface UploaderFacade extends AbstractUserFacade<UploaderWto>  {
	
	byte[] getLogoById(long id);

	boolean saveLogo(long id, InputStream stream);

	/**
	 * Elenco degli uploader che hanno caricato file per il consumer selezionato
	 * @param consumerId
	 * @return
	 */
	List<UploaderWto> findByConsumer(long consumerId);

	
}
