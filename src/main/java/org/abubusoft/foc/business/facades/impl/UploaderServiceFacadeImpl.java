package org.abubusoft.foc.business.facades.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.abubusoft.foc.business.facades.UploaderFacade;
import org.abubusoft.foc.business.services.UploaderService;
import org.abubusoft.foc.exception.AppRuntimeException;
import org.abubusoft.foc.repositories.model.Uploader;
import org.abubusoft.foc.web.model.UploaderWto;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class UploaderServiceFacadeImpl extends AbstractUserFacadeImpl<UploaderWto, UploaderService>
		implements UploaderFacade {

	@Override
	public UploaderWto save(UploaderWto value) {
		Uploader user = mapper.convertUploaderToDto(value);
		Uploader result = null;

		if (user.getId() == null) {
			try {
				user.setImage(IOUtils.toByteArray(loadDefaultLogo().getInputStream()));
				result = service.insertUser(user, value.getPassword());
			} catch (IOException e) {
				e.printStackTrace();
				throw AppRuntimeException.create(e);
			}
		} else {
			// workaround per il logo
			Uploader oldUser = service.findById(user.getId()).get();
			user.setImage(oldUser.getImage());
			result = service.updateById(user);
		}

		return mapper.convertUploaderToWto(result);
	}

	@Override
	public List<UploaderWto> findAll() {
		logger.debug("findAll");
		return mapper.convertUploaderListToDto(service.findAll());
	}

	@Override
	public byte[] getLogoById(long id) {
		Optional<Uploader> result = service.findById(id);

		if (result.isPresent() && result.get().getImage() != null) {
			return result.get().getImage();
		}

		// carichiamo logo di default
		try {
			return IOUtils.toByteArray(loadDefaultLogo().getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Resource loadDefaultLogo() throws IOException {
		ClassPathResource resource = new ClassPathResource("images/noLogo.png");
		logger.info(resource.getFile().getAbsoluteFile().toString());
		return resource;
	}

	@Override
	public UploaderWto findById(long uploaderId) {
		Optional<Uploader> result = service.findById(uploaderId);

		if (result.isPresent()) {
			return mapper.convertUploaderToWto(result.get());
		}

		return null;
	}

	@Override
	public List<UploaderWto> findByConsumer(long consumerId) {
		logger.debug("findByConsumer " + consumerId);
		return mapper.convertUploaderListToDto(service.findByConsumer(consumerId));
	}

	@Override
	public UploaderWto create() {
		UploaderWto result = new UploaderWto();

		prepareData(result);

		return result;
	}

	@Override
	public boolean saveLogo(long id, InputStream inputStream) {
		Optional<Uploader> result = service.findById(id);

		if (result.isPresent()) {
			service.updateUploaderLogo(result.get().getUsername(), inputStream);
			return true;
		}

		return false;

	}

}
