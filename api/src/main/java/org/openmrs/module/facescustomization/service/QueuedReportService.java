package org.openmrs.module.facescustomization.service;

import org.openmrs.Location;
import org.openmrs.module.facescustomization.QueuedReport;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * Service for dealing with queued reports
 */
public interface QueuedReportService {

	@Transactional(readOnly = true)
	public QueuedReport getNextQueuedReport();

	@Transactional
	void processQueuedReport(QueuedReport queuedReport) throws EvaluationException, IOException;

	@Transactional
	public QueuedReport saveQueuedReport(QueuedReport queuedReport);

	@Transactional
	public void purgeQueuedReport(QueuedReport queuedReport);

	@Transactional(readOnly = true)
	public List<QueuedReport> getAllQueuedReports();

	@Transactional(readOnly = true)
	public List<QueuedReport> getQueuedReportsWithStatus(String status);

	@Transactional(readOnly = true)
	public QueuedReport getQueuedReport(Integer reportId);

	@Transactional(readOnly = true)
	public List<QueuedReport> getQueuedReportsByFacilities(List<Location> facilities, String status);

}
