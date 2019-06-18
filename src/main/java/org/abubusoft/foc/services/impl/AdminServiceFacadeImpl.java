package org.abubusoft.foc.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.model.Uploader;
import org.abubusoft.foc.model.UploaderDetailSummary;
import org.abubusoft.foc.model.UploaderSummary;
import org.abubusoft.foc.services.AdminServiceFacade;
import org.abubusoft.foc.services.UploaderService;
import org.abubusoft.foc.services.AdminService;
import org.abubusoft.foc.web.model.AdminWto;
import org.abubusoft.foc.web.model.UploaderWto;
import org.abubusoft.foc.web.support.WtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceFacadeImpl implements AdminServiceFacade {

	private WtoMapper mapper = WtoMapper.INSTANCE;

	private AdminService adminService;

	private UploaderService uploaderService;

	@Autowired
	public void setUploaderService(UploaderService uploaderService) {
		this.uploaderService = uploaderService;
	}

	@Autowired
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	@Override
	public AdminWto saveAdmin(@Valid AdminWto value) {
		Administrator user = mapper.convertAdminToDto(value);
		Administrator result;
		
		if (user.getId() == null) {
			result = adminService.insertUser(user, value.getPassword());
		} else {
			result = adminService.updateById(user);
		}

		return mapper.convertAdminToWto(result);
	}

	@Override
	public boolean deleteAdminById(long id) {
		return adminService.deleteById(id);
	}

	@Override
	public List<AdminWto> findAllAdmin() {
		return mapper.convertAdminListToWto(adminService.findAll());
	}

	@Override
	public List<UploaderSummary> reportCloudFileForAllUploaders(LocalDate validoDal, LocalDate validoAl) {
		return uploaderService.reportCloudFileForAllUploaders(validoDal, validoAl);
	}

	@Override
	public List<UploaderDetailSummary> reportConsumerForAllUploaders(LocalDate validoDal, LocalDate validoAl) {
		return uploaderService.reportConsumerForAllUploaders(validoDal, validoAl);
	}

	@Override
	public UploaderWto updateUploader(@Valid UploaderWto value) {
		Uploader user = mapper.convertUploaderToDto(value);

		Uploader result;
		if (user.getId() == null) {
			result = uploaderService.insertUser(user, value.getPassword());
		} else {
			result = uploaderService.updateById(user);
		}

		return mapper.convertUploaderToWto(result);
	}

	@Override
	public boolean deleteUploaderById(long id) {
		return uploaderService.deleteById(id);
	}

	@Override
	public List<UploaderWto> findAllUploaders() {
		return mapper.convertUploaderListToWto(uploaderService.findAll());
	}

	@Override
	public String getChangePasswordUrlByUsername(String username) {
		return adminService.getChangePasswordUrlByUsername(username);
	}

	@Override
	public AdminWto createAdministrator() {
		AdminWto result = new AdminWto();

		return result;
	}

	@Override
	public AdminWto findAdminById(long id) {
		Optional<Administrator> value = adminService.findById(id);

		if (value.isPresent()) {
			return mapper.convertAdminToWto(value.get());
		}
		return null;
	}

	@Override
	public UploaderWto createUploader() {
		UploaderWto result = new UploaderWto();

		return result;
	}
}
