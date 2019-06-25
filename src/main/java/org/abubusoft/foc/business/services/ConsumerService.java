package org.abubusoft.foc.business.services;

import java.util.List;
import java.util.Optional;

import org.abubusoft.foc.repositories.model.Consumer;
import org.abubusoft.foc.repositories.model.UploaderDetailSummary;

public interface ConsumerService extends AbstractUserService<Consumer> {
	
	boolean existsByCodiceFiscale(String codiceFiscale);

	Optional<Consumer> findByCodiceFiscale(String codiceFiscale);

	List<UploaderDetailSummary> reportConsumerForUploader(long consumerId);

}