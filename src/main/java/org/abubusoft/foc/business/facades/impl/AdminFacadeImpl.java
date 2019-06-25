package org.abubusoft.foc.business.facades.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.abubusoft.foc.business.facades.AdminFacade;
import org.abubusoft.foc.business.services.AdminService;
import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.model.UploaderSummary;
import org.abubusoft.foc.web.model.AdminWto;
import org.springframework.stereotype.Service;

@Service
public class AdminFacadeImpl extends AbstractUserFacadeImpl<AdminWto, AdminService> implements AdminFacade {
		

	@Override
	public AdminWto save(@Valid AdminWto value) {
		Administrator user = mapper.convertAdminToDto(value);
		Administrator result;

		if (user.getId() == null) {
			result = service.insertUser(user, value.getPassword());
		} else {
			result = service.updateById(user);
		}

		return mapper.convertAdminToWto(result);
	}

	@Override
	public List<AdminWto> findAll() {
		return mapper.convertAdminListToWto(service.findAll());
	}



	@Override
	public AdminWto create() {
		AdminWto result = new AdminWto();

		return result;
	}

	@Override
	public AdminWto findById(long id) {
		Optional<Administrator> value = service.findById(id);

		if (value.isPresent()) {
			return mapper.convertAdminToWto(value.get());
		}
		return null;
	}
	
	@Override
	public List<UploaderSummary> reportCloudFileForAllUploaders(LocalDate validoDal, LocalDate validoAl) {
		return service.reportCloudFileForAllUploaders(validoDal, validoAl);
	}

	@Override
	public List<UploaderDetailSummary> reportConsumerForAllUploaders(LocalDate validoDal, LocalDate validoAl) {
		return service.reportConsumerForAllUploaders(validoDal, validoAl);
	}
	


}
