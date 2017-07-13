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
import com.sap.cloud.samples.ariba.discovery.rfx.dtos.EventsDto;

/**
 * Facade for Ariba Discovery RFX Publication to External Marketplace API.
 *
 */
public class PublicSourcingApiFacade {

	private static final String RETRIEVE_EVENTS_PATH = "/site/{0}/events/?rsqlfilter=(count=={1})";
	private static final String ACKNOWLEDGE_EVENTS_PATH = "/action/site/{0}/events/{1}/acknowledge";
	private static final String DEBUG_ACKNOWLEDGING_EVENT_MESSAGE = "Acknowledging event [{}]...";
	private static final String DEBUG_EVENT_ACKNOWLEDGED_MESSAGE = "Acknowledging event [{}] done.";
	private static final String DEBUG_RETRIEVING_EVENTS_MESSAGE = "Retrieving [{}] events...";
	private static final String DEBUG_EVENTS_RETRIEVED_MESSAGE = "Events retrieved: [{}]";
	private static final String DEBUG_CALLING_MESSAGE = "Calling [{}]...";
	private static final String DEBUG_CALLING_URI_RETURNED_STATUS_MESSAGE = "Calling [{}] returned status: {}";
	private static final String DEBUG_RETRIEVING_ACCESS_TOKEN_MESSAGE = "Retrieving OAuth access token...";
	private static final String DEBUG_RETRIEVED_ACCESS_TOKEN_MESSAGE = "Retrieved OAuth access token.";
	private static final String DEBUG_NO_OAUTH_SERVER_IS_CONFIGURED = "No OAuth Server destination is configured, assuming sandbox environment, OAuth request will be skipped.";

	private static final String ERROR_PROBLEM_OCCURED_WHILE_CALLING_MESSAGE = "Problem occured while calling [{0}].";

	private static final int DEFAULT_NUMBER_OF_EVENTS_REQUESTED = 10;

	private final String siteId;

	private final OpenApisEndpoint openApisEndpoint;
	private final OpenAPIsOauthServerFacade openAPIsOauthServerFacade;

	private static final Logger logger = LoggerFactory.getLogger(PublicSourcingApiFacade.class);

	/**
	 * Constructor.
	 * 
	 * @param aribaOpenApisEnvironmentUrl
	 *            the URL of the Ariba OpenAPIs environment to be called.
	 * @param siteId
	 *            the site ID.
	 * @param apiKey
	 *            API key to be used for the API calls.
	 * @param openAPIsOauthServerFacade
	 *            SAP Ariba Open APIs OAuth Server facade.
	 */
	public PublicSourcingApiFacade(String aribaOpenApisEnvironmentUrl, String siteId, String apiKey,
			OpenAPIsOauthServerFacade openAPIsOauthServerFacade) {
		this.siteId = siteId;

		this.openApisEndpoint = new OpenApisEndpoint(aribaOpenApisEnvironmentUrl, apiKey);
		this.openAPIsOauthServerFacade = openAPIsOauthServerFacade;
	}

	/**
	 * Retrieve {@value #DEFAULT_NUMBER_OF_EVENTS_REQUESTED} events.
	 *
	 * @return the {@value #DEFAULT_NUMBER_OF_EVENTS_REQUESTED} most recent
	 *         events from the Extension Queue or null in case of no events.
	 *
	 * @throws UnsuccessfulOperationException
	 *             when retrieving the events was not successful.
	 */
	public EventsDto retrieveEvents() throws UnsuccessfulOperationException {
		return retrieveEvents(DEFAULT_NUMBER_OF_EVENTS_REQUESTED);
	}

