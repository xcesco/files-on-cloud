package org.abubusoft.foc.services.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.repositories.UploadersRepository;
import org.abubusoft.foc.services.UploaderService;
import org.springframework.stereotype.Service;

@Service
public class UploaderServiceImpl  extends AbstractUserServiceImpl<UploadersRepository, Uploader> implements UploaderService  {

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
	public Uploader updateUploaderLogo(String username, InputStream inputStream) {
		Uploader user = repository.findByUsername(username);
		
		user.setImage(getImage(inputStream));
		
		repository.save(user);
		
		return user;
	}


}
