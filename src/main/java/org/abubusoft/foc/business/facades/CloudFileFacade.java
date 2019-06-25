package org.abubusoft.foc.business.facades;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.CloudFileTag;
import org.abubusoft.foc.web.model.CloudFileWto;
import org.abubusoft.foc.web.model.ConsumerAndCloudFileWto;
import org.springframework.data.util.Pair;

public interface CloudFileFacade {
	List<CloudFileWto> findByUploaderAndConsumer(long uploaderId, long consumerId);

	boolean deleteByUploaderAndConsumerAndFile(long uploaderId, long consumerId, long fileId);

	CloudFileWto findByUploaderAndConsumerAndFile(long uploaderId, long consumerId, long fileId);

	long create(long uploaderId, long consumerId, CloudFileWto cloudFile);

	CloudFileWto create(long uploaderId, long consumerId);

	boolean create(long uploaderId, ConsumerAndCloudFileWto consumerCloudFile);

	List<CloudFileTag> findTagsByUploaderAndConsumer(long uploaderId, long consumerId);

	List<CloudFileWto> findByConsumerAndUploader(long consumerId, long uploaderId, Set<String> tags);

	Pair<CloudFile, byte[]> getFile(String fileUUID);

	void storeFile(InputStream inputStream);
}
