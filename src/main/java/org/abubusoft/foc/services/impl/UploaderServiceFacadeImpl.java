package org.abubusoft.foc.services.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.CloudFileTag;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.services.CloudFileService;
import org.abubusoft.foc.services.ConsumerService;
import org.abubusoft.foc.services.UploaderService;
import org.abubusoft.foc.services.UploaderServiceFacade;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerAndCloudFileWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.abubusoft.foc.web.model.UploaderWto;
import org.abubusoft.foc.web.support.WtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploaderServiceFacadeImpl implements UploaderServiceFacade {

	private WtoMapper mapper = WtoMapper.INSTANCE;

	private UploaderService uploaderService;
	
	private ConsumerService consumerService;
	
	private CloudFileService cloudFileService;

	@Autowired
	public void setCloudFileService(CloudFileService cloudFileService) {
		this.cloudFileService = cloudFileService;
	}

	@Autowired
	public void setConsumerService(ConsumerService consumerService) {
		this.consumerService = consumerService;
	}

	@Autowired
	public void setUploaderService(UploaderService uploaderService) {
		this.uploaderService = uploaderService;
	}

	@Override
	public UploaderWto createUploader(UploaderWto value) {
		Uploader user = mapper.convertUploaderToDto(value);
		Uploader result = uploaderService.createUser(user, value.getPassword());

		return mapper.convertUploaderToWto(result);
	}

	@Override
	public boolean deleteById(long id) {
		return uploaderService.deleteById(id);
	}

	@Override
	public String generateChangePasswordUrl(String username) {
		return uploaderService.getChangePasswordUrlByUsername(username);
	}

	@Override
	public List<UploaderWto> findAll() {
		return mapper.convertUploaderListToDto(uploaderService.findAll());
	}

	@Override
	public UploaderWto updateById(UploaderWto value) {
		Uploader user = mapper.convertUploaderToDto(value);
		Uploader result = uploaderService.updateById(user);

		return mapper.convertUploaderToWto(result);
	}

	@Override
	public byte[] getLogoById(long id) {
		Optional<Uploader> result = uploaderService.findById(id);

		if (result.isPresent()) {
			return result.get().getImage();
		}

		return null;
	}

	@Override
	public UploaderWto findById(long uploaderId) {
		Optional<Uploader> result = uploaderService.findById(uploaderId);

		if (result.isPresent()) {
			return mapper.convertUploaderToWto(result.get());
		}

		return null;

	}

	@Override
	public List<ConsumerWto> findAllConsumers() {
		return mapper.convertConsumerListToWto(consumerService.findAll());		
	}

	@Override
	public ConsumerWto createConsumer(@Valid ConsumerWto value) {
		Consumer user = mapper.convertConsumerToDto(value);
		Consumer result = consumerService.createUser(user, value.getPassword());

		return mapper.convertConsumerToWto(result);
	}

	@Override
	public ConsumerWto updateConsumerById(@Valid ConsumerWto value) {
		Consumer user = mapper.convertConsumerToDto(value);
		Consumer result = consumerService.updateById(user);

		return mapper.convertConsumerToWto(result);
	}

	@Override
	public boolean deleteConsumerById(long id) {
		return consumerService.deleteById(id);
	}

	@Override
	public List<CloudFileWto> findCloudFilesByUploaderAndConsumer(long uploaderId, long consumerId) {
		return mapper.convertCloudFileListToWto(cloudFileService.findByUploaderAndConsumer(uploaderId, consumerId));
	}

	@Override
	public boolean deleteCloudFileByUploaderAndConsumerAndFile(long uploaderId, long consumerId, long fileId) {
		CloudFile file = cloudFileService.findByUploaderAndConsumerAndFileId(uploaderId, consumerId, fileId);
		
		cloudFileService.deleteById(file.getId());
		
		return true;
	}

	@Override
	public CloudFileWto findCloudFilesByUploaderAndConsumerAndFile(long uploaderId, long consumerId, long fileId) {
		CloudFile file = cloudFileService.findByUploaderAndConsumerAndFileId(uploaderId, consumerId, fileId);
		
		return mapper.convertToFileWto(file);
	}

	@Override
	public long createCloudFile(long uploaderId, long consumerId, CloudFileWto cloudFile) {
		//TODO inserire invio notifica
		
		CloudFile file=mapper.convertToFileDto(cloudFile);
		
		Optional<Uploader> uploader=uploaderService.findById(uploaderId);
		Optional<Consumer> consumer=consumerService.findById(consumerId);
		
		file.setConsumer(consumer.get());
		file.setUploader(uploader.get());
		
		file=cloudFileService.save(file);
		
		return file.getId();
	}

	@Override
	public String uploaderGetChangePasswordUrlByUsername(String username) {
		return uploaderService.getChangePasswordUrlByUsername(username);
	}
		
	@Override
	public boolean createCloudFile(long uploaderId, ConsumerAndCloudFileWto consumerCloudFile) {
		Consumer consumer=new Consumer();
		consumer.setCodiceFiscale(consumerCloudFile.getConsumerCodiceFiscale());
		consumer.setDisplayName(consumerCloudFile.getConsumerDisplayName());
		consumer.setEmail(consumerCloudFile.getConsumerEmail());
		consumer.setUsername(consumerCloudFile.getConsumerUsername());
		
		
		
		Optional<Uploader> uploaderDto=uploaderService.findById(uploaderId);
		Optional<Consumer> consumerDto=consumerService.findByCodiceFiscale(consumer.getCodiceFiscale());
		if (!consumerDto.isPresent()) {
			consumer=consumerService.updateByUsername(consumer);
		}
		
		cloudFileService.uploadFile(uploaderDto.get().getUsername(), consumer, consumerCloudFile.getFileName(), consumerCloudFile.getFile().getBytes(), consumerCloudFile.getTags());		
		
		return true;
	}

	@Override
	public List<CloudFileTag> findTagsByUploaderAndConsumer(long uploaderId, long consumerId) {
		return cloudFileService.findTagsByUploaderAndConsumer(uploaderId, consumerId);
	}


}
