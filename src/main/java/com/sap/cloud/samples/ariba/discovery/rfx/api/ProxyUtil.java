package com.sap.cloud.samples.ariba.discovery.rfx.api;

import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Proxy Util class.
 *
 */
public class ProxyUtil {

	private static final String ON_PREMISE_PROXY = "OnPremise";

	private static final String INTERNET_PROXY_HOST = "http.proxyHost";
	private static final String INTERNET_PROXY_PORT = "http.proxyPort";

	private static final String ON_PREMISE_PROXY_HOST = "HC_OP_HTTP_PROXY_HOST";
	private static final String ON_PREMISE_PROXY_PORT = "HC_OP_HTTP_PROXY_PORT";

	private static final String DEBUG_CREATING_PROXY_OF_TYPE = "Creating proxy of type: [{}]";
	private static final String DEBUG_CREATED_PROXY = "Created proxy [{}]:[{}]";
	private static final String DEBUG_NO_PROXY_SET = "No proxy set.";

	private static final Logger logger = LoggerFactory.getLogger(ProxyUtil.class);

	/**
	 * Creates and returns a proxy object based on a proxy type.
	 * 
	 * In case of OnPremise proxy type - creates proxy object for on-premise
	 * connectivity. In case of any other proxy type - creates proxy object for
	 * Internet connectivity.
	 * 
	 * @param proxyType
	 *            the proxy type. "OnPremise" for on-premise connectivity.
	 * @return the created proxy host or null if no proxy is set as system
	 *         property.
	 */
	public static HttpHost createProxy(String proxyType) {
		logger.debug(DEBUG_CREATING_PROXY_OF_TYPE, proxyType);

		HttpHost proxy = null;

		String proxyHost;
		int proxyPort;

		if (ON_PREMISE_PROXY.equals(proxyType)) {
			// Get proxy for On-Premise connectivity
			proxyHost = System.getenv(ON_PREMISE_PROXY_HOST);
			proxyPort = Integer.parseInt(System.getenv(ON_PREMISE_PROXY_PORT));
			proxy = new HttpHost(proxyHost, proxyPort);
			logger.debug(DEBUG_CREATED_PROXY, proxyHost, proxyPort);
		} else {
			if (System.getProperty(INTERNET_PROXY_HOST) == null || System.getProperty(INTERNET_PROXY_PORT) == null) {
				logger.debug(DEBUG_NO_PROXY_SET);
			} else {
				// Get proxy for Internet connectivity
				proxyHost = System.getProperty(INTERNET_PROXY_HOST);
				proxyPort = Integer.parseInt(System.getProperty(INTERNET_PROXY_PORT));
				proxy = new HttpHost(proxyHost, proxyPort);
				logger.debug(DEBUG_CREATED_PROXY, proxyHost, proxyPort);
			}
		}

		return proxy;
	}
}