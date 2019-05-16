package org.abubusoft.foc.repositories;

import org.abubusoft.foc.model.Administrator;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorsRepository extends PagingAndSortingRepository<Administrator, Long>, UserRepositoryCustom {

	
}