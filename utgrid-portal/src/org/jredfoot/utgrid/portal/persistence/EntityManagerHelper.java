package org.jredfoot.utgrid.portal.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EntityManagerHelper {
	
	private static final EntityManagerFactory INSTANCE = Persistence
			.createEntityManagerFactory("UTPORTAL_PU");

	public static EntityManagerFactory get() {
		return INSTANCE;
	}

	private EntityManagerHelper() {
	}
}
