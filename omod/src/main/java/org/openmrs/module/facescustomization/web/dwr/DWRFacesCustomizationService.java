package org.openmrs.module.facescustomization.web.dwr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.facescustomization.service.FacesService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DWR service for faces customization module web pages
 */
public class DWRFacesCustomizationService {

	private static final Log log = LogFactory.getLog(DWRFacesCustomizationService.class);
	public String purgeSyncDuplicates() {

        String syncStatus = Context.getService(FacesService.class).removeSyncDuplicates();

        List sampleList = new ArrayList(Arrays.asList(10, 20, 30, 40));
        return syncStatus;
	}

}
