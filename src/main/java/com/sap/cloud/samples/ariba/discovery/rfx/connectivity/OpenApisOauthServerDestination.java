package com.sap.cloud.samples.ariba.discovery.rfx.connectivity;

public class OpenApisOauthServerDestination extends Destination {

	public static final String NAME = "ariba-open-apis-oauth-server";

	private static final String OPEN_APIS_OAUTH_SERVER_URL = "URL";
	private static final String OPEN_APIS_APPLICATION_OAUTH_CLIENT_ID = "User";
	private static final String OPEN_APIS_APPLICATION_OAUTH_CLIENT_SECRET = "Password";

	/**
	 * Constructor.
	 * 
	 * @param destinationName
	 *            use {@value #NAME}
	 */
	public OpenApisOauthServerDestination(String destinationName) {
		super(destinationName);
	}

	public String getAribaOpenApisOauthServerUrl() {
		return getPropertyValue(OPEN_APIS_OAUTH_SERVER_URL);
	}

	public String getApplicationOauthClientId() {
		return getPropertyValue(OPEN_APIS_APPLICATION_OAUTH_CLIENT_ID);
	}

	public String getApplicationOauthClientSecret() {
		return getPropertyValue(OPEN_APIS_APPLICATION_OAUTH_CLIENT_SECRET);
	}
}
