package org.abubusoft.foc.repositories;

import java.util.List;
import java.util.Set;

import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.CloudFileTag;
import org.abubusoft.foc.model.Consumer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudFileRepository extends PagingAndSortingRepository<CloudFile, Long> {
	
	@Query("SELECT DISTINCT t as tag FROM CloudFile f JOIN f.consumer c JOIN f.uploader u JOIN f.tags t WHERE c.id=:consumerId")
	List<CloudFileTag> findTagsByConsumer(long consumerId);
	
	
	@Query("SELECT f FROM CloudFile f JOIN f.consumer c JOIN f.uploader u JOIN f.tags t WHERE u.id=:uploaderId AND c.id=:consumerId ORDER BY f.createdDateTime")
	List<CloudFile> findByConsumer(long uploaderId, long consumerId);
	
     
	//@Query("SELECT c FROM CloudFile c WHERE c.consumer=:consumer and c.uuid=:uuid")
	CloudFile findByConsumerAndUuid(Consumer consumer, String uuid);

//	@Query("SELECT c FROM CloudFile c WHERE c.consumer=:consumer and c.uploader=:uploader")
//	List<CloudFile> findByConsumerAndUploader(Consumer consumer, Uploader uploader);

	@Query("SELECT f FROM CloudFile f JOIN f.consumer c JOIN f.uploader u WHERE c.id=:consumerId and u.id=:uploaderId ORDER BY f.viewTime, f.createdDateTime ")		
	List<CloudFile> findByUploaderAndConsumer(long uploaderId, long consumerId);

	@Query("SELECT f FROM CloudFile f JOIN f.consumer c JOIN f.uploader u JOIN f.tags t WHERE u.id=:uploaderId AND c.id=:consumerId AND t IN :tags ORDER BY f.createdDateTime")
	List<CloudFile> findByUploaderAndConsumerAndTags(long uploaderId, long consumerId, Set<String> tags);
	
}