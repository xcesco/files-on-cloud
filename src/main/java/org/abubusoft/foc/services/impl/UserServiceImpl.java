package org.abubusoft.foc.services.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.model.User;
import org.abubusoft.foc.repositories.AdministratorsRepository;
import org.abubusoft.foc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

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

	@Override
	public User findByEmail(String email) {
		try {
			return repository.findByEmail(email);
		} catch(EmptyResultDataAccessException nre) {
			return null;
		}
		
		
	}
}