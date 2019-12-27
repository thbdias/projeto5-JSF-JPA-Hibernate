package br.com.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlCommandButton;

import br.com.dao.DaoGeneric;
import br.com.entidades.Pessoa;

//classe responsável por tratar uma página jsf
@ViewScoped
@ManagedBean(name = "beanPessoa")
public class BeanPessoa {

	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();

	// método que será utilizado pela tela jsf
	public String salvar() {
		//daoGeneric.salvar(pessoa); //apenas salva no BD e não retorna nada
		pessoa = daoGeneric.merge(pessoa); //salva ou atualiza e retorna o objeto salvo no BD
		
		return "";
	}
	
	public String novo() {
		pessoa = new Pessoa();
		return "";
	}
	

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

}