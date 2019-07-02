package org.abubusoft.foc.business.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.abubusoft.foc.business.services.AbstractUserService;
import org.abubusoft.foc.business.services.SendMailService;
import org.abubusoft.foc.exception.AppNotFoundEntityException;
import org.abubusoft.foc.exception.AppRuntimeException;
import org.abubusoft.foc.repositories.UserRepository;
import org.abubusoft.foc.repositories.model.User;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;

public abstract class AbstractUserServiceImpl<R extends UserRepository<U>, U extends User> implements AbstractUserService<U> {

	@Override
	public void deleteAll() {
		repository.deleteAll();		
	}

	protected Logger log=Logger.getLogger(getClass());
	
	protected R repository;
	
	protected UserRepository<User> userRepository;
	
	
	protected SendMailService sendMailService;

	@Autowired
	public void setSendMailService(SendMailService sendMailService) {
		this.sendMailService = sendMailService;
	}

	@Autowired
	public void setRepository(R repository) {
		this.repository = repository;
	}
	
	@Autowired
	public void setUserRepository(UserRepository<User> userRepository) {
		this.userRepository =userRepository;
	}

	@Override
	public U insertUser(U user, String password) {
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

		CreateRequest request = new CreateRequest();
		request.setEmail(user.getUsername()).setPassword(password).setDisplayName(user.getDisplayName());

		try {
			firebaseAuth.createUser(request);			
			
			//user.setCreatedDateTime(LocalDateTime.now());
			//user.setCreatedDateTime(LocalDateTime.now());
			return repository.save(user);
		} catch (Throwable e) {
			e.printStackTrace();
			sendMailService.sendError(e);
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

			//user.setCreatedDateTime(foundUser.get().getCreatedDateTime());
			//user.setModifiedDateTime(LocalDateTime.now());
			
			firebaseAuth.updateUser(request);

			return repository.save(user);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			sendMailService.sendError(e);
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
			
			//user.setModifiedDateTime(LocalDateTime.now());
			//user.setModifiedDateTime(LocalDateTime.now());

			return repository.save(user);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			sendMailService.sendError(e);
			throw (AppRuntimeException.create(e));
		}
	}

	@Override
	public String getChangePasswordUrlById(long id) {
		Optional<User> user=this.userRepository.findById(id);
		
		if (user.isPresent()) {
			log.info("cerco "+user.get().getUsername());
			String value=getChangePasswordUrlByUsername(user.get().getUsername());
			
			return value;
		}
		
		return null;		
	}

	public String getChangePasswordUrlByUsername(String username) {
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
		return repository.findAll(Sort.by(Direction.ASC, "displayName"));
	}

	@Override
	public U findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public boolean deleteById(long id) {
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
		Optional<User> user=this.userRepository.findById(id);
		
		if (user.isPresent()) {
			try {
				UserRecord userFirebase = firebaseAuth.getUserByEmail(user.get().getUsername());
				firebaseAuth.deleteUser(userFirebase.getUid());
				
				repository.deleteById(user.get().getId());
				return true;
			} catch (FirebaseAuthException e) {
				sendMailService.sendError(e);
				e.printStackTrace();
				throw (AppRuntimeException.create(e));
			}
		}
		
		return false;
	}

	@Override
	public Optional<U> findById(long id) {
		return repository.findById(id);
	}
}
