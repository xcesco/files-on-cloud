package org.abubusoft.foc.services;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.model.UploaderSummary;
import org.abubusoft.foc.web.model.AdminWto;
import org.abubusoft.foc.web.model.UploaderWto;

public interface AdminServiceFacade {

	AdminWto createAdmin(@Valid AdminWto value);

	AdminWto updateAdmin(@Valid AdminWto value);

	boolean deleteAdminById(long id);

	List<AdminWto> findAllAdmin();
	
	UploaderWto createUploader(@Valid UploaderWto value);

	UploaderWto updateUploader(@Valid UploaderWto value);

	boolean deleteUploaderById(long id);

	List<UploaderWto> findAllUploaders();

	List<UploaderSummary> reportCloudFileForAllUploaders(LocalDate validoDal, LocalDate validoAl);

	List<UploaderDetailSummary> reportConsumerForAllUploaders(LocalDate validoDal, LocalDate validoAl);
}
