package org.abubusoft.foc.services;

import org.abubusoft.foc.model.Consumer;

public interface ConsumersService extends AbstractUserService<Consumer> {
	
	boolean existsByCodiceFiscale(String codiceFiscale);

	Consumer findByCodiceFiscale(String codiceFiscale);
}
