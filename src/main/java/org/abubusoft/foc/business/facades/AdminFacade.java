package org.abubusoft.foc.business.facades;

import java.time.LocalDate;
import java.util.List;

import org.abubusoft.foc.repositories.model.UploaderDetailSummary;
import org.abubusoft.foc.repositories.model.UploaderSummary;
import org.abubusoft.foc.web.model.AdminWto;

public interface AdminFacade extends AbstractUserFacade<AdminWto> {
	List<UploaderSummary> reportCloudFileForAllUploaders(LocalDate validoDal, LocalDate validoAl);

	List<UploaderDetailSummary> reportConsumerForAllUploaders(LocalDate validoDal, LocalDate validoAl);
	
}
