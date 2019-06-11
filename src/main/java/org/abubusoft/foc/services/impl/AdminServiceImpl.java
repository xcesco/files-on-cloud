package org.abubusoft.foc.services.impl;

import org.abubusoft.foc.repositories.AdministratorsRepository;
import org.abubusoft.foc.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
	
	private AdministratorsRepository repository;
	
	private 

	@Autowired
	public void setRepository(AdministratorsRepository repository) {
		this.repository = repository;
	}

	@Override
	public void create(String email, String username, String password) {

		
	}

	@Override
	public void update(String email, String displayName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePassword(String email) {
		// TODO Auto-generated method stub
		
	}

}
