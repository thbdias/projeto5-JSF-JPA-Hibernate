package br.com.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Estados;
import br.com.jpa.util.JpaUtil;

@FacesConverter(forClass = Estados.class)
public class EstadoConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	//function utilizada quando for salvar - tela para servidor
	//vem da tela o id e salva no banco o objeto
	@Override //retorna objeto inteiro
	public Object getAsObject(FacesContext context, UIComponent component, String codigoEstado) {
		
		EntityManager entityManager = JpaUtil.getEntityManger();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		Estados estado = (Estados) entityManager.find(Estados.class, Long.parseLong(codigoEstado));
		
		return estado;
	}

	//function é executada quando o objeto vem do servidor para a tela
	//vem do servidor o objeto e manda pra tela o id
	@Override //retorna apenas o codigo em string
	public String getAsString(FacesContext context, UIComponent component, Object estado) {		
		return ((Estados) estado).getId().toString();
	}

}
