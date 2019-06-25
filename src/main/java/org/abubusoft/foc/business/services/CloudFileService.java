package org.abubusoft.foc.business.services;

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
	
	List<CloudFile> findByUploaderAndConsumer(long uploaderId, long consumerId);

	CloudFile findByUploaderAndConsumerAndFileId(long uploaderId, long consumerId, long fileId);

	boolean deleteById(long id);

	CloudFile save(CloudFile file);

	List<CloudFile> findByConsumerAndUploaderAndTags(long consumerId, long uploaderId, Set<String> tags);

	List<CloudFileTag> findTagsByUploaderAndConsumer(long uploaderId, long consumerId);

	Pair<CloudFile, byte[]> getFile(String fileUuid);

	
	
}
