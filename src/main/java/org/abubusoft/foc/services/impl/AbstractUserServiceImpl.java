package org.abubusoft.foc.services.impl;

import java.util.Optional;

import org.abubusoft.foc.exception.AppNotFoundEntityException;
import org.abubusoft.foc.exception.AppRuntimeException;
import org.abubusoft.foc.model.User;
import org.abubusoft.foc.repositories.UserRepository;
import org.abubusoft.foc.services.AbstractUserService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;

public class AbstractUserServiceImpl<R extends UserRepository<U>, U extends User> implements AbstractUserService<U> {

	protected Logger log=Logger.getLogger(getClass());
	
	protected R repository;

	@Autowired
	public void setRepository(R repository) {
		this.repository = repository;
	}

	@Override
	public U createUser(U user, String password) {
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

		CreateRequest request = new CreateRequest();
		request.setEmail(user.getUsername()).setPassword(password).setDisplayName(user.getDisplayName());

		try {
			firebaseAuth.createUser(request);
			
			return repository.save(user);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			throw (AppRuntimeException.create(e));
		}

		
	}

	@Override
	public U updateById(U user) {
		try {
			Optional<U> foundUser=repository.findById(user.getId());
			if (foundUser.isPresent()) {
				
			} else {
				throw (AppRuntimeException.create(AppNotFoundEntityException.class));
			}
			
			FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

			UserRecord userAuth = firebaseAuth.getUserByEmail(user.getUsername());
			UpdateRequest request = new UpdateRequest(userAuth.getUid());
			request.setDisplayName(user.getDisplayName());

			firebaseAuth.updateUser(request);

			return repository.save(user);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			throw (AppRuntimeException.create(e));
		}
	}

	@Override
	public U updateByUsername(U user) {
		try {
			FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

			UserRecord userAuth = firebaseAuth.getUserByEmail(user.getUsername());
			UpdateRequest request = new UpdateRequest(userAuth.getUid());
			request.setDisplayName(user.getDisplayName());

			firebaseAuth.updateUser(request);

			return repository.save(user);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			throw (AppRuntimeException.create(e));
		}
	}

	@Override
	public int deleteByUsername(String username) {
		return repository.deleteByUsername(username);
	}

	@Override
	public String generateChangePasswordUrl(String username) {
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
		try {
			// UserRecord userAuth = firebaseAuth.getUserByEmail(email);
			String url = firebaseAuth.generatePasswordResetLink(username);
			return url;
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			throw (AppRuntimeException.create(e));
		}
	}

	@Override
	public Iterable<U> findAll() {
		return repository.findAll();
	}

	@Override
	public U findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public boolean deleteById(long id) {
		repository.deleteById(id);
		
		return true;
	}

	@Override
	public Optional<U> findById(long id) {
		return repository.findById(id);
	}
}
