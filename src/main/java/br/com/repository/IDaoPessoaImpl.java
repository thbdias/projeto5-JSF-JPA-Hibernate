package br.com.repository;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Estados;
import br.com.entidades.Pessoa;
import br.com.jpa.util.JpaUtil;

public class IDaoPessoaImpl implements IDaoPessoa {

	@Override
	public Pessoa consultarUsuario(String login, String senha) {		
		Pessoa pessoa = null;
		
		EntityManager entityManager = JpaUtil.getEntityManger();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		try {
			pessoa = (Pessoa) entityManager.createQuery("select p from Pessoa p where p.login = '" + login + "' and p.senha = '" + senha + "'").getSingleResult();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		transaction.commit();
		entityManager.close();
		
		return pessoa;
	}
	

	@Override
	public List<SelectItem> listaEstados() {
		
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
		
		EntityManager entityManager = JpaUtil.getEntityManger();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		try {
			List<Estados> listaEstados = entityManager.createQuery("from Estados").getResultList();
			
			for (Estados estado : listaEstados) {
				selectItems.add(new SelectItem(estado, estado.getNome()));
			}
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
				
		transaction.commit();
		entityManager.close();		
		
		return selectItems;
	}

}





