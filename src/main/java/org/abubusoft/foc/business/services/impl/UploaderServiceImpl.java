package org.abubusoft.foc.business.services.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import javax.imageio.ImageIO;

import org.abubusoft.foc.business.services.UploaderService;
import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.model.UploaderSummary;
import org.abubusoft.foc.repositories.UploadersRepository;
import org.springframework.stereotype.Service;

@Service
public class UploaderServiceImpl extends AbstractUserServiceImpl<UploadersRepository, Uploader>
		implements UploaderService {

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

	@Override
	public List<Uploader> findByConsumer(long consumerId) {
		return repository.findByConsumer(consumerId);
	}


}
