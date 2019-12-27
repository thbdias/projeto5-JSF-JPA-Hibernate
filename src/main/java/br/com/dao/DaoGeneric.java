package br.com.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.jpa.util.JpaUtil;

public class DaoGeneric<E> {

	public void salvar(E entidade) {
		EntityManager entityManager = JpaUtil.getEntityManger();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		entityManager.persist(entidade); //salvar no BD
		
		transaction.commit();
		entityManager.close();
	}
	
}
