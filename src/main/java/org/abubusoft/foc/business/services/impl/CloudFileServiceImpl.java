package org.abubusoft.foc.business.services.impl;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.abubusoft.foc.business.services.CloudFileService;
import org.abubusoft.foc.repositories.CloudFileRepository;
import org.abubusoft.foc.repositories.model.CloudFile;
import org.abubusoft.foc.repositories.model.CloudFileTag;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

	private Storage storage;

	@Override
	public CloudFile insertFile(CloudFile cloudFile, InputStream content) {
		// String mimeType = MimeTypeUtils.getFromFileName(cloudFile.getFileName());

		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd-HHmmssSSS-");
		DateTime dt = DateTime.now(DateTimeZone.UTC);
		String dtString = dt.toString(dtf);
		String storageFileName = dtString + cloudFile.getFileName();

		BlobId blobId = BlobId.of(bucketName, storageFileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(cloudFile.getMimeType()).build();
		@SuppressWarnings("deprecation")
		BlobInfo blob = storage.create(blobInfo, content);
		logger.debug(String.format("File salvato su storage %s,%s", blobId.getBucket(), blobId.getName()));

		String blobName = blob.getBlobId().getName();

		cloudFile.setUuid(UUID.randomUUID().toString());
		cloudFile.setStorageName(blobName);
		//cloudFile.setCreatedDateTime(LocalDateTime.now());
		//cloudFile.setCreatedDateTime(LocalDateTime.now());

		return repository.save(cloudFile);

	}

	@Override
	public void deleteAllFiles() {
		// cancelliamo da storage
		Page<Blob> blobs = storage.get(bucketName).list();
		for (Blob blob : blobs.iterateAll()) {
			blob.delete();
		}

		// cancelliamo da db
		repository.deleteAll();

	}

	@Override
	public Pair<CloudFile, byte[]> getFile(String fileUuid) {
		CloudFile file = repository.findByUuid(fileUuid);

		BlobId blobId = BlobId.of(bucketName, file.getStorageName());
		Blob blob = storage.get(blobId);

		Pair<CloudFile, byte[]> result = Pair.of(file, blob.getContent());
		return result;
	}

	@Override
	public List<CloudFile> findByUploaderAndConsumer(long uploaderId, long consumerId) {
		return repository.findByUploaderAndConsumer(uploaderId, consumerId);
	}

	@Override
	public List<CloudFileTag> findTagsByUploaderAndConsumer(long uploaderId, long consumerId) {
		return repository.findTagsByUploaderAndConsumer(uploaderId, consumerId).stream()
				.sorted(Comparator.comparing(CloudFileTag::getTag)).collect(Collectors.toList());
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
		//file.setModifiedDateTime(LocalDateTime.now());
		return repository.save(file);
	}

	@Override
	public List<CloudFile> findByConsumerAndUploaderAndTags(long consumerId, long uploaderId, Set<String> tags) {
		if (tags == null || tags.size() == 0) {
			return repository.findByUploaderAndConsumer(uploaderId, consumerId);
		} else {
			return repository.findByUploaderAndConsumerAndTags(uploaderId, consumerId, tags);
		}
	}

	@Override
	public Iterable<CloudFile> findAll() {
		return repository.findAll(Sort.by(Direction.ASC, "viewed").and(Sort.by(Direction.DESC, "createdDateTime").and(Sort.by(Direction.DESC, "fileName"))));
	}

	@Override
	public boolean deleteByUUID(String fileUUID) {
		CloudFile file = repository.findByUuid(fileUUID);

		BlobId blobId = BlobId.of(bucketName, file.getStorageName());
		storage.delete(blobId);

		repository.deleteById(file.getId());

		return true;
	}

	@Override
	public CloudFile findByUUID(String fileUUID) {
		return repository.findByUuid(fileUUID);
	}

}
