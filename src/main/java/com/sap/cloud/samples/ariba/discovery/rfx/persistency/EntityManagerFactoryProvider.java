package com.sap.cloud.samples.ariba.discovery.rfx.persistency;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entity manager factory provider.
 */
public class EntityManagerFactoryProvider {

	private static final String DEBUG_INITIALIZING_ENTITY_MANAGER_FACTORY = "Initializing entity manager factory ...";
	private static final String DEBUG_ENTITY_MANAGER_FACTORY_INITIALIZED = "Entity manager factory initialized.";
	private static final String DEBUG_CLOSING_ENTITY_MANAGER_FACTORY = "Closing entity manager factory ...";
	private static final String DEBUG_ENTITY_MANAGER_FACTORY_CLOSED = "Entity manager factory closed.";

	private final static Logger logger = LoggerFactory.getLogger(EntityManagerFactoryProvider.class);

	private static EntityManagerFactory entityManagerFactory = null;

	/**
	 * Initializes and returns singleton EntityManagerFactory.
	 *
	 * @return singleton EntityManagerFactory.
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		if (EntityManagerFactoryProvider.entityManagerFactory == null
				|| !EntityManagerFactoryProvider.entityManagerFactory.isOpen()) {
			synchronized (EntityManagerFactoryProvider.class) {
				if (EntityManagerFactoryProvider.entityManagerFactory == null
						|| !EntityManagerFactoryProvider.entityManagerFactory.isOpen()) {
					initEntityManagerFactory(DataSourceProvider.getInstance().getDataSource());
				}
			}
		}

		return EntityManagerFactoryProvider.entityManagerFactory;
	}

	private static void initEntityManagerFactory(DataSource dataSource) {
		logger.debug(DEBUG_INITIALIZING_ENTITY_MANAGER_FACTORY);

		Map<Object, Object> properties = new HashMap<>();
		properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, dataSource);

		EntityManagerFactoryProvider.entityManagerFactory = Persistence
				.createEntityManagerFactory(PublicSourcingPersistenceUnit.NAME, properties);

		logger.debug(DEBUG_ENTITY_MANAGER_FACTORY_INITIALIZED);
	}

	/**
	 * Closes the entity manager factory.
	 */
	public synchronized void closeEntityManagerFactory() {
		logger.debug(DEBUG_CLOSING_ENTITY_MANAGER_FACTORY);

		if (EntityManagerFactoryProvider.entityManagerFactory != null) {
			EntityManagerFactoryProvider.entityManagerFactory.close();
			EntityManagerFactoryProvider.entityManagerFactory = null;
		}

		logger.debug(DEBUG_ENTITY_MANAGER_FACTORY_CLOSED);
	}
}
