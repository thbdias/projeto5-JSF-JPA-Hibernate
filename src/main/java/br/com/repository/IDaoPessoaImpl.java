package br.com.repository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Pessoa;
import br.com.jpa.util.JpaUtil;

@Named //indica que esta classe irá usar injeção de dependencia cdi
public class IDaoPessoaImpl implements IDaoPessoa {
	
	@Inject
	private EntityManager entityManager;

	@Override
	public Pessoa consultarUsuario(String login, String senha) {
		
		Pessoa pessoa = null;
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		try {
			pessoa = (Pessoa) entityManager.createQuery("select p from Pessoa p where p.login = '" + login + "' and p.senha = '" + senha + "'").getSingleResult();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		transaction.commit();
		
		return pessoa;
	}

}
