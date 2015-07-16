/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

/*
* This class serves requests for reports (queued,running, completed and failed)
*
* */

package org.openmrs.module.facescustomization.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.facescustomization.QueuedReport;
import org.openmrs.module.facescustomization.service.QueuedReportService;
import org.openmrs.web.controller.PortletController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("**/queuedSystemReports.portlet")
public class QueuedSystemReportsPortletController extends PortletController {

    private final Log log = LogFactory.getLog(this.getClass());
	/**
	 * @see org.openmrs.web.controller.PortletController#populateModel(javax.servlet.http.HttpServletRequest,
	 *      java.util.Map)
	 */

	@Override
	protected void populateModel(HttpServletRequest request, Map<String, Object> model) {

		String status = (String) model.get("status");

		Map<Location, List<QueuedReport>> queuedReportsMap = new LinkedHashMap<Location, List<QueuedReport>>();

		if (Context.isAuthenticated() && status != null) {

			List<QueuedReport> reports = Context.getService(QueuedReportService.class).getQueuedReportsWithStatus(status);

			for (QueuedReport thisReport : reports) {
				Location thisFacility = thisReport.getFacility();

				if (!queuedReportsMap.containsKey(thisFacility))
					queuedReportsMap.put(thisFacility, new ArrayList<QueuedReport>());

				queuedReportsMap.get(thisFacility).add(thisReport);
			}
		}

		model.put("queuedReportsMap", queuedReportsMap);

		// date time format -- needs to come from here because we can make it locale-specific
		// TODO extract this to a utility if used more than once

		SimpleDateFormat sdf = Context.getDateFormat();
		String format = sdf.toPattern();
		format += " hh:mm a";

		model.put("datetimeFormat", format);
	}
}