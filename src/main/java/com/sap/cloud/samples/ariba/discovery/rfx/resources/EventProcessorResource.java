package com.sap.cloud.samples.ariba.discovery.rfx.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.samples.ariba.discovery.rfx.connectivity.OpenApisOauthServerDestination;
import com.sap.cloud.samples.ariba.discovery.rfx.connectivity.PublicSourcingDestination;
import com.sap.cloud.samples.ariba.discovery.rfx.scheduler.PublicSourcingEventsProcessor;

/**
 * REST endpoint for processing of events manually. The event processing
 * includes calling Discovery RFX Publication to External Marketplace API and
 * persisting all new events.
 * 
 */
@Path("eventProcessor")
public class EventProcessorResource {

	private static final String DEBUG_MANUAL_START_OF_EVENTS_PROCESSING = "Manual start of events processing...";
	private static final String DEBUG_MANUAL_START_OF_EVENTS_PROCESSING_FINISHED = "Manual start of events processing finished.";

	private static final String ERROR_PROCESSING_EVENTS = "Problem occured while processing events.";

	private static final Logger logger = LoggerFactory.getLogger(EventProcessorResource.class);

	/**
	 * Starts the processing of events manually. That is - calls Discovery RFX
	 * Publication to External Marketplace API and persists all new events.
	 *
	 * @return response - HTTP OK 200 for success or HTTP Error 500 Internal
	 *         server error
	 */
	@Path("process")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response processEvents() {
		logger.debug(DEBUG_MANUAL_START_OF_EVENTS_PROCESSING);

		Response response = null;

		try {
			
			PublicSourcingEventsProcessor eventsProcessor = PublicSourcingEventsProcessor.initilizeEventProcessorInstance();
			eventsProcessor.processEvents();

			response = Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			logger.error(ERROR_PROCESSING_EVENTS, e);
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		logger.debug(DEBUG_MANUAL_START_OF_EVENTS_PROCESSING_FINISHED);

		return response;
	}
}
