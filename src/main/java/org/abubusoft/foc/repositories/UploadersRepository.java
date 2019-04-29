package org.abubusoft.foc.repositories;

import org.abubusoft.foc.model.Uploader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadersRepository extends JpaRepository<Uploader, Long>, UserRepositoryCustom {

	
}