package org.abubusoft.foc.services.impl;

import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.repositories.ConsumersRepository;
import org.abubusoft.foc.services.ConsumersService;
import org.springframework.stereotype.Service;

@Service
public class ConsumersServiceImpl  extends AbstractUserServiceImpl<ConsumersRepository, Consumer> implements ConsumersService {

	@Override
	public Consumer findByCodiceFiscale(String codiceFiscale) {
		return repository.findByCodiceFiscale(codiceFiscale);
	}

	@Override
	public boolean existsByCodiceFiscale(String codiceFiscale) {
		return repository.existsByCodiceFiscale(codiceFiscale);
	}

}
