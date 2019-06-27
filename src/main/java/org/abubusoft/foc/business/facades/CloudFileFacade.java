package org.abubusoft.foc.business.facades;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.abubusoft.foc.repositories.model.CloudFile;
import org.abubusoft.foc.repositories.model.CloudFileTag;
import org.abubusoft.foc.web.model.CloudFileInfoWto;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerWto;
import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;

public interface CloudFileFacade {
	List<CloudFileInfoWto> findByUploaderAndConsumer(long uploaderId, long consumerId);

	boolean deleteByUploaderAndConsumerAndFile(long uploaderId, long consumerId, long fileId);

	CloudFileInfoWto findByUploaderAndConsumerAndFile(long uploaderId, long consumerId, long fileId);

	long create(long uploaderId, long consumerId, CloudFileInfoWto cloudFile);

	List<CloudFileTag> findTagsByUploaderAndConsumer(long uploaderId, long consumerId);

	List<CloudFileInfoWto> findByConsumerAndUploader(long consumerId, long uploaderId, Set<String> tags);

	Pair<CloudFile, byte[]> getFile(String fileUUID);

	void storeFile(InputStream inputStream);

	long save(long uploaderId, CloudFileInfoWto cloudFileInfo, MultipartFile multipartFile) throws IOException;
	
	CloudFileWto create();

	CloudFileWto create(long uploaderId, long consumerId);

	
}
