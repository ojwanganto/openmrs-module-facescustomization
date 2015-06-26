package org.openmrs.module.facescustomization.db;

import org.openmrs.Location;
import org.openmrs.module.facescustomization.QueuedReport;

import java.util.Date;
import java.util.List;

/**
 * DAO for QueuedReport objects
 */
public interface QueuedReportDAO {

	public QueuedReport saveQueuedReport(QueuedReport queuedReport);

	public QueuedReport getNextQueuedReport(Date date);

	public void purgeQueuedReport(QueuedReport queuedReport);

	public List<QueuedReport> getAllQueuedReports();

	public List<QueuedReport> getQueuedReportsWithStatus(String status);

	public QueuedReport getQueuedReport(Integer reportId);

	public List<QueuedReport> getQueuedReportsByFacilities(List<Location> allowedFacilities, String status);

}
