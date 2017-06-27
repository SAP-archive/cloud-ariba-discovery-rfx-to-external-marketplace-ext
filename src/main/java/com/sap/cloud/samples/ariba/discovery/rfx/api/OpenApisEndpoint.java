package com.sap.cloud.samples.ariba.discovery.rfx.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
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
 * Represents Ariba Open APIs endpoint with OAuth authorization and API key.
 *
 */
public class OpenApisEndpoint {

	private static final String DEBUG_EXECUTING_HTTP_GET_FOR = "Executing HTTP Get for [{}{}]...";
	private static final String DEBUG_EXECUTED_HTTP_GET_FOR = "Executed HTTP Get for [{}{}].";
	private static final String DEBUG_EXECUTING_HTTP_POST_FOR_WITH_JSON_CONTENT = "Executing HTTP Post for [{}{}] with JSON content [{}]...";
	private static final String DEBUG_EXECUTED_HTTP_POST_FOR_WITH_JSON_CONTENT = "Executed HTTP Post for [{}{}] with JSON content [{}].";
	private static final String DEBUG_EXECUTING_HTTP_POST_FOR = "Executing HTTP Post for [{}{}]...";
	private static final String DEBUG_EXECUTED_HTTP_POST_FOR = "Executed HTTP Post for [{}{}].";

	private static final String AUTHORIZATION = "Authorization";
	private static final String APPLICATION_JSON = "application/json";
	public static final String AUTHORIZATION_BEARER = "Bearer";

	private static final String APIKEY = "apiKey";

	private static final Logger logger = LoggerFactory.getLogger(OpenApisEndpoint.class);

	private String baseUri;
	private Header bearerAuthorizationHeader;
	private Header apikeyHeader;
	
	/**
	 * Constructor.
	 * 
	 * @param baseUri
	 *            base URI that will be called.
	 * @param accessToken
	 *            OAuth access token that will be used when calling the API.
	 * @param apiKey
	 *            API key that will be used when calling the API.
	 */
	public OpenApisEndpoint(String baseUri, String accessToken, String apiKey) {
		this.baseUri = baseUri;
		this.bearerAuthorizationHeader = getBearerAuthorizationHeader(accessToken);
		this.apikeyHeader = getApikeyHeader(apiKey);
	}
	
	private Header getBearerAuthorizationHeader(String accessToken) {
		return new BasicHeader(AUTHORIZATION, AUTHORIZATION_BEARER + " " + accessToken);
	}

	private Header getApikeyHeader(String apikey) {
		return new BasicHeader(APIKEY, apikey);
	}

	/**
	 * Performs HTTP Get request with OAuth authentication for the endpoint with
	 * the given path.
	 * 
	 * @param path
	 *            the path to be called.
	 * @return the CloseableHttpResponse object.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse httpGet(String path) throws ClientProtocolException, IOException {
		logger.debug(DEBUG_EXECUTING_HTTP_GET_FOR, baseUri, path);
		
		HttpGet httpGet = createHttpGet(baseUri + path);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpGet);

		logger.debug(DEBUG_EXECUTED_HTTP_GET_FOR, baseUri, path);
		return response;
	}
	
	private HttpGet createHttpGet(String uri) {
		HttpGet httpGet = new HttpGet(uri);
		httpGet.addHeader(bearerAuthorizationHeader);
		httpGet.addHeader(apikeyHeader);

		return httpGet;
	}

	/**
	 * Performs HTTP Post request with OAuth authentication for the endpoint
	 * with the given path.
	 * 
	 * @param path
	 *            the path to be called.
	 * @return the CloseableHttpResponse object.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse httpPost(String path) throws ClientProtocolException, IOException {
		logger.debug(DEBUG_EXECUTING_HTTP_POST_FOR, baseUri, path);
		
		HttpPost httpPost = createHttpPost(baseUri + path);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = httpClient.execute(httpPost);

		logger.debug(DEBUG_EXECUTED_HTTP_POST_FOR, baseUri, path);
		return response;
	}

	/**
	 * Performs HTTP Post request with OAuth authentication for the endpoint
	 * with the given path, with the given JSON as payload.
	 * 
	 * @param path
	 *            the path to be called.
	 * @param jsonContent
	 *            the JSON content to be posted.
	 * @return the CloseableHttpResponse object.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public CloseableHttpResponse httpPost(String path, String jsonContent) throws ClientProtocolException, IOException {
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
		httpPost.addHeader(bearerAuthorizationHeader);
		httpPost.addHeader(apikeyHeader);

		return httpPost;
	}
}
