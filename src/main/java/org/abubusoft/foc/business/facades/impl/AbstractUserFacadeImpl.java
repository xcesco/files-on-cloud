package org.abubusoft.foc.business.facades.impl;

import org.abubusoft.foc.business.facades.AbstractUserFacade;
import org.abubusoft.foc.business.services.AbstractUserService;
import org.abubusoft.foc.web.model.ChangePasswordWto;
import org.abubusoft.foc.web.model.UserWto;
import org.abubusoft.foc.web.support.WtoMapper;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractUserFacadeImpl<W extends UserWto, S extends AbstractUserService<?>> implements AbstractUserFacade<W> {
	
	protected Logger log=Logger.getLogger(getClass());
	
	protected WtoMapper mapper = WtoMapper.INSTANCE;
	
	protected S service;

	@Autowired
	public void setService(S service) {
		this.service = service;
	}

	@Override
	public boolean deleteById(long id) {
		return service.deleteById(id);
	}

	@Override
	public ChangePasswordWto getChangePasswordUrlById(long id) {
		String url=service.getChangePasswordUrlById(id);
		
		return new ChangePasswordWto(url);
	}

}
