package org.abubusoft.foc.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.abubusoft.foc.business.services.ConsumerService;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.repositories.ConsumersRepository;
import org.springframework.stereotype.Service;

@Service
public class ConsumersServiceImpl extends AbstractUserServiceImpl<ConsumersRepository, Consumer>
		implements ConsumerService {

	@Override
	public Optional<Consumer> findByCodiceFiscale(String codiceFiscale) {
		return repository.findByCodiceFiscale(codiceFiscale);
	}

	@Override
	public boolean existsByCodiceFiscale(String codiceFiscale) {
		return repository.existsByCodiceFiscale(codiceFiscale);
	}

	@Override
	public List<UploaderDetailSummary> reportConsumerForUploader(long consumerId) {
		return repository.reportUploaderByConsumer(consumerId);
	}

}
