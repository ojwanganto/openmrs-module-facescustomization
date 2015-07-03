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
package org.openmrs.module.facescustomization.service.impl;

import org.openmrs.Location;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.facescustomization.service.FacesService;
import org.openmrs.module.facescustomization.db.FacesDAO;

import java.util.Map;

/**
 * It is a default implementation of {@link FacesService}.
 */
public class FacesServiceImpl extends BaseOpenmrsService implements FacesService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private FacesDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(FacesDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public FacesDAO getDao() {
	    return dao;
    }

    @Override
    public String removeSyncDuplicates() {
        return dao.removeSyncDuplicates();
    }

    @Override
    public Map<Location, String> checkSyncStatus() {
        return dao.checkSyncStatus();
    }
}