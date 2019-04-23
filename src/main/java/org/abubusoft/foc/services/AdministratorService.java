package org.abubusoft.foc.services;

import java.util.List;

import org.abubusoft.foc.model.Administrator;

public interface AdministratorService {

	List<Administrator> findAll();

	void add(Administrator value);
	
}