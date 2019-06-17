package org.abubusoft.foc.services.impl;

import java.util.List;
import java.util.Optional;

import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.services.UploaderService;
import org.abubusoft.foc.services.UploaderServiceFacade;
import org.abubusoft.foc.web.model.UploaderWto;
import org.abubusoft.foc.web.support.WtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploaderServiceFacadeImpl implements UploaderServiceFacade {

	private WtoMapper mapper = WtoMapper.INSTANCE;

	private UploaderService uploaderService;

	@Autowired
	public void setUploaderService(UploaderService uploaderService) {
		this.uploaderService = uploaderService;
	}

	@Override
	public UploaderWto createUploader(UploaderWto value) {
		Uploader user = mapper.convertUploaderToDto(value);
		Uploader result = uploaderService.createUser(user, value.getPassword());

		return mapper.convertUploaderToWto(result);
	}

	@Override
	public boolean deleteById(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String generateChangePasswordUrl(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UploaderWto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UploaderWto updateById(UploaderWto value) {
		Uploader user = mapper.convertUploaderToDto(value);
		Uploader result = uploaderService.updateById(user);

		return mapper.convertUploaderToWto(result);
	}

	@Override
	public byte[] getLogoById(long id) {
		Optional<Uploader> result = uploaderService.findById(id);

		if (result.isPresent()) {
			return result.get().getImage();
		}

		return null;
	}

	@Override
	public UploaderWto findById(long uploaderId) {
		Optional<Uploader> result = uploaderService.findById(uploaderId);

		if (result.isPresent()) {
			return mapper.convertUploaderToWto(result.get());
		}

		return null;

	}

}