	/**
	 * Retrieves the "count" most recent events from Extension Queue.
	 * 
	 * @param count
	 *            the number of events to be retrieved from the Extension Queue.
	 * @return the "count" most recent events from the Extension Queue or null
	 *         in case of no events.
	 * @throws UnsuccessfulOperationException
	 *             when retrieving the events was not successful.
	 */
	public EventsDto retrieveEvents(int count) throws UnsuccessfulOperationException {
		logger.debug(DEBUG_RETRIEVING_EVENTS_MESSAGE, count);
		AccessTokenDto accessToken = null;
		if(openAPIsOauthServerFacade != null) {
			logger.debug(DEBUG_RETRIEVING_ACCESS_TOKEN_MESSAGE);
			accessToken = openAPIsOauthServerFacade.retrieveAccessToken();
			logger.debug(DEBUG_RETRIEVED_ACCESS_TOKEN_MESSAGE);
		} else {
			logger.debug(DEBUG_NO_OAUTH_SERVER_IS_CONFIGURED);
		}

		EventsDto result = null;
		String retrieveEventsPath = MessageFormat.format(RETRIEVE_EVENTS_PATH, siteId, count);

		logger.debug(DEBUG_CALLING_MESSAGE, retrieveEventsPath);
		try (CloseableHttpResponse retrieveEventsResponse = openApisEndpoint.httpGet(retrieveEventsPath,
				accessToken != null ? accessToken.getAccessToken() : null)) {
			int retrieveEventsResponseStatusCode = HttpResponseUtils.validateHttpStatusResponse(retrieveEventsResponse,
					HttpStatus.SC_OK, HttpStatus.SC_NO_CONTENT);

			logger.debug(DEBUG_CALLING_URI_RETURNED_STATUS_MESSAGE, retrieveEventsPath,
					retrieveEventsResponseStatusCode);

			if (retrieveEventsResponseStatusCode == HttpStatus.SC_OK) {
				HttpEntity retrieveEventsResponseEntity = retrieveEventsResponse.getEntity();
				if (retrieveEventsResponseEntity != null) {
					try {
						result = HttpResponseUtils.convertHttpResponse(retrieveEventsResponseEntity, EventsDto.class);
					} finally {
						EntityUtils.consume(retrieveEventsResponseEntity);
					}
				}
			}

		} catch (IOException | HttpResponseException e) {
			String errorMessage = MessageFormat.format(ERROR_PROBLEM_OCCURED_WHILE_CALLING_MESSAGE, retrieveEventsPath);
			logger.error(errorMessage);
			throw new UnsuccessfulOperationException(errorMessage, e);
		}

		logger.debug(DEBUG_EVENTS_RETRIEVED_MESSAGE, result);

		return result;
	}

	/**
	 * Acknowledges event. Acknowledged event is not returned in subsequent Get
	 * Event calls. Subsequent API calls for the event, such as Post Document
	 * Update and Resume, will not work until Acknowledge has been called.
	 * 
	 * @param eventId
	 *            event id to be acknowledged.
	 * @throws UnsuccessfulOperationException
	 *             when acknowledge fails. when there is a problem with the HTTP
	 *             request.
	 */
	public void acknowledgeEvent(String eventId) throws UnsuccessfulOperationException {
		logger.debug(DEBUG_ACKNOWLEDGING_EVENT_MESSAGE, eventId);
		AccessTokenDto accessToken = null;
		if(openAPIsOauthServerFacade != null){
			logger.debug(DEBUG_RETRIEVING_ACCESS_TOKEN_MESSAGE);
			accessToken = openAPIsOauthServerFacade.retrieveAccessToken();
			logger.debug(DEBUG_RETRIEVED_ACCESS_TOKEN_MESSAGE);			
		}

		String acknowledgeEventUrl = MessageFormat.format(ACKNOWLEDGE_EVENTS_PATH, siteId, eventId);

		logger.debug(DEBUG_CALLING_MESSAGE, acknowledgeEventUrl);

		try (CloseableHttpResponse acknowledgeEventResponse = openApisEndpoint.httpPost(acknowledgeEventUrl,
				accessToken!= null ? accessToken.getAccessToken():null)) {

			int acknowledgeEventsResponseStatusCode = HttpResponseUtils
					.validateHttpStatusResponse(acknowledgeEventResponse, HttpStatus.SC_OK);

			logger.debug(DEBUG_CALLING_URI_RETURNED_STATUS_MESSAGE, acknowledgeEventUrl,
					acknowledgeEventsResponseStatusCode);
		} catch (IOException | HttpResponseException e) {
			String errorMessage = MessageFormat.format(ERROR_PROBLEM_OCCURED_WHILE_CALLING_MESSAGE,
					acknowledgeEventUrl);
			logger.error(errorMessage);
			throw new UnsuccessfulOperationException(errorMessage, e);
		}

		logger.debug(DEBUG_EVENT_ACKNOWLEDGED_MESSAGE, eventId);
	}

}
