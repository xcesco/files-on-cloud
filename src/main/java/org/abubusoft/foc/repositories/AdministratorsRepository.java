package org.abubusoft.foc.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.abubusoft.foc.repositories.model.Administrator;
import org.abubusoft.foc.repositories.model.UploaderDetailSummary;
import org.abubusoft.foc.repositories.model.UploaderSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorsRepository extends UserRepository<Administrator> {

	@Query("SELECT u.id as uploaderId, u.displayName as uploaderDisplayName, count(f) as fileCount FROM CloudFile f JOIN f.uploader u WHERE f.createdDateTime BETWEEN :intervalloDa AND :intervalloA GROUP BY f.uploader ORDER BY u.displayName")
	List<UploaderSummary> reportCloudFileForAllUploaders(LocalDateTime intervalloDa, LocalDateTime intervalloA);
	
	@Query("SELECT u.id as uploaderId, u.displayName as uploaderDisplayName, c.id as consumerId, c.displayName as consumerDisplayName, count(f) as fileCount FROM CloudFile f JOIN f.uploader u JOIN f.consumer c WHERE f.createdDateTime BETWEEN :intervalloDa AND :intervalloA GROUP BY u.id, c.id ORDER BY u.displayName")
	List<UploaderDetailSummary> reportConsumerForAllUploaders(LocalDateTime intervalloDa, LocalDateTime intervalloA);
		
}