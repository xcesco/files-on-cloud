package org.abubusoft.foc.business.facades.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.abubusoft.foc.business.facades.ConsumerFacade;
import org.abubusoft.foc.business.services.ConsumerService;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceFacadeImpl extends AbstractUserFacadeImpl<ConsumerWto, ConsumerService>
		implements ConsumerFacade {

	@Override
	public List<ConsumerWto> findAll() {
		return mapper.convertConsumerListToWto(service.findAll());
	}

	@Override
	public List<UploaderDetailSummary> findUploadersWithFileByConsumerId(long consumerId) {
		List<UploaderDetailSummary> result = service.reportConsumerForUploader(consumerId);

		return result;
	}

	@Override
	public ConsumerWto create() {
		ConsumerWto result = new ConsumerWto();

		return result;
	}

	@Override
	public ConsumerWto save(@Valid ConsumerWto value) {
		Consumer user = mapper.convertConsumerToDto(value);

		Consumer result = null;
		if (value.getId() == null) {
			result = service.insertUser(user, value.getPassword());
		} else {
			result = service.updateById(user);
		}

		return mapper.convertConsumerToWto(result);
	}
	
	@Override
	public ConsumerWto findById(long id) {
		Optional<Consumer> value = service.findById(id);

		if (value.isPresent()) {
			return mapper.convertConsumerToWto(value.get());
		}
		return null;
	}

}
