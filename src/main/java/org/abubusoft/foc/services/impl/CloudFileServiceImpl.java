package org.abubusoft.foc.services.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.abubusoft.foc.repositories.CloudFileRepository;
import org.abubusoft.foc.services.CloudFileService;
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
	public String uploadFile(Part filePart) throws IOException {
		return uploadFile(filePart.getSubmittedFileName(), filePart.getInputStream());
	}
	
	@Override
	public String uploadFile(String filename, InputStream inputStream) throws IOException {
		MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
	    String mimeTypeString = fileTypeMap.getContentType(filename);
	    MimeType mimeType=MimeType.valueOf(mimeTypeString);
		
		BlobInfo blobInfo = saveFile(filename, mimeType, inputStream);
		return blobInfo.getMediaLink();
	}

	@SuppressWarnings("deprecation")
	private BlobInfo saveFile(String fileName, MimeType mimeType, InputStream inputStream) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
		DateTime dt = DateTime.now(DateTimeZone.UTC);
		String dtString = dt.toString(dtf);
		fileName = fileName + dtString;

		 BlobId blobId = BlobId.of(bucketName, fileName);
		 BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(mimeType.toString()).build();
		 BlobInfo blob = storage.create(blobInfo, inputStream);
		 
		 return blob;
//		
//		// the inputstream is closed by default, so we don't need to close it here
//		BlobInfo blobInfo = storage.create(BlobInfo.newBuilder(bucketName, fileName)
//				// Modify access list to allow all users with link to read file
//				.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))).build(), inputStream);
//		// return the public download link
//		return blobInfo;
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

}
