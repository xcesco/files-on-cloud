package org.abubusoft.foc.repositories;

import java.util.List;

import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.Uploader;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadersRepository extends UserRepository<Uploader> {
	
	@Query("SELECT DISTINCT u FROM CloudFile f JOIN f.uploader u JOIN f.consumer c WHERE c.id=:consumerId ORDER BY u.displayName")
	List<Uploader> findByConsumer(long consumerId);

	@Query("SELECT c FROM CloudFile c WHERE c.consumer=:consumer and c.uploader=:uploader")
	List<CloudFile> findByConsumerAndUploader(Consumer consumer, Uploader uploader);
		
}