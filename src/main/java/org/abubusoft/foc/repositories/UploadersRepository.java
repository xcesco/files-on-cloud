package org.abubusoft.foc.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.abubusoft.foc.model.CloudFile;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.model.UploaderSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadersRepository extends UserRepository<Uploader> {
	
	@Query("SELECT DISTINCT u FROM CloudFile f JOIN f.uploader u JOIN f.consumer c WHERE c.id=:consumerId ORDER BY u.displayName")
	List<Uploader> findByConsumer(long consumerId);

	@Query("SELECT c FROM CloudFile c WHERE c.consumer=:consumer and c.uploader=:uploader")
	List<CloudFile> findByConsumerAndUploader(Consumer consumer, Uploader uploader);
	
	@Query("SELECT u.id as uploaderId, u.displayName as uploaderDisplayName, count(f) as fileCount FROM CloudFile f JOIN f.uploader u WHERE f.createdDateTime BETWEEN :intervalloDa AND :intervalloA GROUP BY f.uploader ORDER BY u.displayName")
	List<UploaderSummary> reportCloudFileForAllUploaders(LocalDateTime intervalloDa, LocalDateTime intervalloA);
	
	@Query("SELECT u.id as uploaderId, u.displayName as uploaderDisplayName, c.id as consumerId, c.displayName as consumerDisplayName, count(f) as fileCount FROM CloudFile f JOIN f.uploader u JOIN f.consumer c WHERE f.createdDateTime BETWEEN :intervalloDa AND :intervalloA GROUP BY u.id, c.id ORDER BY u.displayName")
	List<UploaderDetailSummary> reportConsumerForAllUploaders(LocalDateTime intervalloDa, LocalDateTime intervalloA);
}