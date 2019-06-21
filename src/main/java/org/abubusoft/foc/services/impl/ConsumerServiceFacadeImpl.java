package org.abubusoft.foc.services.impl;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.services.CloudFileService;
import org.abubusoft.foc.services.ConsumerService;
import org.abubusoft.foc.services.ConsumerServiceFacade;
import org.abubusoft.foc.services.UploaderService;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.abubusoft.foc.web.support.WtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceFacadeImpl implements ConsumerServiceFacade {

	private WtoMapper mapper = WtoMapper.INSTANCE;

	private ConsumerService consumerService;
	
	private UploaderService uploaderService;
	
	private CloudFileService cloudFileService;

	@Autowired
	public void setCloudFileService(CloudFileService cloudFileService) {
		this.cloudFileService = cloudFileService;
	}

	@Autowired
	public void setUploaderService(UploaderService uploaderService) {
		this.uploaderService = uploaderService;
	}

	@Autowired
	public void setConsumerService(ConsumerService consumerService) {
		this.consumerService = consumerService;
	}
	
	@Override
	public ConsumerWto updateConsumerById(@Valid ConsumerWto value) {
		Consumer user = mapper.convertConsumerToDto(value);
		Consumer result = consumerService.updateById(user);

		return mapper.convertConsumerToWto(result);
	}
	
	@Override
	public String consumerGetChangePasswordUrlById(long id) {
		return consumerService.getChangePasswordUrlById(id);		
	}

	@Override
	public List<UploaderDetailSummary> findUploadersWithFileByConsumerId(long consumerId) {
		List<UploaderDetailSummary> result = consumerService.reportConsumerForUploader(consumerId);
		
		return result;
	}

	@Override
	public List<CloudFileWto> findFilesByConsumerAndUploader(long consumerId, long uploaderId, Set<String> tags) {
		List<CloudFile> result = cloudFileService.findByConsumerAndUploaderAndTags(consumerId, uploaderId, tags);
		
		return mapper.convertCloudFileListToWto(result);
	}

	@Override
	public Pair<CloudFile, byte[]> getFile(String fileUUID) {
		return cloudFileService.getFile(fileUUID);
	}
		
}
