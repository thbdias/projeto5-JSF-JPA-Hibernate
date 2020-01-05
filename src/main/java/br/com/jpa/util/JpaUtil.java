package br.com.jpa.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped //package do cdi -> uma instância para a aplicacao inteira
public class JpaUtil {
	
	private EntityManagerFactory factory = null;
	
	public JpaUtil() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("projeto5-JSF-JPA-Hibernate");
		}
	}	
	
	@Produces
	@RequestScoped
	public EntityManager getEntityManger() {
		return factory.createEntityManager();
	}	
	
	public Object getPrimaryKey(Object entity) {
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}

}
