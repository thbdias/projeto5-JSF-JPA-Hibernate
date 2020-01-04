package br.com.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Cidades;
import br.com.entidades.Estados;
import br.com.jpa.util.JpaUtil;

//value cidadeConverter -> utilizado na tela
@FacesConverter(forClass = Cidades.class, value = "cidadeConverter")
public class CidadeConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	//function utilizada quando for salvar - tela para servidor
	//vem da tela o id e salva no banco o objeto
	@Override //retorna objeto inteiro
	public Object getAsObject(FacesContext context, UIComponent component, String codigoCidade) {
		
		EntityManager entityManager = JpaUtil.getEntityManger();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		Cidades cidade = (Cidades) entityManager.find(Cidades.class, Long.parseLong(codigoCidade));
		
		return cidade;
	}

	//function é executada quando o objeto vem do servidor para a tela
	//vem do servidor o objeto e manda pra tela o id
	@Override //retorna apenas o codigo em string
	public String getAsString(FacesContext context, UIComponent component, Object cidade) {	
		
		if (cidade == null ) return null;
		
		if (cidade instanceof Cidades) 
			return ((Cidades) cidade).getId().toString();
		else 
			return cidade.toString();
	}

}
