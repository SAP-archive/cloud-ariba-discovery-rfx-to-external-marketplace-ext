package com.sap.cloud.samples.ariba.discovery.rfx;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.samples.ariba.discovery.rfx.connectivity.PublicSourcingDestination;
import com.sap.cloud.samples.ariba.discovery.rfx.scheduler.EventsScheduler;

/**
 * Context listener for receiving notification events about ServletContext
 * lifecycle changes.
 * 
 * Initializes the application.
 *
 */
public class PublicSourcingContextListener implements ServletContextListener {

	private static final String DEBUG_INITIALIZING_APPLICATION = "Initializing application...";
	private static final String DEBUG_APPLICATION_IS_INITIALIZED = "Application is initialized.";
	private static final String DEBUG_RETRIEVING_DESTINATION = "Retrieving [{}] destination...";
	private static final String DEBUG_RETRIEVED_DESTINATION = "Retrieved [{}] destination.";
	private static final String DEBUG_INITIALIZING_SCHEDULER = "Initializing scheduler...";
	private static final String DEBUG_SCHEDULER_INITIALIZED = "Scheduler is initialized.";
	private static final String DEBUG_STOPPING_SCHEDULER = "Stopping scheduler...";
	private static final String DEBUG_SCHEDULER_STOPPED = "Scheduler is stopped.";
	private static final String ERROR_OCCURED_WHILE_INITIALIZING_APPLICATION_MESSAGE = "Error occurred while initializing application: {}";
	private static final String ERROR_OCCURED_WHILE_CLEANING_UP_MESSAGE = "Error occurred while cleaning up: {}";

	private static final Logger logger = LoggerFactory.getLogger(PublicSourcingContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			logger.debug(DEBUG_INITIALIZING_APPLICATION);

			// scheduleEventsRetrieving();

			logger.debug(DEBUG_APPLICATION_IS_INITIALIZED);
		} catch (Exception e) {
			logger.error(ERROR_OCCURED_WHILE_INITIALIZING_APPLICATION_MESSAGE, e);
			throw new RuntimeException(e);
		}
	}

	private void scheduleEventsRetrieving() throws SchedulerException {
		logger.debug(DEBUG_RETRIEVING_DESTINATION, PublicSourcingDestination.NAME);
		PublicSourcingDestination aribaDestination = new PublicSourcingDestination(PublicSourcingDestination.NAME);
		logger.debug(DEBUG_RETRIEVED_DESTINATION, PublicSourcingDestination.NAME);

		logger.debug(DEBUG_INITIALIZING_SCHEDULER);
		EventsScheduler eventsScheduler = EventsScheduler.getInstance();
		eventsScheduler.startAndSchedule(aribaDestination.getJobIntervalSeconds());
		logger.debug(DEBUG_SCHEDULER_INITIALIZED);
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		try {
			// stopScheduledEventsRetrieving();
		} catch (Exception e) {
			logger.error(ERROR_OCCURED_WHILE_CLEANING_UP_MESSAGE, e);
		}
	}

	private void stopScheduledEventsRetrieving() throws SchedulerException {
		logger.debug(DEBUG_STOPPING_SCHEDULER);

		EventsScheduler eventsScheduler = EventsScheduler.getInstance();
		eventsScheduler.stop();

		logger.debug(DEBUG_SCHEDULER_STOPPED);
	}

}
