package org.abubusoft.foc.services;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.model.UploaderSummary;

public interface UploaderService extends AbstractUserService<Uploader> {
	
	List<Uploader> findByConsumer(long consumerId);
	
	public Uploader updateUploaderLogo(String username, InputStream inputStream);

	List<UploaderSummary> reportCloudFileForAllUploaders(LocalDate validoDal, LocalDate validoAl);

	List<UploaderDetailSummary> reportConsumerForAllUploaders(LocalDate validoDal, LocalDate validoAl);	
	
}
