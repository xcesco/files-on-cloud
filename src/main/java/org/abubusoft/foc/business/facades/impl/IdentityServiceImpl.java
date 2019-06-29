package org.abubusoft.foc.business.facades.impl;

import java.util.HashSet;
import java.util.Set;

import org.abubusoft.foc.business.services.AuthService;
import org.abubusoft.foc.exception.AppRuntimeException;
import org.abubusoft.foc.repositories.GenericUserRepository;
import org.abubusoft.foc.repositories.model.User;
import org.abubusoft.foc.web.security.ng.JwtService;
import org.abubusoft.foc.web.security.ng.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;

@Service
public class IdentityServiceImpl implements AuthService {
	
	@Autowired
	private JwtService jwtService;

	public void setJwtService(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Autowired
	private GenericUserRepository repository;

	public void setUserRepository(GenericUserRepository repository) {
		this.repository = repository;
	}

	@Override
	public User findByUsername(String email) {
		try {
			return repository.findByUsername(email);
		} catch (EmptyResultDataAccessException nre) {
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

	@Override
	public void deleteAllUsers() {
		FirebaseAuth firebase = FirebaseAuth.getInstance();

		Set<String> uidToDelete = new HashSet<>();
		ListUsersPage list;
		try {
			list = firebase.listUsers(null);

			while (list != null) {
				for (ExportedUserRecord item : list.getValues()) {
					// log.info(String.format("Select %s (%s) to delete ", item.getEmail(),
					// item.getUid()));
					uidToDelete.add(item.getUid());
				}

				list = list.getNextPage();
			}

			for (String uid : uidToDelete) {
				firebase.deleteUser(uid);
			}

			repository.deleteAllInBatch();
		} catch (Throwable e) {
			e.printStackTrace();
			throw AppRuntimeException.create(e);
		}

	}

	@Override
	public String generateToken(String username) {
		User user = findByUsername(username);
		String token=jwtService.generateToken(JwtUserFactory.create(user));
		return token;
	}
}