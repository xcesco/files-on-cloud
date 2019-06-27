package org.abubusoft.foc.business.facades.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
	public CloudFileWto create() {
		CloudFileWto result = new CloudFileWto();
						
		return result;
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
	public long save(long uploaderId, CloudFileInfoWto cloudFileInfo,
			MultipartFile multipartFile) throws IOException {
		
		ConsumerWto consumerWto = cloudFileInfo.getConsumer();
		Optional<Uploader> uploader = uploaderService.findById(uploaderId);

		// vediamo se ha id o codice fiscale. Nel secondo caso lo crea, sempre che non
		// esista già
		Consumer consumer;
		Optional<Consumer> consumerInDb = consumerService.findByCodiceFiscale(consumerWto.getCodiceFiscale());
		if (consumerInDb.isPresent()) {
			// assert ok, c'è l'abbiamo
			consumer=consumerInDb.get();
		} else {
			// lo dobbiamo inserire
			consumer = new Consumer();
			consumer.setCodiceFiscale(consumerWto.getCodiceFiscale());
			consumer.setEmail(consumerWto.getEmail());
			consumer.setUsername(consumerWto.getUsername());
			consumer.setDisplayName(consumerWto.getDisplayName());
			consumer=consumerService.insertUser(consumer, "password");
		}

		CloudFile cloudFile = mapper.convertToFileDto(cloudFileInfo, multipartFile);
		cloudFile.setConsumer(consumer);
		cloudFile.setUploader(uploader.get());

		cloudFile=cloudFileService.uploadFile(cloudFile, multipartFile.getInputStream());		
		
		// TODO inserire invio notifica

		return cloudFile.getId();
	}


	@Override
	public long create(long uploaderId, long consumerId, CloudFileInfoWto cloudFile) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void storeFile(InputStream inputStream) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CloudFileWto create(long uploaderId, long consumerId) {
		CloudFileWto result = new CloudFileWto();
		
		result.setConsumer(mapper.convertConsumerToWto(consumerService.findById(consumerId).get()));
		result.setUploader(mapper.convertUploaderToWto(uploaderService.findById(uploaderId).get()));
		
		return result;
	}

}
