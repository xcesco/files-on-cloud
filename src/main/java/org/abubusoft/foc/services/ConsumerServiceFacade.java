package org.abubusoft.foc.services;

import java.util.List;
import java.util.Set;

import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.springframework.data.util.Pair;

public interface ConsumerServiceFacade  {
	
	String consumerGetChangePasswordUrlById(long id);

	ConsumerWto updateConsumerById(ConsumerWto user);
	
	List<UploaderDetailSummary> findUploadersWithFileByConsumerId(long consumerId);

	List<CloudFileWto> findFilesByConsumerAndUploader(long consumerId, long uploaderId, Set<String> tags);

	Pair<CloudFile, byte[]> getFile(String fileUUID);
}
