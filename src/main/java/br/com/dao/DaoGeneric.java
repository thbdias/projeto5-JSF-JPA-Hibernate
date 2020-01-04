package br.com.dao;

import java.util.List;

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
	
	//apresenta o erro: detached
	public void delete(E entidade) {
		EntityManager entityManager = JpaUtil.getEntityManger();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		entityManager.remove(entidade); 
		
		transaction.commit();
		entityManager.close();
	}
	
	
	public void deletePorId(E entidade) {
		EntityManager entityManager = JpaUtil.getEntityManger();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		Object id = JpaUtil.getPrimaryKey(entidade);
		entityManager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id = " + id).executeUpdate(); //create query do JPA
		
		transaction.commit();
		entityManager.close();
	}
	
	//um datatable é sempre amarrado a uma lista
	public List<E> getListEntity(Class<E> entidade){
		EntityManager entityManager = JpaUtil.getEntityManger();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		List<E> listaRetorno = entityManager.createQuery("from " + entidade.getName()).getResultList();
		
		transaction.commit();
		entityManager.close();
		
		return listaRetorno;
	}
	
	public E consultar(Class<E> entidade, String codigo) {
		EntityManager entityManager = JpaUtil.getEntityManger();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		E objeto = (E) entityManager.find(entidade, Long.parseLong(codigo));
				
		transaction.commit();
		entityManager.close();
		
		return objeto;
	}
	
}
