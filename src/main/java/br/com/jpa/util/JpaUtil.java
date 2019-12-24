package br.com.jpa.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
	
	private static EntityManagerFactory factory = null;
	
	static {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("projeto5-JSF-JPA-Hibernate");
		}
	}

}
