package org.abubusoft.foc.business.facades.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.abubusoft.foc.business.facades.UploaderFacade;
import org.abubusoft.foc.business.services.UploaderService;
import org.abubusoft.foc.repositories.model.Uploader;
import org.abubusoft.foc.web.model.UploaderWto;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class UploaderServiceFacadeImpl extends AbstractUserFacadeImpl<UploaderWto, UploaderService> implements UploaderFacade {

	
	
	@Override
	public UploaderWto save(UploaderWto value) {
		Uploader user = mapper.convertUploaderToDto(value);
		Uploader result;

		if (user.getId() == null) {
			result = service.insertUser(user, value.getPassword());
		} else {
			result = service.updateById(user);
		}

		return mapper.convertUploaderToWto(result);
	}

	@Override
	public List<UploaderWto> findAll() {
		return mapper.convertUploaderListToDto(service.findAll());
	}

	@Override
	public byte[] getLogoById(long id) {
		Optional<Uploader> result = service.findById(id);

		if (result.isPresent() && result.get().getImage()!=null) {
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
	    ClassPathResource resource = new ClassPathResource("images/user.png");
	    log.info(resource.getFile().getAbsoluteFile().toString());
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
