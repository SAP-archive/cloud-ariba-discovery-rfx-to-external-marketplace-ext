package com.sap.cloud.samples.ariba.discovery.rfx.api;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents Ariba Open APIs OAuth Server end point.
 *
 */
public class OpenApisOauthServerEndpoint {

	private static final String DEBUG_EXECUTING_HTTP_POST_FOR = "Executing HTTP Post for [{}{}]...";
	private static final String DEBUG_EXECUTED_HTTP_POST_FOR = "Executed HTTP Post for [{}{}].";

	private static final String AUTHORIZATION = "Authorization";
	private static final String AUTHORIZATION_BASIC = "Basic";

	private static final Logger logger = LoggerFactory.getLogger(OpenApisOauthServerEndpoint.class);

	private String baseUri;
	private Header basicAuthorizationHeader;

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
	public OpenApisOauthServerEndpoint(String baseUri, String applicationOauthClientId,
			String applicationOauthClientSecret) {
		this.baseUri = baseUri;
		this.basicAuthorizationHeader = getBasicAuthorizationHeader(applicationOauthClientId,
				applicationOauthClientSecret);
	}

	private Header getBasicAuthorizationHeader(String user, String password) {
		return new BasicHeader(AUTHORIZATION, AUTHORIZATION_BASIC + " " + encodeBase64(user, password));
	}

	private String encodeBase64(String user, String password) {
		return new String(Base64.encodeBase64((user + ":" + password).getBytes()));
	}

	/**
	 * Performs HTTP Post request with basic authentication for the end point
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

	private HttpPost createHttpPost(String uri) {
		HttpPost httpPost = new HttpPost(uri);
		httpPost.addHeader(basicAuthorizationHeader);

		return httpPost;
	}

}
