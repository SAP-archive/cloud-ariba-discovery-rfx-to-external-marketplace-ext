package com.sap.cloud.samples.ariba.discovery.rfx.persistency;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity manager provider.
 */
public class EntityManagerProvider {

	private static final String DEBUG_CREATING_ENTITY_MANAGER = "Creating entity manager...";
	private static final String DEBUG_ENTITY_MANAGER_CREATED = "Entity manager created.";
	
	private final static Logger logger = LoggerFactory.getLogger(EntityManagerProvider.class);

	/**
	 * Creates and returns new entity manager.
	 *
	 * @return new entity manager.
	 */
	public static EntityManager createEntityManager() {
		logger.debug(DEBUG_CREATING_ENTITY_MANAGER);
		EntityManager entityManager = EntityManagerFactoryProvider.getEntityManagerFactory().createEntityManager();
		logger.debug(DEBUG_ENTITY_MANAGER_CREATED);
		
		return entityManager;
	}
}
