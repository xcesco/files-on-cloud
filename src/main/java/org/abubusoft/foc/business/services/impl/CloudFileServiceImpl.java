package org.abubusoft.foc.business.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.abubusoft.foc.business.services.CloudFileService;
import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.CloudFileTag;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.repositories.CloudFileRepository;
import org.abubusoft.foc.repositories.ConsumersRepository;
import org.abubusoft.foc.repositories.UploadersRepository;
import org.abubusoft.foc.repositories.support.MimeTypeUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

/**
 * https://cloud.google.com/java/getting-started/using-cloud-storage
 * 
 * @author xcesco
 *
 */
@Component
public class CloudFileServiceImpl implements CloudFileService {

	Logger logger = LoggerFactory.getLogger(CloudFileServiceImpl.class);

	@Value("${app.file-storage.bucket-name}")
	String bucketName;

	@PostConstruct
	void init() {
		storage = StorageOptions.getDefaultInstance().getService();
	}

	private CloudFileRepository repository;

	@Autowired
	public void setRepository(CloudFileRepository repository) {
		this.repository = repository;
	}

	private UploadersRepository uploaderRepository;

	@Autowired
	public void setUploaderRepository(UploadersRepository uploaderRepository) {
		this.uploaderRepository = uploaderRepository;
	}

	@Autowired
	public void setConsumerRepository(ConsumersRepository consumerRepository) {
		this.consumerRepository = consumerRepository;
	}

	private ConsumersRepository consumerRepository;

	private Storage storage;

	@Override
	public String uploadFile(Part filePart) throws IOException {
		// return uploadFile(filePart.getSubmittedFileName(),
		// filePart.getInputStream());
		return null;
	}

	/**
	 * Extracts the file payload from an HttpServletRequest, checks that the file
	 * extension is supported and uploads the file to Google Cloud Storage.
	 */
	@Override
	public String getImageUrl(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Part filePart = req.getPart("file");
		final String fileName = filePart.getSubmittedFileName();
		String imageUrl = req.getParameter("imageUrl");
		// Check extension of file
		if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
			final String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
			String[] allowedExt = { "jpg", "jpeg", "png", "gif" };
			for (String s : allowedExt) {
				if (extension.equals(s)) {
					return this.uploadFile(filePart);
				}
			}
			throw new ServletException("file must be an image");
		}
		return imageUrl;
	}

	@Override
	public CloudFile uploadFile(String uploaderUsername, Consumer consumer, String fileName, byte[] content,
			Set<String> tags) {
		Uploader uploader = uploaderRepository.findByUsername(uploaderUsername);

		// recuperiamo interamente il consumer o lo creiamo nel caso in cui non ci sia
		// il codice fiscale
		Optional<Consumer> optConsumer = consumerRepository.findByCodiceFiscale(consumer.getCodiceFiscale());
		if (optConsumer.isPresent()) {
			consumer = optConsumer.get();
		} else {
			consumer = consumerRepository.save(consumer);
		}

		// only by file name
		String mimeType = MimeTypeUtils.getFromFileName(fileName);

		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd-HHmmssSSS-");
		DateTime dt = DateTime.now(DateTimeZone.UTC);
		String dtString = dt.toString(dtf);
		String storageFileName = dtString + fileName;

		BlobId blobId = BlobId.of(bucketName, storageFileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(mimeType).build();
		BlobInfo blob = storage.create(blobInfo, content);
		logger.debug(String.format("File salvato su storage %s,%s", blobId.getBucket(), blobId.getName()));

		String blobName = blob.getBlobId().getName();

		CloudFile cloudFile = new CloudFile();
		cloudFile.setFileName(fileName);
		cloudFile.setMimeType(mimeType);
		cloudFile.setContentLength(content.length);
		cloudFile.setTags(tags);
		cloudFile.setUuid(UUID.randomUUID().toString());
		cloudFile.setStorageName(blobName);

		cloudFile.setConsumer(consumer);
		cloudFile.setUploader(uploader);

		return repository.save(cloudFile);

	}

	@Override
	public void deleteAllFiles() {
		// cancelliamo da db
		repository.deleteAll();

		// cancelliamo da storage
		Page<Blob> blobs = storage.get(bucketName).list();
		for (Blob blob : blobs.iterateAll()) {
			blob.delete();
		}
	}

	@Override
	public Pair<CloudFile, byte[]> getFile(String fileUuid) {
		CloudFile file = repository.findByUuid(fileUuid);

		BlobId blobId = BlobId.of(bucketName, file.getStorageName());
		Blob blob = storage.get(blobId);

		Pair<CloudFile, byte[]> result = Pair.of(file, blob.getContent());
		return result;
	}

	/*
	@Override
	public List<CloudFile> findByConsumerAndUploader(Consumer consumer, Uploader uploader) {
		return repository.findByConsumerAndUploader(consumer, uploader);
	}*/
	
	@Override
	public List<CloudFile> findByUploaderAndConsumer(long uploaderId, long consumerId) {
		return repository.findByUploaderAndConsumer(uploaderId, consumerId);
	}	

	@Override
	public List<CloudFileTag> findTagsByUploaderAndConsumer(long uploaderId, long consumerId) {
		return repository.findTagsByUploaderAndConsumer(uploaderId, consumerId);
	}

	@Override
	public boolean deleteById(long id) {
		repository.deleteById(id);
		
		return true;	
	}

	@Override
	public CloudFile findByUploaderAndConsumerAndFileId(long uploaderId, long consumerId, long fileId) {
		return repository.findByUploaderAndConsumerAndFileId(uploaderId, consumerId, fileId);
	}

	@Override
	public CloudFile save(CloudFile file) {
		return repository.save(file);
	}

	@Override
	public List<CloudFile> findByConsumerAndUploaderAndTags(long consumerId, long uploaderId, Set<String> tags) {
		if (tags==null || tags.size()==0) {
			return repository.findByUploaderAndConsumer(uploaderId, consumerId);
		} else {
			return repository.findByUploaderAndConsumerAndTags(uploaderId, consumerId, tags);
		}
	}

}
