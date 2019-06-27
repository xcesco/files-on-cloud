package org.abubusoft.foc.business.facades.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.mail.Multipart;

import org.abubusoft.foc.business.facades.CloudFileFacade;
import org.abubusoft.foc.business.services.CloudFileService;
import org.abubusoft.foc.business.services.ConsumerService;
import org.abubusoft.foc.business.services.UploaderService;
import org.abubusoft.foc.repositories.model.CloudFile;
import org.abubusoft.foc.repositories.model.CloudFileTag;
import org.abubusoft.foc.repositories.model.Consumer;
import org.abubusoft.foc.repositories.model.Uploader;
import org.abubusoft.foc.web.model.CloudFileInfoWto;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerAndCloudFileWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.abubusoft.foc.web.support.WtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudFileFacadeImpl implements CloudFileFacade {
	protected WtoMapper mapper = WtoMapper.INSTANCE;
	
	protected UploaderService uploaderService;
	
	protected ConsumerService consumerService;
	
	protected CloudFileService cloudFileService;

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
	public List<CloudFileInfoWto> findByConsumerAndUploader(long consumerId, long uploaderId, Set<String> tags) {
		List<CloudFile> result = cloudFileService.findByConsumerAndUploaderAndTags(consumerId, uploaderId, tags);
		
		return mapper.convertCloudFileListToWto(result);
	}

	@Override
	public Pair<CloudFile, byte[]> getFile(String fileUUID) {
		return cloudFileService.getFile(fileUUID);
	}
	
	@Override
	public List<CloudFileTag> findTagsByUploaderAndConsumer(long uploaderId, long consumerId) {
		return cloudFileService.findTagsByUploaderAndConsumer(uploaderId, consumerId);
	}
	
	@Override
	public CloudFileWto create(long uploaderId, long consumerId) {
		Optional<Uploader> uploader = uploaderService.findById(uploaderId);
		Optional<Consumer> consumer = consumerService.findById(consumerId);

		if (uploader.isPresent() && consumer.isPresent()) {
			CloudFileWto result = new CloudFileWto();
			result.setConsumer(mapper.convertConsumerToWto(consumer.get()));
			result.setUploader(mapper.convertUploaderToWto(uploader.get()));

			return result;
		}

		return null;
	}

	@Override
	public void storeFile(InputStream inputStream) {
		//uploaderService.
		
	}
	
	@Override
	public List<CloudFileInfoWto> findByUploaderAndConsumer(long uploaderId, long consumerId) {
		return mapper.convertCloudFileListToWto(cloudFileService.findByUploaderAndConsumer(uploaderId, consumerId));
	}

	@Override
	public boolean deleteByUploaderAndConsumerAndFile(long uploaderId, long consumerId, long fileId) {
		CloudFile file = cloudFileService.findByUploaderAndConsumerAndFileId(uploaderId, consumerId, fileId);

		cloudFileService.deleteById(file.getId());

		return true;
	}

	@Override
	public CloudFileInfoWto findByUploaderAndConsumerAndFile(long uploaderId, long consumerId, long fileId) {
		CloudFile file = cloudFileService.findByUploaderAndConsumerAndFileId(uploaderId, consumerId, fileId);

		return mapper.convertToFileWto(file);
	}

	@Override
	public long create(long uploaderId, CloudFileInfoWto cloudFileInfo, MultipartFile file) {
		// TODO inserire invio notifica

		CloudFile file = mapper.convertToFileDto(cloudFile);

		Optional<Uploader> uploader = uploaderService.findById(uploaderId);
		
		ConsumerWto inputConsumer = cloudFileInfo.getConsumer();
		
		// vediamo se ha id o codice fiscale. Nel secondo caso lo crea, sempre che non esista gia'
		Optional<Consumer> consumer = consumerService.findById(inputConsumer);

		file.setConsumer(consumer.get());
		file.setUploader(uploader.get());

		file = cloudFileService.save(file);

		return file.getId();
	}


	@Override
	public boolean create(long uploaderId, ConsumerAndCloudFileWto consumerCloudFile) {
		//TODO inserire notifica
		
		Consumer consumer = new Consumer();
		consumer.setCodiceFiscale(consumerCloudFile.getConsumerCodiceFiscale());
		consumer.setDisplayName(consumerCloudFile.getConsumerDisplayName());
		consumer.setEmail(consumerCloudFile.getConsumerEmail());
		consumer.setUsername(consumerCloudFile.getConsumerUsername());

		Optional<Uploader> uploaderDto = uploaderService.findById(uploaderId);
		Optional<Consumer> consumerDto = consumerService.findByCodiceFiscale(consumer.getCodiceFiscale());
		if (!consumerDto.isPresent()) {
			consumer = consumerService.updateByUsername(consumer);
		}

		cloudFileService.uploadFile(uploaderDto.get().getUsername(), consumer, consumerCloudFile.getFileName(),
				consumerCloudFile.getFile().getBytes(), consumerCloudFile.getTags());

		return true;
	}
}
