package org.abubusoft.foc.repositories;

import org.abubusoft.foc.repositories.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericUserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	@Modifying
	@Query("delete from User where username=?1")
	int deleteByUsername(String username);
	
}