package com.sap.cloud.samples.ariba.discovery.rfx.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents Ariba Open APIs end point with basic authentication and API key.
 *
 */
public class OpenApisEndpoint {

	private static final String DEBUG_EXECUTING_HTTP_GET_FOR = "Executing HTTP Get for [{}{}]...";
	private static final String DEBUG_EXECUTED_HTTP_GET_FOR = "Executed HTTP Get for [{}{}].";
	private static final String DEBUG_EXECUTING_HTTP_POST_FOR_WITH_JSON_CONTENT = "Executing HTTP Post for [{}{}] with JSON content [{}]...";
	private static final String DEBUG_EXECUTED_HTTP_POST_FOR_WITH_JSON_CONTENT = "Executed HTTP Post for [{}{}] with JSON content [{}].";
	private static final String DEBUG_EXECUTING_HTTP_POST_FOR = "Executing HTTP Post for [{}{}]...";
	private static final String DEBUG_EXECUTED_HTTP_POST_FOR = "Executed HTTP Post for [{}{}].";

	private static final String APPLICATION_JSON = "application/json";
	private static final String AUTHORIZATION_BASIC = "Basic";

	private static final String APIKEY = "apiKey";

	private static final Logger logger = LoggerFactory.getLogger(OpenApisEndpoint.class);

	private String baseUri;
	private HttpHost proxy;
	private Header basicAuthorizationHeader;
	private Header apikeyHeader;

	/**
	 * Constructor used for production environment.
	 * 
	 * @param baseUri
	 *            base URI that will be called.
	 * @param proxyType
	 *            the proxy type.
	 * @param serviceProviderUser
	 *            the service provider user.
	 * @param serviceProviderPassword
	 *            the service provider password.
	 * @param apiKey
	 *            API key that will be used when calling the API.
	 */
	public OpenApisEndpoint(String baseUri, String proxyType, String serviceProviderUser,
			String serviceProviderPassword, String apiKey) {
		this.baseUri = baseUri;
		this.proxy = ProxyUtil.createProxy(proxyType);
		this.basicAuthorizationHeader = getBasicAuthorizationHeader(serviceProviderUser, serviceProviderPassword);
		this.apikeyHeader = getApikeyHeader(apiKey);
	}

	/**
	 * Constructor used for sandbox environment.
	 * 
	 * @param baseUri
	 *            base URI that will be called.
	 * @param apiKey
	 *            API key that will be used when calling the API.
	 */
	public OpenApisEndpoint(String baseUri, String proxyType, String apiKey) {
		this.baseUri = baseUri;
		this.proxy = ProxyUtil.createProxy(proxyType);
		this.apikeyHeader = getApikeyHeader(apiKey);
	}

	private Header getBasicAuthorizationHeader(String user, String password) {
		return new BasicHeader(HttpHeaders.AUTHORIZATION,
				AUTHORIZATION_BASIC + " " + OpenApisEndpoint.encodeBase64(user, password));
	}

	private static String encodeBase64(String user, String password) {
		return new String(Base64.encodeBase64((user + ":" + password).getBytes()));
	}

	private Header getApikeyHeader(String apikey) {
		return new BasicHeader(APIKEY, apikey);
	}

	/**
	 * Performs HTTP Get request for the end point with the given path.
	 * 
	 * @param path
	 *            the path to be called.
	 * @return the CloseableHttpResponse object.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse executeHttpGet(String path) throws ClientProtocolException, IOException {
		logger.debug(DEBUG_EXECUTING_HTTP_GET_FOR, baseUri, path);

		HttpGet httpGet = createHttpGet(baseUri + path);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpGet);

		logger.debug(DEBUG_EXECUTED_HTTP_GET_FOR, baseUri, path);
		return response;
	}

	private HttpGet createHttpGet(String uri) {
		HttpGet httpGet = new HttpGet(uri);
		if (basicAuthorizationHeader != null) {
			httpGet.addHeader(basicAuthorizationHeader);
		}
		httpGet.addHeader(apikeyHeader);

		if (proxy != null) {
			RequestConfig requsetConfig = RequestConfig.custom().setProxy(proxy).build();
			httpGet.setConfig(requsetConfig);
		}

		return httpGet;
	}

	/**
	 * Performs HTTP Post request for the end point with the given path.
	 * 
	 * @param path
	 *            the path to be called.
	 * @return the CloseableHttpResponse object.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse executeHttpPost(String path) throws ClientProtocolException, IOException {
		logger.debug(DEBUG_EXECUTING_HTTP_POST_FOR, baseUri, path);

		HttpPost httpPost = createHttpPost(baseUri + path);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpPost);

		logger.debug(DEBUG_EXECUTED_HTTP_POST_FOR, baseUri, path);
		return response;
	}

	/**
	 * Performs HTTP Post request for the end point with the given path, with
	 * the given JSON as payload.
	 * 
	 * @param path
	 *            the path to be called.
	 * @param jsonContent
	 *            the JSON content to be posted.
	 * @return the CloseableHttpResponse object.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse executeHttpPost(String path, String jsonContent)
			throws ClientProtocolException, IOException {
		logger.debug(DEBUG_EXECUTING_HTTP_POST_FOR_WITH_JSON_CONTENT, baseUri, path, jsonContent);

		HttpPost httpPost = createHttpPost(baseUri + path);

		StringEntity input = new StringEntity(jsonContent, StandardCharsets.UTF_8);
		input.setContentType(APPLICATION_JSON);
		httpPost.setEntity(input);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpPost);

		logger.debug(DEBUG_EXECUTED_HTTP_POST_FOR_WITH_JSON_CONTENT, baseUri, path, jsonContent);
		return response;
	}

	private HttpPost createHttpPost(String uri) {
		HttpPost httpPost = new HttpPost(uri);
		if (basicAuthorizationHeader != null) {
			httpPost.addHeader(basicAuthorizationHeader);
		}
		httpPost.addHeader(apikeyHeader);

		if (proxy != null) {
			RequestConfig requsetConfig = RequestConfig.custom().setProxy(proxy).build();
			httpPost.setConfig(requsetConfig);
		}

		return httpPost;
	}
}
