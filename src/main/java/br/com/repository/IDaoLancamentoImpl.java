package br.com.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Lancamento;
import br.com.jpa.util.JpaUtil;

@Named //indica que esta classe irá usar injeção de dependencia cdi
public class IDaoLancamentoImpl implements IDaoLancamento {

	@Inject
	private EntityManager entityManager;
	
	@Override
	public List<Lancamento> consultar(Long codUser) {
		
		List<Lancamento> lista = null;
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lista = entityManager.createQuery("from Lancamento where usuario.id = " + codUser).getResultList();
		
		transaction.commit();
		
		return lista;
	}

}
