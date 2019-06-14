package org.abubusoft.foc.services.impl;

import org.abubusoft.foc.exception.AppRuntimeException;
import org.abubusoft.foc.model.User;
import org.abubusoft.foc.repositories.GenericUserRepository;
import org.abubusoft.foc.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private GenericUserRepository repository;

	public void setUserRepository(GenericUserRepository repository) {
		this.repository = repository;
	}

	@Override
	public User findByUsername(String email) {
		try {
			return repository.findByUsername(email);
		} catch(EmptyResultDataAccessException nre) {
			return null;
		}				
	}
	
	@Override
	public void deleteByUsername(String username) {
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
		
		UserRecord user;
		try {
			user = firebaseAuth.getUserByEmail(username);
			firebaseAuth.deleteUser(user.getUid());
			
			repository.deleteByUsername(username);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			throw (AppRuntimeException.create(e));
		}
		
	}
}