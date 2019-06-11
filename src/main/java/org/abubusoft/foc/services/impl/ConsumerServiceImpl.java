package org.abubusoft.foc.services.impl;

import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.model.Consumer;
import org.abubusoft.foc.repositories.ConsumersRepository;
import org.abubusoft.foc.services.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;

@Service
public class ConsumerServiceImpl implements ConsumerService {

	private ConsumersRepository repository;

	@Autowired
	public void setRepository(ConsumersRepository repository) {
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

	@Override
	public Consumer create(String email, String username, String password, String codiceFiscale)
			throws FirebaseAuthException {
		CreateRequest request = new CreateRequest();
		request.setEmail(email).setPassword(password).setDisplayName(username);

		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
		firebaseAuth.createUser(request);

		Consumer user = new Consumer();
		user.setDisplayName(username);
		user.setEmail(email);
		user.setUsername(username);
		user.setCodiceFiscale(codiceFiscale);
		return repository.save(user);

	}

}
