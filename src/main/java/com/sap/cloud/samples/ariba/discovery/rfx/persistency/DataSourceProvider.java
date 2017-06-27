package com.sap.cloud.samples.ariba.discovery.rfx.persistency;

import java.text.MessageFormat;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data source provider.
 */
public class DataSourceProvider {

	private static final String ERROR_LOOKING_UP_THE_DATA_SOURCE_FAILED = "Looking up the Data Source caused an exception. Make sure the resource [{0}] is set up properly.";

	private static final String DEBUG_INITIALIZING_DATA_SOURCE = "Initializing Data Source ...";
	private static final String DEBUG_DATA_SOURCE_INITIALIZED = "Data Source initialized.";

	private static final String DATA_SOURCE_NAME = "java:comp/env/jdbc/DefaultDB";

	private final static Logger logger = LoggerFactory.getLogger(DataSourceProvider.class);

	private static DataSourceProvider instance;
	private DataSource dataSource;

	private DataSourceProvider() {
		this.dataSource = retrieveDataSourceProvider();
	}

	/**
	 * Initializes and returns singleton DataSourceProvider.
	 * 
	 * @return singleton DataSourceProvider.
	 */
	public static synchronized DataSourceProvider getInstance() {
		if (DataSourceProvider.instance == null) {
			synchronized (DataSourceProvider.class) {
				if (DataSourceProvider.instance == null) {
					DataSourceProvider.instance = new DataSourceProvider();
				}
			}
		}

		return DataSourceProvider.instance;
	}

	/**
	 * Returns the data source.
	 *
	 * @return the data source
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	private DataSource retrieveDataSourceProvider() {
		logger.debug(DEBUG_INITIALIZING_DATA_SOURCE);

		DataSource dataSource;
		try {
			InitialContext initialContext = new InitialContext();
			dataSource = (DataSource) initialContext.lookup(DATA_SOURCE_NAME);
		} catch (NamingException e) {
			String errorMessage = MessageFormat.format(ERROR_LOOKING_UP_THE_DATA_SOURCE_FAILED, DATA_SOURCE_NAME);
			logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}

		logger.debug(DEBUG_DATA_SOURCE_INITIALIZED);
		return dataSource;
	}
}