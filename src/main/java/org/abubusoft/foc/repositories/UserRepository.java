package org.abubusoft.foc.repositories;

import org.abubusoft.foc.repositories.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository<U extends User> extends PagingAndSortingRepository<U, Long> {

	U findByUsername(String username);

	int deleteByUsername(String username);
}
