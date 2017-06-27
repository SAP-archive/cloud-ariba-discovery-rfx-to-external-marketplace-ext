package com.sap.cloud.samples.ariba.discovery.rfx.connectivity;

import java.text.MessageFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

/**
 * Represents destination.
 */
public class Destination {

	private static final String ERROR_PROPERTY_NOT_FOUND = "Property [{0}] not found in destination [ {1} ]. Hint: Make sure to have the property configured in the destination.";
	private static final String ERROR_DESTINATION_PROPERTY_KEY_CANNOT_BE_NULL = "Destination property key cannot be null.";
	private static final String ERROR_DESTINATION_NOT_FOUND = "Destination [{0}] not found. Hint: Make sure to have the destination configured.";

	private static final Logger logger = LoggerFactory.getLogger(Destination.class);

	private String name;
	private Map<String, String> properties;

	/**
	 * Constructor used to initialize the Destination.
	 * 
	 * @param name
	 *            the name of the destination.
	 */
	public Destination(String name) {
		this.name = name;
		this.properties = retrieveDestinationProperties(name);
	}

	/**
	 * Returns all destination properties.
	 * 
	 * @return all destination properties.
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * Returns destination property value by specified destination property key.
	 * 
	 * @param propertyKey
	 *            - the key of the searched destination property
	 * @return the value of the destination with the specified destination key.
	 * @throws IllegalArgumentException
	 *             when the property key is null or destination property with
	 *             the specified key is not found.
	 * @throws RuntimeException
	 *             when connectivity configuration initialization has failed.
	 */
	public String getPropertyValue(String propertyKey) {
		if (propertyKey == null) {
			throw new IllegalArgumentException(ERROR_DESTINATION_PROPERTY_KEY_CANNOT_BE_NULL);
		}

		String propertyValue = properties.get(propertyKey);

		if (propertyValue == null) {
			String errorMessage = MessageFormat.format(ERROR_PROPERTY_NOT_FOUND, propertyKey, name);
			logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}

		return propertyValue;
	}

	private Map<String, String> retrieveDestinationProperties(String destinationName) {
		DestinationConfiguration destinationConfiguration = ConnectivityConfigurationProvider
				.retrieveConnectivityConfiguration().getConfiguration(destinationName);

		if (destinationConfiguration == null) {
			String errorMessage = MessageFormat.format(ERROR_DESTINATION_NOT_FOUND, destinationName);
			logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}

		return destinationConfiguration.getAllProperties();
	}
}