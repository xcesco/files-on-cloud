package org.abubusoft.foc.services;

import org.abubusoft.foc.web.model.ConsumerWto;

public interface ConsumerServiceFacade  {
	
	String getChangePasswordUrlByUsername(String username);

	ConsumerWto updateConsumerById(ConsumerWto user);
	
}
