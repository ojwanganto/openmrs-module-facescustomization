package org.openmrs.module.facescustomization.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.scheduler.tasks.AbstractTask;

/**
 * Abstract task for AMRS Reports tasks where we want only one to run at a time
 */
public abstract class FacesCustomReportsTask extends AbstractTask {

	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * common execute method, framing the actual execution code by checking for a lock and opening a session
	 */
	public void execute() {

		if (FacesCustomReportsCommonTaskLock.getInstance().isLocked()) {
			return;
		}

		if (!FacesCustomReportsCommonTaskLock.getInstance().getLock(this.getClass())) {
			return;
		}

		try {
			this.doExecute();
		} catch (Exception e) {
			log.error("Error running AMRS reports task", e);
			throw new APIException("Error running AMRS reports task", e);
		} finally {
			if (!FacesCustomReportsCommonTaskLock.getInstance().releaseLock(this.getClass()))
				log.error("Could not release lock.");

		}
	}

	/**
	 * the actual execution method
	 */
	protected abstract void doExecute() throws APIException;

}
