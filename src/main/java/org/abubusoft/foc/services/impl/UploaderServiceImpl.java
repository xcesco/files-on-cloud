package org.abubusoft.foc.services.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.abubusoft.foc.exception.AppRuntimeException;
import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.repositories.UploadersRepository;
import org.abubusoft.foc.services.UploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;

@Service
public class UploaderServiceImpl implements UploaderService {
	protected UploadersRepository repository;
	
	@Autowired
	public void setRepository(UploadersRepository uploaderRepository) {
		this.repository = uploaderRepository;
	}

	public static byte[] getImage(InputStream inputStream) {
		try {
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", byteOutStream);
			return byteOutStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Uploader createUploader(String username, String email, String displayName, String password, InputStream inputStream) {
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

		CreateRequest request = new CreateRequest();
		request.setEmail(username).setPassword(password).setDisplayName(displayName);

		try {
			firebaseAuth.createUser(request);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
			throw (AppRuntimeException.create(e));
		}
		
		Uploader user = new Uploader();		
		user.setEmail(email);
		user.setUsername(username);
		user.setImage(getImage(inputStream));
		user.setDisplayName(displayName);
		
		return repository.save(user);
	}

	@Override
	public Uploader updateUploaderByUsername(String username, String email, String displayName) {
		try {
			FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
			
			UserRecord userAuth = firebaseAuth.getUserByEmail(username);
			UpdateRequest request = new UpdateRequest(userAuth.getUid());
			request.setDisplayName(displayName);

			firebaseAuth.updateUser(request);

			Uploader userApp = repository.findByUsername(username);
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
	public String generateChangePasswordUrl(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Uploader> findAll() {
		return repository.findAll();
	}

	@Override
	public Uploader updateUploaderLogo(String username, InputStream inputStream) {
		Uploader user = repository.findByUsername(username);
		
		user.setImage(getImage(inputStream));
		
		repository.save(user);
		
		return user;
	}


}
