package org.abubusoft.foc.business.facades.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.abubusoft.foc.business.facades.AdminFacade;
import org.abubusoft.foc.business.services.AdminService;
import org.abubusoft.foc.business.services.UploaderService;
import org.abubusoft.foc.repositories.model.AdminReportItem;
import org.abubusoft.foc.repositories.model.Administrator;
import org.abubusoft.foc.repositories.model.Uploader;
import org.abubusoft.foc.web.model.AdminWto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminFacadeImpl extends AbstractUserFacadeImpl<AdminWto, AdminService> implements AdminFacade {

	protected UploaderService uploaderService;

	@Override
	public AdminWto create() {
		AdminWto result = new AdminWto();
		
		prepareData(result);

		return result;
	}
	
	@Override
	public List<AdminWto> findAll() {
		return mapper.convertAdminListToWto(service.findAll());
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
	public List<AdminReportItem> report(LocalDate validoDal, LocalDate validoAl) {
		List<AdminReportItem> result=new ArrayList<>();
		for (Uploader item: uploaderService.findAll())
		{
			result.add(new AdminReportItem(item));
		}
		
		List<AdminReportItem> notNotValue = service.report(validoDal, validoAl);
		
		notNotValue.forEach(current -> {
			for (AdminReportItem item: result) {
				if (item.getUploaderId()==current.getUploaderId()) {
					item.setFileCount(current.getFileCount());
					item.setConsumerCount(current.getConsumerCount());
				}
			}
		});
		
		return result;
	}

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
	
//	@Override
//	public List<UploaderSummary> reportCloudFileForAllUploaders(LocalDate validoDal, LocalDate validoAl) {
//		return service.reportCloudFileForAllUploaders(validoDal, validoAl);
//	}

	@Autowired
	public void setUploaderService(UploaderService uploaderService) {
		this.uploaderService = uploaderService;
	}
	


}
