package br.com.projeto5;

import javax.persistence.Persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TesteJPA {
    
	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("projeto5-JSF-JPA-Hibernate");
	}
	
}
