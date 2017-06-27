package com.sap.cloud.samples.ariba.discovery.rfx.scheduler;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.samples.ariba.discovery.rfx.api.PublicSourcingApiFacade;
import com.sap.cloud.samples.ariba.discovery.rfx.api.UnsuccessfulOperationException;
import com.sap.cloud.samples.ariba.discovery.rfx.connectivity.PublicSourcingDestination;
import com.sap.cloud.samples.ariba.discovery.rfx.daos.EventDao;
import com.sap.cloud.samples.ariba.discovery.rfx.dtos.EventDto;
import com.sap.cloud.samples.ariba.discovery.rfx.dtos.EventsDto;
import com.sap.cloud.samples.ariba.discovery.rfx.persistency.entities.Event;
import com.sap.cloud.samples.ariba.discovery.rfx.utils.AribaModelMapper;

/**
 * Used to retrieve events from Ariba and save them in database.
 *
 */
public class PublicSourcingEventsProcessor {

	private static final String DEBUG_PERSISTING_EVENT_WITH_EVENT_ID = "Persisting event with event id [{}]...";
	private static final String DEBUG_PERSISTED_EVENT_WITH_EVENT_ID = "Persisted event with event id [{}].";
	private static final String DEBUG_EVENT_WITH_EVENT_ID_ALREADY_EXISTS_WILL_NOT_PERSIST_IT = "Event with event id [{}] already exists. Will not persist it.";
	private static final String DEBUG_ACKNOWLEDGING_EVENT_WITH_EVENT_ID = "Acknowledging event with event id [{}]...";
	private static final String DEBUG_ACKNOWLEDGED_EVENT_WITH_EVENT_ID = "Acknowledged event with event id [{}].";
	private static final String DEBUG_STARTED_PROCESSING_EVENTS = "Started processing events...";
	private static final String DEBUG_PROCESSING_EVENTS = "Processing [{}] events...";
	private static final String DEBUG_NO_EVENTS_TO_PROCESS = "No events to process.";
	private static final String DEBUG_PROCESSING_EVENTS_FINISHED = "Processing events finished.";

	private static final String ERROR_FAILED_TO_PROCESS_EVENT_WITH_EVENT_ID_MESSAGE = "Failed to process event with event id [{0}].";
	private static final String ERROR_PROBLEM_OCUURED_WHILE_PROCESSING_EVENTS = "Problem ocuured while processing events: {0}";

	private PublicSourcingApiFacade aribaPublicSourcingFacade;
	private EventDao eventDao;

	private static final Logger logger = LoggerFactory.getLogger(PublicSourcingEventsProcessor.class);

	public PublicSourcingEventsProcessor(PublicSourcingDestination aribaDestination) {
		String aribaUrl = aribaDestination.getAribaUrl();
		String siteId = aribaDestination.getSiteId();
		String accessToken = aribaDestination.getAccessToken();
		String apiKey = aribaDestination.getApiKey();

		this.aribaPublicSourcingFacade = new PublicSourcingApiFacade(aribaUrl, siteId, accessToken, apiKey);
		this.eventDao = new EventDao();
	}

	public void processEvents() throws EventProcessingException {
		logger.debug(DEBUG_STARTED_PROCESSING_EVENTS);

		try {
			EventsDto retrievedEventsDtos = aribaPublicSourcingFacade.retrieveEvents();
			if (retrievedEventsDtos != null && retrievedEventsDtos.getEvents() != null) {
				EventDto[] eventsDtos = retrievedEventsDtos.getEvents();
				logger.debug(DEBUG_PROCESSING_EVENTS, eventsDtos.length);
				for (EventDto eventDto : eventsDtos) {
					processEvent(eventDto);
				}
			} else {
				logger.debug(DEBUG_NO_EVENTS_TO_PROCESS);
			}
		} catch (Exception e) {
			logger.error(ERROR_PROBLEM_OCUURED_WHILE_PROCESSING_EVENTS);
			throw new EventProcessingException(ERROR_PROBLEM_OCUURED_WHILE_PROCESSING_EVENTS, e);
		}

		logger.debug(DEBUG_PROCESSING_EVENTS_FINISHED);
	}

	private void processEvent(EventDto eventDto) throws EventProcessingException {
		try {
			persistEvent(eventDto);
			acknowledgeEvent(eventDto);
		} catch (Exception e) {
			String errorMessage = MessageFormat.format(ERROR_FAILED_TO_PROCESS_EVENT_WITH_EVENT_ID_MESSAGE,
					eventDto.getEventId());
			logger.error(errorMessage);
			throw new EventProcessingException(errorMessage, e);
		}
	}

	private void persistEvent(EventDto eventDto) {
		logger.debug(DEBUG_PERSISTING_EVENT_WITH_EVENT_ID, eventDto.getEventId());

		Event existingEvent = eventDao.findByEventId(eventDto.getEventId());

		if (existingEvent == null) {
			Event event = AribaModelMapper.map(eventDto);
			eventDao.create(event);

			logger.debug(DEBUG_PERSISTED_EVENT_WITH_EVENT_ID, eventDto.getEventId());
		} else {
			logger.debug(DEBUG_EVENT_WITH_EVENT_ID_ALREADY_EXISTS_WILL_NOT_PERSIST_IT, eventDto.getEventId());
		}
	}

	private void acknowledgeEvent(EventDto eventDto) throws UnsuccessfulOperationException {
		logger.debug(DEBUG_ACKNOWLEDGING_EVENT_WITH_EVENT_ID, eventDto.getEventId());

		aribaPublicSourcingFacade.acknowledgeEvent(eventDto.getEventId());

		logger.debug(DEBUG_ACKNOWLEDGED_EVENT_WITH_EVENT_ID, eventDto.getEventId());
	}
}
