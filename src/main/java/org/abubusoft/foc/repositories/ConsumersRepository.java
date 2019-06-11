package org.abubusoft.foc.repositories;

import org.abubusoft.foc.model.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumersRepository extends JpaRepository<Consumer, Long> {

	
}