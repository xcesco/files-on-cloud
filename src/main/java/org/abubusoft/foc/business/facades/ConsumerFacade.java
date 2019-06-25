package org.abubusoft.foc.business.facades;

import java.util.List;

import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.web.model.ConsumerWto;

public interface ConsumerFacade extends AbstractUserFacade<ConsumerWto>  {
			
	List<UploaderDetailSummary> findUploadersWithFileByConsumerId(long consumerId);


}
