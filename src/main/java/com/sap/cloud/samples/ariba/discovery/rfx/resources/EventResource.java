package com.sap.cloud.samples.ariba.discovery.rfx.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.samples.ariba.discovery.rfx.daos.EventDao;
import com.sap.cloud.samples.ariba.discovery.rfx.persistency.entities.Event;
import com.sap.cloud.samples.ariba.discovery.rfx.utils.AribaModelMapper;

/**
 * REST endpoint for retrieving and deleting events.
 *
 */
@Path("events")
public class EventResource {

	private static final String ERROR_RETRIEVING_EVENTS = "Problem occured while retrieving events.";

	private static final String DEBUG_STARTED_RETRIEVING_EVENTS = "Started retrieving events fromt the database...";
	private static final String DEBUG_RETIEVED_EVENTS = "Retieved {} events from the database.";
	private static final String DEBUG_RETRIEVING_EVENTS_FINISHED = "Retrieving events from the database finished.";

	private EventDao eventDao = new EventDao();

	private static final Logger logger = LoggerFactory.getLogger(EventResource.class);

	/**
	 * Retrieves all events from the database.
	 *
	 * @return response - HTTP OK 200 for success with events list as JSON or
	 *         HTTP Error 500 Internal server error
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveEvents() {
		logger.debug(DEBUG_STARTED_RETRIEVING_EVENTS);

		Response response = null;

		try {
			List<Event> events = eventDao.readAll();
			logger.debug(DEBUG_RETIEVED_EVENTS, events.size());

			response = Response.status(Response.Status.OK).entity(AribaModelMapper.map(events)).build();
		} catch (Exception e) {
			logger.error(ERROR_RETRIEVING_EVENTS, e);
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		logger.debug(DEBUG_RETRIEVING_EVENTS_FINISHED);

		return response;
	}
}
