package org.abubusoft.foc.services.impl;

import javax.validation.Valid;

import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.services.ConsumerServiceFacade;
import org.abubusoft.foc.services.ConsumersService;
import org.abubusoft.foc.services.UploaderService;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.abubusoft.foc.web.support.WtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceFacadeImpl implements ConsumerServiceFacade {

	private WtoMapper mapper = WtoMapper.INSTANCE;

	private ConsumersService consumerService;
	
	private UploaderService uploaderService;

	@Autowired
	public void setUploaderService(UploaderService uploaderService) {
		this.uploaderService = uploaderService;
	}

	@Autowired
	public void setConsumerService(ConsumersService consumerService) {
		this.consumerService = consumerService;
	}
	
	@Override
	public ConsumerWto updateConsumerById(@Valid ConsumerWto value) {
		Consumer user = mapper.convertConsumerToDto(value);
		Consumer result = consumerService.updateById(user);

		return mapper.convertConsumerToWto(result);
	}

	@Override
	public String generateChangePasswordUrl(String username) {
		return consumerService.generateChangePasswordUrl(username);
	}
		
}
