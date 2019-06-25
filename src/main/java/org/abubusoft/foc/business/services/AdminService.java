package org.abubusoft.foc.business.services;

import java.time.LocalDate;
import java.util.List;

import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.model.UploaderSummary;

public interface AdminService extends AbstractUserService<Administrator> {

	List<UploaderSummary> reportCloudFileForAllUploaders(LocalDate validoDal, LocalDate validoAl);

	List<UploaderDetailSummary> reportConsumerForAllUploaders(LocalDate validoDal, LocalDate validoAl);
	
}
