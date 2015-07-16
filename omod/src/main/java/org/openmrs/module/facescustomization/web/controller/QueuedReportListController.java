package org.openmrs.module.facescustomization.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.facescustomization.QueuedReport;
import org.openmrs.module.facescustomization.service.QueuedReportService;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * controller for View AMRS Reports page
 */
@Controller
public class QueuedReportListController {

	private static final Log log = LogFactory.getLog(QueuedReportListController.class);
/*
    @ModelAttribute("errorReports")
    public Integer getErrorReports() {
        return Context.getService(QueuedReportService.class).getQueuedReportsWithStatus("ERROR").size();
    }

    @ModelAttribute("newReports")
    public Integer getNewReports() {
        return Context.getService(QueuedReportService.class).getQueuedReportsWithStatus("NEW").size();
    }

    @ModelAttribute("completeReports")
    public Integer getCompletedReports() {
        return Context.getService(QueuedReportService.class).getQueuedReportsWithStatus("COMPLETE").size();
    }*/

	@RequestMapping(method = RequestMethod.GET, value = "module/facescustomization/queuedReport.list")
	public String preparePage() {
		return "module/facescustomization/queuedReportList";
	}

	@RequestMapping(value = "/module/facescustomization/downloadxls")
	public void downloadXLS(HttpServletResponse response,
	                        @RequestParam(required = true, value = "reportId") Integer reportId) throws IOException {

		if (reportId == null) {
			// TODO say something ...
			return;
		}

		QueuedReport report = Context.getService(QueuedReportService.class).getQueuedReport(reportId);

		if (report == null) {
			// TODO say something ...
			return;
		}

		String folderName = Context.getAdministrationService().getGlobalProperty("facescustomization.file_dir");

		File fileDir = OpenmrsUtil.getDirectoryInApplicationDataDirectory(folderName);
		File amrsFileToDownload = new File(fileDir, report.getXlsFilename());

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + report.getXlsFilename());
		response.setContentLength((int) amrsFileToDownload.length());

		FileCopyUtils.copy(new FileInputStream(amrsFileToDownload), response.getOutputStream());
	}

}
