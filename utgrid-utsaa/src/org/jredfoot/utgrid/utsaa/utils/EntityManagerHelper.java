package org.jredfoot.utgrid.utsaa.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EntityManagerHelper {
	
	private static final EntityManagerFactory INSTANCE = Persistence
			.createEntityManagerFactory("UTSAA_PU");

	public static EntityManagerFactory get() {
		return INSTANCE;
	}

	private EntityManagerHelper() {
	}
}
