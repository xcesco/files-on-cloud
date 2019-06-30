package org.abubusoft.foc.business.services;

import java.time.LocalDate;
import java.util.List;

import org.abubusoft.foc.repositories.model.AdminReportItem;
import org.abubusoft.foc.repositories.model.Administrator;
import org.abubusoft.foc.repositories.model.UploaderDetailSummary;
import org.abubusoft.foc.repositories.model.UploaderSummary;

public interface AdminService extends AbstractUserService<Administrator> {

	List<UploaderSummary> reportCloudFileForAllUploaders(LocalDate validoDal, LocalDate validoAl);

	List<AdminReportItem> report(LocalDate validoDal, LocalDate validoAl);
		
}
