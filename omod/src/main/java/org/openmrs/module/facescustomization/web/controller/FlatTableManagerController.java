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
package org.openmrs.module.facescustomization.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for flat table manager
 */
@Controller
public class FlatTableManagerController {
	
	protected final Log log = LogFactory.getLog(getClass());
    private static final String FORM_VIEW = "/module/facescustomization/flatTableManager";
	
	@RequestMapping(value = "/module/facescustomization/flatTable.form", method = RequestMethod.GET)
	public String manageFlatTable(ModelMap model) {
        return FORM_VIEW;
	}

}