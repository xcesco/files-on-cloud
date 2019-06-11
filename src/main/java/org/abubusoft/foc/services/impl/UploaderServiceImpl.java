package org.abubusoft.foc.services.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.repositories.UploadersRepository;
import org.abubusoft.foc.services.UploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;

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
	public void update(String email, String displayName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePassword(String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Uploader create(String email, String username, String password, InputStream inputStream)
			throws FirebaseAuthException {
		CreateRequest request=new CreateRequest();
		request.setEmail(email).setPassword(password).setDisplayName(username);
		
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
		firebaseAuth.createUser(request);
		
		Uploader user = new Uploader();
		user.setDisplayName(username);
		user.setEmail(email);
		user.setUsername(username);
		user.setImage(getImage(inputStream));
		
		return repository.save(user);
		
	}


}
