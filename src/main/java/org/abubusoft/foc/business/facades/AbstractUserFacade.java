package org.abubusoft.foc.business.facades;

import java.util.List;

import org.abubusoft.foc.web.model.ChangePasswordWto;
import org.abubusoft.foc.web.model.UserWto;

public interface AbstractUserFacade<W extends UserWto> {
	
	W create();
	
	List<W> findAll();
	
	W findById(long id);

	boolean deleteById(long id);

	W save(W bean);
	
	ChangePasswordWto getChangePasswordUrlById(long id);

}
