package com.sap.cloud.samples.ariba.discovery.rfx.connectivity;

import java.text.MessageFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;

/**
 * Connectivity configuration provider.
 */
public class ConnectivityConfigurationProvider {

	private static final String DEBUG_INITIALIAZING_CONNECTIVITY_SERVICE = "Initialazing connectivity service...";
	private static final String DEBUG_CONNECTIVITY_SERVICE_INITIALIZED = "Connectivity service initialized.";
	
	private static final String ERROR_LOOKING_UP_THE_CONNECTIVITY_SERVICE_FAILED = "Looking up the connectivity configuration caused an exception. Make sure the resource [{0}] is set up properly.";

	private static final String CONNECTIVITY_SERVICE_NAME = "java:comp/env/connectivityConfiguration";

	private static final Logger logger = LoggerFactory.getLogger(ConnectivityConfigurationProvider.class);

	private static ConnectivityConfiguration connectivityConfiguration;

	/**
	 * Initializes and returns singleton ConnectivityConfiguration.
	 * 
	 * @return singleton ConnectivityConfiguration.
	 */
	public static ConnectivityConfiguration retrieveConnectivityConfiguration() {
		if (ConnectivityConfigurationProvider.connectivityConfiguration == null) {
			synchronized (ConnectivityConfigurationProvider.class) {
				if (ConnectivityConfigurationProvider.connectivityConfiguration == null) {
					ConnectivityConfigurationProvider.connectivityConfiguration = initConnectivityConfiguration();
				}
			}
		}

		return ConnectivityConfigurationProvider.connectivityConfiguration;
	}

	private static ConnectivityConfiguration initConnectivityConfiguration() {
		logger.debug(DEBUG_INITIALIAZING_CONNECTIVITY_SERVICE);

		ConnectivityConfiguration connectivityConfiguration;
		try {
			Context initialContext = new InitialContext();
			connectivityConfiguration = (ConnectivityConfiguration) initialContext.lookup(CONNECTIVITY_SERVICE_NAME);
		} catch (NamingException e) {
			String errorMessage = MessageFormat.format(ERROR_LOOKING_UP_THE_CONNECTIVITY_SERVICE_FAILED,
					CONNECTIVITY_SERVICE_NAME);
			logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage);
		}

		logger.debug(DEBUG_CONNECTIVITY_SERVICE_INITIALIZED);
		return connectivityConfiguration;
	}
}
