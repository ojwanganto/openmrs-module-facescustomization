package org.openmrs.module.facescustomization.task;

import org.openmrs.module.facescustomization.ReportQueueProcessor;

/**
 * Scheduled task for running queued reports
 */
public class RunQueuedReportsTask extends FacesCustomReportsTask {

	// Instance of processor
	private static ReportQueueProcessor processor = null;

	/**
	 * Default Constructor (Uses SchedulerConstants.username and SchedulerConstants.password
	 */
	public RunQueuedReportsTask() {
		if (processor == null) {
			processor = new ReportQueueProcessor();
		}
	}

	/**
	 * Process the next queued item
	 */
	public void doExecute() {
		processor.processQueuedReports();
	}

}