package org.abubusoft.foc.services.impl;

import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.repositories.AdministratorsRepository;
import org.abubusoft.foc.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;

@Service
public class AdminServiceImpl implements AdminService {
	
	private AdministratorsRepository repository;

	@Override
	public Administrator create(String email, String username, String password) throws FirebaseAuthException {
		CreateRequest request=new CreateRequest();
		request.setEmail(email).setPassword(password).setDisplayName(username);
		
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
		firebaseAuth.createUser(request);
		
		Administrator user = new Administrator();
		user.setDisplayName(username);
		user.setEmail(email);
		user.setUsername(username);
		return repository.save(user);
	}
	

	@Autowired
	public void setRepository(AdministratorsRepository repository) {
		this.repository = repository;
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
