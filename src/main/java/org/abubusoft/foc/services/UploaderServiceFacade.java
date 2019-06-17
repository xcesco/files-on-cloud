package org.abubusoft.foc.services;

import java.util.List;

import javax.validation.Valid;

import org.abubusoft.foc.web.model.CloudFileWto;
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

	ConsumerWto createConsumer(@Valid ConsumerWto value);

	ConsumerWto updateConsumerById(@Valid ConsumerWto value);

	boolean deleteConsumerById(long id);

	List<CloudFileWto> findCloudFilesByUploaderAndConsumer(long uploaderId, long consumerId);

	boolean deleteCloudFileByUploaderAndConsumerAndFile(long uploaderId, long consumerId, long fileId);

	CloudFileWto findCloudFilesByUploaderAndConsumerAndFile(long uploaderId, long consumerId, long fileId);

	long createCloudFile(long uploaderId, long consumerId, CloudFileWto cloudFile);
	

}
