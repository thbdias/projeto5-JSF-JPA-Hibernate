package br.com.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.jpa.util.JpaUtil;

@Named //indica que esta classe irá usar injeção de dependencia cdi
public class DaoGeneric<E> {
	
	@Inject
	private EntityManager entityManager;	
	
	@Inject
	private JpaUtil jpaUtil;
	
	
	
	
	public void salvar(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		entityManager.persist(entidade); //apenas salva no BD e não retorna nada
		
		transaction.commit();
	}
	
	public E merge(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		E objetoGravado = entityManager.merge(entidade); //salva ou atualiza e retorna o objeto salvo no BD		
		
		transaction.commit();
		
		return objetoGravado;
	}
	
	//apresenta o erro: detached
	public void delete(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		entityManager.remove(entidade); 
		
		transaction.commit();
	}
	
	
	public void deletePorId(E entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		Object id = jpaUtil.getPrimaryKey(entidade);
		entityManager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id = " + id).executeUpdate(); //create query do JPA
		
		transaction.commit();
	}
	
	//um datatable é sempre amarrado a uma lista
	public List<E> getListEntity(Class<E> entidade){
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		List<E> listaRetorno = entityManager.createQuery("from " + entidade.getName()).getResultList();
		
		transaction.commit();
		
		return listaRetorno;
	}
	
}
