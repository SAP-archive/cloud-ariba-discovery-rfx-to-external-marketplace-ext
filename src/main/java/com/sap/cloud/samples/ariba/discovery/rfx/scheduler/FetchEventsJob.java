package com.sap.cloud.samples.ariba.discovery.rfx.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Job used to retrieve and persist Public Sourcing events from Ariba.
 *
 */
public class FetchEventsJob implements Job {

	private static final String DEBUG_FETCH_EVENTS_JOB_EXECUTING_MESSAGE = "Executing Fetch Events Job...";
	private static final String DEBUG_FETCH_EVENTS_JOB_EXECUTED_SUCCESSFULLY_MESSAGE = "Fetch Events Job executed successfully.";
	private static final String DEBUG_FETCH_STARTING_MESSAGE = "Started fetching events...";
	private static final String DEBUG_FETCH_DONE_MESSAGE = "Done fetching events.";

	private static final String ERROR_FAILED_TO_FETCH_EVENTS_MESSAGE = "Failed to fetch events.";

	private static final Logger logger = LoggerFactory.getLogger(FetchEventsJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.debug(DEBUG_FETCH_EVENTS_JOB_EXECUTING_MESSAGE);

		try {
			processEvents();

			logger.debug(DEBUG_FETCH_EVENTS_JOB_EXECUTED_SUCCESSFULLY_MESSAGE);
		} catch (Exception e) {
			logger.error(ERROR_FAILED_TO_FETCH_EVENTS_MESSAGE, e);
		}
	}

	private void processEvents() throws EventProcessingException {
		logger.debug(DEBUG_FETCH_STARTING_MESSAGE);

		PublicSourcingEventsProcessor eventsProcessor = PublicSourcingEventsProcessor.createEventProcessor();
		eventsProcessor.processEvents();

		logger.debug(DEBUG_FETCH_DONE_MESSAGE);
	}

}
