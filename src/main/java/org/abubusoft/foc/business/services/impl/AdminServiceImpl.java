package org.abubusoft.foc.business.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.abubusoft.foc.business.services.AdminService;
import org.abubusoft.foc.repositories.AdministratorsRepository;
import org.abubusoft.foc.repositories.model.AdminReportItem;
import org.abubusoft.foc.repositories.model.Administrator;
import org.abubusoft.foc.repositories.model.UploaderDetailSummary;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends AbstractUserServiceImpl<AdministratorsRepository, Administrator>
		implements AdminService {

	
	@Override
	public List<AdminReportItem> report(LocalDate validoDal, LocalDate validoAl) {
		//repository.findAll().forEach(action);
		
		List<UploaderDetailSummary> input = repository.reportConsumerForAllUploaders(validoDal.atStartOfDay(), validoAl.atTime(23, 59, 59));
		
		AdminReportItem currentItem=null;
		List<AdminReportItem> result=new ArrayList<>();
		
		for (UploaderDetailSummary item:input) {
			if (currentItem==null || currentItem.getUploaderId()!=item.getUploaderId()) {
				currentItem = createItem(result, item);
			} 
			
			currentItem.setFileCount(currentItem.getFileCount()+item.getSummaryCount());
			currentItem.setConsumerCount(currentItem.getConsumerCount()+1);
		}
		
		return result;
	}

	private AdminReportItem createItem(List<AdminReportItem> result, UploaderDetailSummary item) {
		AdminReportItem currentItem;
		currentItem=new AdminReportItem();
		currentItem.setUploaderId(item.getUploaderId());
		currentItem.setUploaderDisplayName(item.getUploaderDisplayName());
						
		result.add(currentItem);
		return currentItem;
	}

}
