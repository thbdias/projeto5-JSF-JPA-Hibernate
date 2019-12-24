package br.com.projeto5;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlCommandButton;

//@RequestScoped 
@ViewScoped
//@SessionScoped
//@ApplicationScoped
@ManagedBean(name = "beanPessoa")
public class BeanPessoa {

	private String nome;
	private String senha;
	private String texto;
	private List<String> nomes = new ArrayList<String>();
	private HtmlCommandButton commandButton;
	
	
	
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public HtmlCommandButton getCommandButton() {
		return commandButton;
	}

	public void setCommandButton(HtmlCommandButton commandButton) {
		this.commandButton = commandButton;
	}

	public void setNomes(List<String> nomes) {
		this.nomes = nomes;
	}
	
	public List<String> getNomes() {
		return nomes;
	}

	public String addNome() {
		nomes.add(nome);
		
		if (nomes.size() > 3) {
			commandButton.setDisabled(true);
			return "paginanavegada?faces-redirect=true"; //navegação dinâmica - redirecinamento de URL
		}
		
		return ""; // null ou vazio fica na mesma página -> outcome
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


}