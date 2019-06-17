package org.abubusoft.foc.repositories;

import java.util.List;

import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumersRepository extends UserRepository<Consumer> {

	Consumer findByCodiceFiscale(String codiceFiscale);
	
	boolean existsByCodiceFiscale(String codiceFiscale);

	@Query("SELECT u.id as uploaderId, u.displayName as uploaderDisplayName, c.id as consumerId, c.displayName as consumerDisplayName, count(f) as fileCount FROM CloudFile f JOIN f.uploader u JOIN f.consumer c WHERE c.id=:consumerId  GROUP BY c.id, u.id ORDER BY u.displayName")
	List<UploaderDetailSummary> reportUploaderByConsumer(long consumerId);
	
	@Query("SELECT u.id as uploaderId, u.displayName as uploaderDisplayName, c.id as consumerId, c.displayName as consumerDisplayName, count(f) as fileCount FROM CloudFile f JOIN f.uploader u JOIN f.consumer c WHERE c.id=:consumerId  GROUP BY c.id, u.id ORDER BY u.displayName")
	List<String> reportTagsByConsumer(long consumerId);
	
	
}