package com.sap.cloud.samples.ariba.discovery.rfx.scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scheduler handler. Manages a job triggering.
 *
 */
public class EventsScheduler {

	private static final String DEBUG_INITIALIZING_EVENTS_SCHEDULER_HANDLER_INSTANCE_MESSAGE = "Initializing events scheduler handler instance...";
	private static final String DEBUG_EVENTS_SCHEDULER_HANDLER_INSTANCE_IS_INITIALIZED_MESSAGE = "Events scheduler handler instance is initialized.";
	private static final String DEBUG_JOBS_INIT_STARTING_MESSAGE = "Starting jobs initialization...";
	private static final String DEBUG_JOBS_INIT_DONE_MESSAGE = "Jobs initialization done.";
	private static final String DEBUG_JOBS_SHUTDOWN_STARTING_MESSAGE = "Starting jobs shutdown...";
	private static final String DEBUG_JOBS_SHUTDOWN_DONE_MESSAGE = "Starting jobs done.";
	private static final String DEBUG_SCHEDULER_IS_ALREADY_STARTED_MESSAGE = "Scheduler is already started. Skipping starting and scheduling jobs again.";

	private static final Logger logger = LoggerFactory.getLogger(EventsScheduler.class);

	private static EventsScheduler instance;
	private Scheduler scheduler;

	public static EventsScheduler getInstance() throws SchedulerException {
		if (EventsScheduler.instance == null) {
			synchronized (EventsScheduler.class) {
				if (EventsScheduler.instance == null) {
					logger.debug(DEBUG_INITIALIZING_EVENTS_SCHEDULER_HANDLER_INSTANCE_MESSAGE);
					
					EventsScheduler.instance = new EventsScheduler();

					logger.debug(DEBUG_EVENTS_SCHEDULER_HANDLER_INSTANCE_IS_INITIALIZED_MESSAGE);
				}
			}
		}

		return EventsScheduler.instance;
	}

	private EventsScheduler() throws SchedulerException {
		this.scheduler = StdSchedulerFactory.getDefaultScheduler();
	}

	public synchronized void startAndSchedule(Integer jobIntervalSeconds) throws SchedulerException {
		if (!scheduler.isStarted()) {
			logger.debug(DEBUG_JOBS_INIT_STARTING_MESSAGE);

			scheduler.start();

			JobDetail job = JobBuilder.newJob(FetchEventsJob.class).build();
			Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(
					SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(jobIntervalSeconds).repeatForever())
					.build();

			scheduler.scheduleJob(job, trigger);

			logger.debug(DEBUG_JOBS_INIT_DONE_MESSAGE);
		} else {
			logger.debug(DEBUG_SCHEDULER_IS_ALREADY_STARTED_MESSAGE);
		}
	}

	/**
	 * Stop scheduler
	 *
	 * @throws SchedulerException
	 *             if scheduler is not stopped
	 */
	public void stop() throws SchedulerException {
		logger.debug(DEBUG_JOBS_SHUTDOWN_STARTING_MESSAGE);

		scheduler.shutdown();

		logger.debug(DEBUG_JOBS_SHUTDOWN_DONE_MESSAGE);
	}
}
