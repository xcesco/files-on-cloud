package org.abubusoft.foc.repositories;

import java.util.List;

import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.Uploader;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudFileRepository extends PagingAndSortingRepository<CloudFile, Long> {
     
	//@Query("SELECT c FROM CloudFile c WHERE c.consumer=:consumer and c.uuid=:uuid")
	CloudFile findByConsumerAndUuid(Consumer consumer, String uuid);

	@Query("SELECT c FROM CloudFile c WHERE c.consumer=:consumer and c.uploader=:uploader")
	List<CloudFile> findByConsumerAndUploader(Consumer consumer, Uploader uploader);

	@Query("SELECT c FROM CloudFile c WHERE c.consumer=:consumerId and c.uploader=:uploaderId ORDER BY c.viewTime, c.createdDateTime ")
	List<CloudFile> findByConsumerIdAndUploaderId(long consumerId, long uploaderId);
	
}