package org.abubusoft.foc.repositories;

import java.util.List;
import java.util.Set;

import org.abubusoft.foc.repositories.model.CloudFile;
import org.abubusoft.foc.repositories.model.CloudFileTag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudFileRepository extends PagingAndSortingRepository<CloudFile, Long> {
	
	@Query("SELECT DISTINCT t as tag FROM CloudFile f JOIN f.consumer c JOIN f.uploader u JOIN f.tags t WHERE c.id=:consumerId AND u.id=:uploaderId")
	List<CloudFileTag> findTagsByUploaderAndConsumer(long uploaderId, long consumerId);	
	
//	@Query("SELECT f FROM CloudFile f JOIN f.consumer c JOIN f.uploader u JOIN f.tags t WHERE u.id=:uploaderId AND c.id=:consumerId ORDER BY f.createdDateTime")
//	List<CloudFile> findByConsumer(long uploaderId, long consumerId);
//	
     
	//
	@Query("SELECT DISTINCT f FROM CloudFile f JOIN f.consumer c JOIN f.uploader u WHERE c.id=:consumerId and u.id=:uploaderId ORDER BY f.viewed ASC, f.createdDateTime DESC, f.fileName DESC")		
	List<CloudFile> findByUploaderAndConsumer(long uploaderId, long consumerId);

	//
	@Query("SELECT DISTINCT f FROM CloudFile f JOIN f.consumer c JOIN f.uploader u JOIN f.tags t WHERE u.id=:uploaderId AND c.id=:consumerId AND t IN :tags ORDER BY f.viewed ASC, f.createdDateTime DESC, f.fileName DESC")
	List<CloudFile> findByUploaderAndConsumerAndTags(long uploaderId, long consumerId, Set<String> tags);

	@Query("SELECT f FROM CloudFile f JOIN f.consumer c JOIN f.uploader u WHERE f.id=:fileId AND u.id=:uploaderId AND c.id=:consumerId")
	CloudFile findByUploaderAndConsumerAndFileId(long uploaderId, long consumerId, long fileId);

	@Query("SELECT c FROM CloudFile c WHERE c.uuid=:uuid")
	CloudFile findByUuid(String uuid);

	@Query("SELECT f FROM CloudFile f JOIN f.consumer c WHERE c.id=:consumerId")
	List<CloudFile> findByConsumerId(Long consumerId);
	
	@Query("SELECT f FROM CloudFile f JOIN f.uploader u WHERE u.id=:uploaderId")
	List<CloudFile> findByUploaderId(Long uploaderId);

//	@Query("DELETE FROM CloudFile c JOIN f.consumer c WHERE c.id=:consumerId")
//	int deleteByConsumerId(long consumerId);
//	
//	@Query("DELETE FROM CloudFile c JOIN f.uploader c WHERE u.id=:uploaderId")
//	int deleteByUploaderId(long uploaderId);
		
}