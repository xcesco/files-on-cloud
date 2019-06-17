package org.abubusoft.foc.services;

import java.util.List;

import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.UploaderDetailSummary;

public interface ConsumerService extends AbstractUserService<Consumer> {
	
	boolean existsByCodiceFiscale(String codiceFiscale);

	Consumer findByCodiceFiscale(String codiceFiscale);

	List<UploaderDetailSummary> reportConsumerForUploader(long consumerId);	
}
