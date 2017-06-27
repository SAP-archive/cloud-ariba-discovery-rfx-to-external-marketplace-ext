package com.sap.cloud.samples.ariba.discovery.rfx.daos;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.samples.ariba.discovery.rfx.persistency.entities.Event;

/**
 * Event DAO.
 *
 */
public class EventDao extends BaseDao<Event> {

	private static final String DEBUG_SEARCHING_EVENT_MESSAGE = "Searching for event with event ID [{}]...";
	private static final String DEBUG_EVENT_FOUND_MESSAGE = "Event with event id [{}] was found.";
	private static final String DEBUG_EVENT_NOT_FOUND_MESSAGE = "Event with event id [{}] was not found.";

	private static final Logger logger = LoggerFactory.getLogger(EventDao.class);

	public EventDao() {
		super(Event.class);
	}

	/**
	 * Finds event by event id.
	 *
	 * @param eventId
	 *            the event id.
	 * @return the event with the given event id or null.
	 */
	public Event findByEventId(String eventId) {
		logger.debug(DEBUG_SEARCHING_EVENT_MESSAGE, eventId);

		TypedQuery<Event> typedQuery = createNamedQuery(Event.QUERY_FIND_BY_EVENT_ID);
		typedQuery.setParameter(Event.QUERY_PARAM_FIND_BY_EVENT_ID, eventId);
		List<Event> resultList = typedQuery.getResultList();

		Event event = null;
		if (!resultList.isEmpty()) {
			event = resultList.get(0);
			logger.debug(DEBUG_EVENT_FOUND_MESSAGE, eventId);
		} else {
			logger.debug(DEBUG_EVENT_NOT_FOUND_MESSAGE, eventId);
		}

		return event;
	}

}
