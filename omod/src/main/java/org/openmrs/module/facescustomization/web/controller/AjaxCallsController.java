package org.openmrs.module.facescustomization.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "module/facescustomization/ajaxCalls")
public class AjaxCallsController {

    private final Log log = LogFactory.getLog(this.getClass());

    @RequestMapping(value="removeDuplicates.jsp", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> removeDuplicates() {
        List sampleList = new ArrayList(Arrays.asList(10,20,30,40));
        return sampleList;
    }


}