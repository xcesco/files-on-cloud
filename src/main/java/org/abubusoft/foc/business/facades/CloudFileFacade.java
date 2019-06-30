package org.abubusoft.foc.business.facades;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
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
	CloudFileWto create();

	CloudFileWto create(long uploaderId, long consumerId);

	boolean deleteByUploaderAndConsumerAndFile(long uploaderId, long consumerId, long fileId);

	boolean deleteByUUID(String fileUUID);

	List<CloudFileWto> findAll();

	List<CloudFileWto> findByConsumerAndUploader(long consumerId, long uploaderId, Set<String> tags);

	List<CloudFileWto> findByUploaderAndConsumer(long uploaderId, long consumerId);

	CloudFileInfoWto findByUploaderAndConsumerAndFile(long uploaderId, long consumerId, long fileId);

	List<CloudFileTag> findTagsByUploaderAndConsumer(long uploaderId, long consumerId);

	Pair<CloudFile, byte[]> getFile(String fileUUID);

	boolean sendNotificationByUUID(String fileUUID);

	void updateViewStatus(String ip, CloudFile first);

	long save(long uploaderId, ConsumerWto consumerWto, String hashtag,
			MultipartFile multipartFile) throws IOException;

	long save(long uploaderId, LocalDateTime creationTime, ConsumerWto consumerWto, String hashtag, String fileName,
			String fileMediaType, long fileSize, InputStream fileContent) throws IOException;

}
