package org.abubusoft.foc.business.facades.impl;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import org.abubusoft.foc.business.facades.UploaderFacade;
import org.abubusoft.foc.business.services.UploaderService;
import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.web.model.UploaderWto;
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

		if (result.isPresent()) {
			return result.get().getImage();
		}

		return null;
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

		return result;
	}

	@Override
	public boolean saveLogo(long id, byte[] byteArray) {
		Optional<Uploader> result = service.findById(id);

		if (result.isPresent()) {
			service.updateUploaderLogo(result.get().getUsername(), new ByteArrayInputStream(byteArray));
			return true;
		}

		return false;
		
	}


}
