package org.abubusoft.foc.services.impl;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.repositories.CloudFileRepository;
import org.abubusoft.foc.repositories.ConsumersRepository;
import org.abubusoft.foc.repositories.UploadersRepository;
import org.abubusoft.foc.services.CloudFileService;
import org.jboss.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;

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
	
	protected Logger log=Logger.getLogger(getClass());

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

	public String uploadFile(String filename, byte[] content) throws IOException {
		MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
		String mimeTypeString = fileTypeMap.getContentType(filename);
		MimeType mimeType = MimeType.valueOf(mimeTypeString);

		BlobInfo blobInfo = saveFile(filename, mimeType, content);
		return blobInfo.getBlobId().getName();
	}

	private BlobInfo saveFile(String fileName, MimeType mimeType, byte[] content) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd-HHmmssSSS-");
		DateTime dt = DateTime.now(DateTimeZone.UTC);
		String dtString = dt.toString(dtf);
		fileName = dtString + fileName;

		BlobId blobId = BlobId.of(bucketName, fileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(mimeType.toString()).build();
		BlobInfo blob = storage.create(blobInfo, content);

		return blob;
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
	public void uploadFile(String uploaderUsername, Consumer consumer, String fileName, byte[] content, Set<String> tags) {
		Uploader uploader = uploaderRepository.findByUsername(uploaderUsername);

		// recuperiamo interamente il consumer o lo creiamo nel caso in cui non ci sia il codice fiscale
		if (consumerRepository.existsByCodiceFiscale(consumer.getCodiceFiscale())) {
			consumer = consumerRepository.findByCodiceFiscale(consumer.getCodiceFiscale());
		} else {
			consumer = consumerRepository.save(consumer);
		}
		
		MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
		String mimeTypeString = fileTypeMap.getContentType(fileName);
		MimeType mimeType = MimeType.valueOf(mimeTypeString);
		
		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd-HHmmssSSS-");
		DateTime dt = DateTime.now(DateTimeZone.UTC);
		String dtString = dt.toString(dtf);
		fileName = dtString + fileName;

		BlobId blobId = BlobId.of(bucketName, fileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(mimeType.toString()).build();
		BlobInfo blob = storage.create(blobInfo, content);
		log.debug(String.format("File salvato su storage %s,%s", blobId.getBucket(), blobId.getName()));
		
		String blobName=blob.getBlobId().getName();
		
		CloudFile cloudFile=new CloudFile();
		cloudFile.setFileName(fileName);
		cloudFile.setMimeType(mimeTypeString);
		cloudFile.setTags(tags);
		cloudFile.setUuid(UUID.randomUUID().toString());
		cloudFile.setStorageName(blobName);
		
		cloudFile.setConsumer(consumer);
		cloudFile.setUploader(uploader);
		
		repository.save(cloudFile);
		
	}

}
