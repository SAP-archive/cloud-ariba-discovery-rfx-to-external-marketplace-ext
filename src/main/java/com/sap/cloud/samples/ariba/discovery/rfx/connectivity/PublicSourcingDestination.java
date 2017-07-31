package com.sap.cloud.samples.ariba.discovery.rfx.connectivity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublicSourcingDestination extends Destination {


	public static final String NAME = "ariba-public-sourcing";

	private static final String SITE_ID = "SiteId";
	private static final String AUTHENTICATION = "Authentication";
	private static final String BASIC_AUTHENTICATION_VALUE = "BasicAuthentication";
	private static final String SERVICE_PROVIDER_USER = "User";
	private static final String SERVICE_PROVIDER_PASSWORD = "Password";
	private static final String API_KEY = "ApiKey";
	private static final String OPEN_API_ENVIRONMENT_URL = "URL";
	private static final String JOB_INTERVAL_IN_SECONDS = "JobIntervalInSeconds";

	private static final String ERROR_DESTINATION_PROPERTY_IS_NOT_VALID_INTEGER = "Destination property [{0}] is not valid integer.";

	private static final Logger logger = LoggerFactory.getLogger(PublicSourcingDestination.class);

	public PublicSourcingDestination(String destinationName) {
		super(destinationName);
	}

	@Override
	public List<String> getRequiredProperties() {
		List<String> requiredProperties = new ArrayList<>();
		requiredProperties.add(SITE_ID);
		requiredProperties.add(AUTHENTICATION);
		requiredProperties.add(API_KEY);
		requiredProperties.add(OPEN_API_ENVIRONMENT_URL);
		requiredProperties.add(JOB_INTERVAL_IN_SECONDS);
		
		return requiredProperties;
	}

	public String getAribaUrl() {
		return getPropertyValue(OPEN_API_ENVIRONMENT_URL);
	}

	public String getSiteId() {
		return getPropertyValue(SITE_ID);
	}

	public boolean isBasicAuthentication() {
		return getPropertyValue(AUTHENTICATION) == BASIC_AUTHENTICATION_VALUE;
	}

	public String getServiceProviderUser() {
		return getPropertyValue(SERVICE_PROVIDER_USER);
	}

	public String getServiceProviderPassword() {
		return getPropertyValue(SERVICE_PROVIDER_PASSWORD);
	}

	public String getApiKey() {
		return getPropertyValue(API_KEY);
	}

	public Integer getJobIntervalSeconds() {
		String jobIntervalSeconds = getPropertyValue(JOB_INTERVAL_IN_SECONDS);
		Integer seconds;
		try {
			seconds = Integer.valueOf(jobIntervalSeconds);
		} catch (NumberFormatException e) {
			String errorMessage = MessageFormat.format(ERROR_DESTINATION_PROPERTY_IS_NOT_VALID_INTEGER,
					JOB_INTERVAL_IN_SECONDS);
			logger.error(errorMessage);
			throw new IllegalArgumentException(errorMessage, e);
		}

		return seconds;
	}
}
