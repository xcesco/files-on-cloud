package org.abubusoft.foc.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.abubusoft.foc.model.User;
import org.abubusoft.foc.repositories.UserRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public User findByEmail(String email) {		
			TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User as u where u.email=:email",
					User.class);
			query.setParameter("email", email);
			return query.getSingleResult();
		
//		 CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//
//		  CriteriaQuery<User> q = cb.createQuery(User.class);
//		  q.where(restriction)
//		 // Root<User> c = q.from(User.class);
//		  TypedQuery<User> query = entityManager.createQuery(q);
//		  List<User> results = query.getResultList();
//		  
//		  return results;
		// return q.getResultList();

	}

}
