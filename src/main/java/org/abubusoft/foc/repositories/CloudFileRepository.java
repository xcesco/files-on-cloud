package org.abubusoft.foc.repositories;

import org.abubusoft.foc.model.CloudFile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudFileRepository extends PagingAndSortingRepository<CloudFile, Long> {

	
}