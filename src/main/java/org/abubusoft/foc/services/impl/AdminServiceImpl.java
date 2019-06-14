package org.abubusoft.foc.services.impl;

import org.abubusoft.foc.exception.AppRuntimeException;
import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.repositories.AdministratorsRepository;
import org.abubusoft.foc.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;

@Service
public class AdminServiceImpl implements AdminService {

	private AdministratorsRepository repository;

	@Override
	public Administrator createAdministrator(String username, String email, String displayName, String password) {
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

		CreateRequest request = new CreateRequest();
		request.setEmail(username).setPassword(password).setDisplayName(displayName);

		try {
			firebaseAuth.createUser(request);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			throw (AppRuntimeException.create(e));
		}

		Administrator user = new Administrator();
		user.setDisplayName(displayName);
		user.setEmail(email);
		user.setUsername(username);
		return repository.save(user);
	}

	@Autowired
	public void setRepository(AdministratorsRepository repository) {
		this.repository = repository;
	}

	/**
	 * Recuperiamo mediante username l'utente ed aggiorniamo relativi email e
	 * displayname.
	 */
	@Override
	public Administrator updateAdministratorByUsername(String username, String email, String displayName) {		
		try {
			FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
			
			UserRecord userAuth = firebaseAuth.getUserByEmail(username);
			UpdateRequest request = new UpdateRequest(userAuth.getUid());
			request.setDisplayName(displayName);

			firebaseAuth.updateUser(request);

			Administrator userApp = repository.findByUsername(username);
			userApp.setDisplayName(username);
			userApp.setEmail(email);
			userApp.setUsername(username);
			return repository.save(userApp);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			throw (AppRuntimeException.create(e));
		}
	}

	@Override
	public int deleteByUsername(String username) {				
		try {
			UserRecord user;
			FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
			
			user = firebaseAuth.getUserByEmail(username);
			firebaseAuth.deleteUser(user.getUid());

			return repository.deleteByUsername(username);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			throw (AppRuntimeException.create(e));
		}
	}

	@Override
	public Iterable<Administrator> findAll() {
		return repository.findAll();
	}

	@Override
	public String generateChangePasswordUrl(String username) {
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
		try {
			//UserRecord userAuth = firebaseAuth.getUserByEmail(email);
			String url = firebaseAuth.generatePasswordResetLink(username);
			return url;
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			throw (AppRuntimeException.create(e));
		}
	}

}
