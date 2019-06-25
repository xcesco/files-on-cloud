package org.abubusoft.foc.business.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.abubusoft.foc.business.services.AdminService;
import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.model.UploaderSummary;
import org.abubusoft.foc.repositories.AdministratorsRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends AbstractUserServiceImpl<AdministratorsRepository, Administrator>
		implements AdminService {

	@Override
	public List<UploaderSummary> reportCloudFileForAllUploaders(LocalDate validoDal, LocalDate validoAl) {
		return repository.reportCloudFileForAllUploaders(validoDal.atStartOfDay(), validoAl.atTime(23, 59, 59));
	}

	@Override
	public List<UploaderDetailSummary> reportConsumerForAllUploaders(LocalDate validoDal, LocalDate validoAl) {
		return repository.reportConsumerForAllUploaders(validoDal.atStartOfDay(), validoAl.atTime(23, 59, 59));
	}

}
