package org.abubusoft.foc.business.services;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.abubusoft.foc.repositories.model.CloudFile;
import org.abubusoft.foc.repositories.model.CloudFileTag;
import org.springframework.data.util.Pair;

public interface CloudFileService {
 
	void deleteAllFiles();
	
	List<CloudFile> findByUploaderAndConsumer(long uploaderId, long consumerId);

	CloudFile findByUploaderAndConsumerAndFileId(long uploaderId, long consumerId, long fileId);

	boolean deleteById(long id);

	CloudFile save(CloudFile file);

	List<CloudFile> findByConsumerAndUploaderAndTags(long consumerId, long uploaderId, Set<String> tags);

	List<CloudFileTag> findTagsByUploaderAndConsumer(long uploaderId, long consumerId);

	Pair<CloudFile, byte[]> getFile(String fileUuid);

	CloudFile insertFile(CloudFile cloudFile, InputStream content);

	Iterable<CloudFile> findAll();

	boolean deleteByUUID(String fileUUID);

	CloudFile findByUUID(String fileUUID);	
	
}
