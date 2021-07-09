package com.census.censusapp.utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtility {
	private EntityManagerUtility() {
		throw new AssertionError();
		// Enforce noninstantiability with a private constructor
		// Joshua Bloch , Effective Java Item 04
	}

	// postgres is the name of database and this is also present in persitance.xml
	private static EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("censusspring");
	
	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

}
