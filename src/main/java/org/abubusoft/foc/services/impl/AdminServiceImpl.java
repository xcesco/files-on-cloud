package org.abubusoft.foc.services.impl;

import org.abubusoft.foc.model.Administrator;
import org.abubusoft.foc.repositories.AdministratorsRepository;
import org.abubusoft.foc.services.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends AbstractUserServiceImpl<AdministratorsRepository, Administrator> implements AdminService {

}
