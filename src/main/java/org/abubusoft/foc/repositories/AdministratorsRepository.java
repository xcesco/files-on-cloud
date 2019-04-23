package org.abubusoft.foc.repositories;

import org.abubusoft.foc.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorsRepository extends JpaRepository<Administrator, Long> {
    // ...
}