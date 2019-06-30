package org.abubusoft.foc.business.facades.impl;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.abubusoft.foc.business.facades.CloudFileFacade;
import org.abubusoft.foc.business.services.CloudFileService;
import org.abubusoft.foc.business.services.ConsumerService;
import org.abubusoft.foc.business.services.SendMailService;
import org.abubusoft.foc.business.services.UploaderService;
import org.abubusoft.foc.repositories.model.CloudFile;
import org.abubusoft.foc.repositories.model.CloudFileTag;
import org.abubusoft.foc.repositories.model.Consumer;
import org.abubusoft.foc.repositories.model.Uploader;
import org.abubusoft.foc.web.model.CloudFileInfoWto;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.abubusoft.foc.web.support.WtoMapper;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudFileFacadeImpl implements CloudFileFacade {
	protected Logger logger=Logger.getLogger(getClass());
	
	protected WtoMapper mapper = WtoMapper.INSTANCE;

	protected UploaderService uploaderService;

	protected ConsumerService consumerService;

	protected CloudFileService cloudFileService;
	
	protected SendMailService sendMailService;

	@Autowired
	public void setSendMailService(SendMailService sendMailService) {
		this.sendMailService = sendMailService;
	}

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
	public List<CloudFileWto> findByConsumerAndUploader(long consumerId, long uploaderId, Set<String> tags) {
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
	public List<CloudFileWto> findByUploaderAndConsumer(long uploaderId, long consumerId) {
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
	public CloudFileWto create(long uploaderId, long consumerId) {
		CloudFileWto result = new CloudFileWto();
		
		result.setConsumer(mapper.convertConsumerToWto(consumerService.findById(consumerId).get()));
		result.setUploader(mapper.convertUploaderToWto(uploaderService.findById(uploaderId).get()));
		
		return result;
	}

	@Override
	public List<CloudFileWto> findAll() {
		return mapper.convertCloudFileListToWto(cloudFileService.findAll());
	}

	@Override
	public void updateViewStatus(String ip, CloudFile file) {
		file.setViewIp(ip);
		file.setViewTime(LocalDateTime.now());
		file.setViewed(true);
		
		cloudFileService.save(file);
		
	}

	@Override
	public boolean deleteByUUID(String fileUUID) {
		return cloudFileService.deleteByUUID(fileUUID);
	}

	@Override
	public boolean sendNotificationByUUID(String fileUUID) {
		CloudFile file = cloudFileService.findByUUID(fileUUID);
		logger.info("Invio email");
		sendMailService.send(file.getUploader(), file.getConsumer(), file);
		
		return true;
	}

	@Override
	public long save(long uploaderId, ConsumerWto consumerWto, String hashtag, MultipartFile multipartFile) throws IOException {
		return saveInternal(uploaderId, LocalDateTime.now(), consumerWto, hashtag, 
				multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getSize(), multipartFile.getInputStream());
	}
	
	@Override
	public long save(long uploaderId, LocalDateTime creationTime, ConsumerWto consumerWto, String hashtag, String fileName, String fileMediaType, long fileSize, InputStream fileContent) throws IOException {
		return saveInternal(uploaderId, creationTime, consumerWto, hashtag, fileName, fileMediaType, fileSize, fileContent);
	}

	private long saveInternal(long uploaderId, LocalDateTime creationTime, ConsumerWto consumerWto, String hashtag,
			String fileName, String fileMediaType, long fileSize, InputStream fileContent) throws IOException {
		Set<String> hashTagSet=null;
		if (StringUtils.hasText(hashtag)) {
			hashTagSet=new HashSet<>();			
			List<String> tagValues = Arrays.asList(hashtag.split(","));
			
			for (String item: tagValues) {
				if (StringUtils.hasText(item)) {
					hashTagSet.add(item.trim());
				}
			}	
		}	
		
		CloudFileInfoWto info=new CloudFileInfoWto();
		info.setConsumer(consumerWto);
		info.setTags(hashTagSet);
				
		Optional<Uploader> uploaderOpt = uploaderService.findById(uploaderId);
	    Uploader uploader=uploaderOpt.get();

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
		
		CloudFile cloudFile = mapper.convertToFileDto(uploader, consumer, creationTime, hashTagSet,fileName, fileMediaType, fileSize);
		cloudFile=cloudFileService.uploadFile(cloudFile, fileContent);		
		
		logger.info("Invio email");
		sendMailService.send(uploader, consumer, cloudFile);

		return cloudFile.getId();
	}

}
