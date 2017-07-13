package com.sap.cloud.samples.ariba.discovery.rfx.api;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.samples.ariba.discovery.rfx.dtos.AccessTokenDto;

/**
 * Facade for SAP Ariba Open APIs OAuth Server.
 *
 */
public class OpenAPIsOauthServerFacade {

	private static final String OPEN_API_OAUTH_GRANT_TYPE_QUERY_PARAMETER = "?grant_type=openapi_2lo";

	private static final String DEBUG_RETRIEVING_ACCESS_TOKEN_MESSAGE = "Retrieving Access Token...";
	private static final String DEBUG_RETRIEVED_ACCESS_TOKEN_MESSAGE = "Retrieved Access Token.";
	private static final String DEBUG_CALLING_URI_RETURNED_STATUS_MESSAGE = "Calling [{}] returned status: {}";

	private static final String ERROR_PROBLEM_OCCURED_WHILE_CALLING_MESSAGE = "Problem occured while calling [{0}].";

	private OpenApisOauthServerEndpoint openApisOauthServerEndpoint;

	private static final Logger logger = LoggerFactory.getLogger(OpenAPIsOauthServerFacade.class);

	/**
	 * Constructor.
	 * 
	 * @param openApisOAuthServerUrl
	 *            the URL of the SAP Ariba OpenAPIs OAuth Server.
	 * @param applicationOauthClientId
	 *            the Open APIs application OAuth client ID.
	 * @param applicationOauthClientSecret
	 *            the Open APIs application OAuth client secret.
	 */
	public OpenAPIsOauthServerFacade(String openApisOAuthServerUrl, String applicationOauthClientId,
			String applicationOauthClientSecret) {
		this.openApisOauthServerEndpoint = new OpenApisOauthServerEndpoint(openApisOAuthServerUrl,
				applicationOauthClientId, applicationOauthClientSecret);
	}

	/**
	 * Retrieves an access token for the given OAuth client.
	 * 
	 * @return an access token.
	 * @throws UnsuccessfulOperationException
	 */
	public AccessTokenDto retrieveAccessToken() throws UnsuccessfulOperationException {
		logger.debug(DEBUG_RETRIEVING_ACCESS_TOKEN_MESSAGE);

		AccessTokenDto result = null;
		try (CloseableHttpResponse retrieveAccessTokenResponse = openApisOauthServerEndpoint
				.httpPost(OPEN_API_OAUTH_GRANT_TYPE_QUERY_PARAMETER)) {
			int retrieveEventsResponseStatusCode = HttpResponseUtils
					.validateHttpStatusResponse(retrieveAccessTokenResponse, HttpStatus.SC_OK);

			logger.debug(DEBUG_CALLING_URI_RETURNED_STATUS_MESSAGE, OPEN_API_OAUTH_GRANT_TYPE_QUERY_PARAMETER,
					retrieveEventsResponseStatusCode);

			HttpEntity retrieveAccessTokenResponseEntity = retrieveAccessTokenResponse.getEntity();
			if (retrieveAccessTokenResponseEntity != null) {
				try {
					result = HttpResponseUtils.convertHttpResponse(retrieveAccessTokenResponseEntity,
							AccessTokenDto.class);
				} finally {
					EntityUtils.consume(retrieveAccessTokenResponseEntity);
				}
			}

		} catch (IOException | HttpResponseException e) {
			String errorMessage = MessageFormat.format(ERROR_PROBLEM_OCCURED_WHILE_CALLING_MESSAGE,
					OPEN_API_OAUTH_GRANT_TYPE_QUERY_PARAMETER);
			logger.error(errorMessage);
			throw new UnsuccessfulOperationException(errorMessage, e);
		}

		logger.debug(DEBUG_RETRIEVED_ACCESS_TOKEN_MESSAGE, result);

		return result;
	}
}
