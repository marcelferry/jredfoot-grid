package org.jredfoot.utgrid.common.persistence.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EMF {
	
	private static final EntityManagerFactory INSTANCE = Persistence
			.createEntityManagerFactory("Gerente");

	public static EntityManagerFactory get() {
		return INSTANCE;
	}

	private EMF() {
	}
}
