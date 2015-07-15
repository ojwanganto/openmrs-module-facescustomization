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
package org.openmrs.module.facescustomization.extension.html;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openmrs.api.context.Context;
import org.openmrs.module.Extension;
import org.openmrs.module.reporting.ReportingConstants;
import org.openmrs.module.web.extension.AdministrationSectionExt;
import org.openmrs.util.PrivilegeConstants;

/**
 * This class defines the links that will appear on the administration page under the
 * "facescustomization.title" heading. 
 */
public class AdminList extends AdministrationSectionExt {
	
	/**
	 * @see AdministrationSectionExt#getMediaType()
	 */
	public Extension.MEDIA_TYPE getMediaType() {
		return Extension.MEDIA_TYPE.html;
	}
	
	/**
	 * @see AdministrationSectionExt#getTitle()
	 */
	public String getTitle() {
		return "facescustomization.title";
	}
	
	/**
	 * @see AdministrationSectionExt#getLinks()
	 */
	public Map<String, String> getLinks() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("/module/facescustomization/manage.form", "facescustomization.manage");
        map.put("/module/facescustomization/syncStatus.list", "facescustomization.syncStatus");
        map.put("module/facescustomization/queuedReport.list", "Manage Facility Reports");
		return map;
	}
   /* public Map<String, String> getLinks() {
        Map<String, String> map = new LinkedHashMap<String, String>();

        if (Context.hasPrivilege(ReportingConstants.PRIV_VIEW_REPORTS)) {
            map.put("module/amrsreports/queuedReport.list", "Manage AMRS Reports");
        }

        if (Context.hasPrivilege(PrivilegeConstants.VIEW_LOCATIONS)) {
            map.put("module/amrsreports/cccNumbers.list", "Manage CCC Numbers");
            map.put("module/amrsreports/facility.list", "Manage MOH Facilities");

            if (Context.hasPrivilege(PrivilegeConstants.VIEW_USERS)) {
                map.put("module/amrsreports/facilityPrivileges.form", "Manage User/Facility Privileges");
            }

            map.put("/module/amrsreports/cohortCounts.list", "View Cohort Counts");
        }

        if (Context.hasPrivilege(PrivilegeConstants.VIEW_GLOBAL_PROPERTIES)) {
            map.put("module/amrsreports/settings.form", "Settings");
        }

        return map;
    }*/
	
}
