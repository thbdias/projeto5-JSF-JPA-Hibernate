package br.com.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.jpa.util.JpaUtil;

public class DaoGeneric<E> {

	public void salvar(E entidade) {
		EntityManager entityManager = JpaUtil.getEntityManger();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		entityManager.persist(entidade); //apenas salva no BD e não retorna nada
		
		transaction.commit();
		entityManager.close();
	}
	
	public E merge(E entidade) {
		EntityManager entityManager = JpaUtil.getEntityManger();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		E objetoGravado = entityManager.merge(entidade); //salva ou atualiza e retorna o objeto salvo no BD		
		
		transaction.commit();
		entityManager.close();
		
		return objetoGravado;
	}
	
}
