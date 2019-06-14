package org.abubusoft.foc.repositories;

import org.abubusoft.foc.model.Consumer;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumersRepository extends UserRepository<Consumer> {

	Consumer findByCodiceFiscale(String codiceFiscale);
	
	boolean existsByCodiceFiscale(String codiceFiscale);

}