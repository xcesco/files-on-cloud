package org.abubusoft.foc.services;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.CloudFileTag;
import org.abubusoft.foc.model.Consumer;
import org.springframework.data.util.Pair;

public interface CloudFileService {
 
	String uploadFile(Part filePart) throws IOException;

	String getImageUrl(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;
	
	CloudFile uploadFile(String uploaderUsername, Consumer consumer, String fileName, byte[] content, Set<String> tags);

	void deleteAllFiles();

	Pair<CloudFile, byte[]> getFile(String username, String fileUuid);

	//List<CloudFile> findByConsumerAndUploader(Consumer consumer, Uploader uploader1);
	
	List<CloudFileTag> findTagsByConsumer(long consumerId);

	List<CloudFile> findByUploaderAndConsumer(long uploaderId, long consumerId);

	List<CloudFile> findByUploaderAndConsumerTags(long uploaderId, long consumerId, Set<String> tags);
	
	CloudFile findByUploaderAndConsumerAndFileId(long uploaderId, long consumerId, long fileId);

	boolean deleteById(long id);

	CloudFile save(CloudFile file);

	
	
}
