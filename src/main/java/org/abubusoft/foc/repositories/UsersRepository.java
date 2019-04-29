package org.abubusoft.foc.repositories;

import org.abubusoft.foc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
	
	@Query("select c from User as c where email=:email")
	User findByEmail(String email);
}