package org.abubusoft.foc.repositories;

import org.abubusoft.foc.model.Administrator;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import io.swagger.annotations.Api;

@Api(tags = "Administrators Entity")
@RepositoryRestResource(path = "administrators")
public interface AdministratorsRepository extends PagingAndSortingRepository<Administrator, Long>, UserRepositoryCustom {

	
}