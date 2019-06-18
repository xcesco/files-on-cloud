package org.abubusoft.foc.services;

import java.util.List;

import javax.validation.Valid;

import org.abubusoft.foc.model.CloudFileTag;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerAndCloudFileWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.abubusoft.foc.web.model.UploaderWto;

public interface UploaderServiceFacade  {
	
	boolean deleteById(long id);

	String generateChangePasswordUrl(String username);

	List<UploaderWto> findAll();
	
	UploaderWto updateById(UploaderWto user);
	
	byte[] getLogoById(long id);

	UploaderWto findById(long uploaderId);

	UploaderWto createUploader(UploaderWto user);

	List<ConsumerWto> findAllConsumers();

	ConsumerWto saveConsumer(@Valid ConsumerWto value);

	boolean deleteConsumerById(long id);

	List<CloudFileWto> findCloudFilesByUploaderAndConsumer(long uploaderId, long consumerId);

	boolean deleteCloudFileByUploaderAndConsumerAndFile(long uploaderId, long consumerId, long fileId);

	CloudFileWto findCloudFilesByUploaderAndConsumerAndFile(long uploaderId, long consumerId, long fileId);

	long createCloudFile(long uploaderId, long consumerId, CloudFileWto cloudFile);

	String uploaderGetChangePasswordUrlByUsername(String username);

	boolean createCloudFile(long uploaderId, ConsumerAndCloudFileWto consumerCloudFile);

	List<CloudFileTag> findTagsByUploaderAndConsumer(long uploaderId, long consumerId);
	
	ConsumerWto createConsumer();

	CloudFileWto newCloudFile(long uploaderId, long consumerId);
	

}
