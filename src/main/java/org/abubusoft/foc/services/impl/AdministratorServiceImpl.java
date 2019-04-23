package org.abubusoft.foc.services.impl;

import java.util.List;

import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.repositories.AdministratorsRepository;
import org.abubusoft.foc.services.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {

	@Autowired
	private AdministratorsRepository repository;

	public void setRepository(AdministratorsRepository repository) {
		this.repository = repository;
	}

	public List<Administrator> findAll() {
		return repository.findAll();
	}

	@Override
	public void add(Administrator value) {
		repository.save(value);

	}
}