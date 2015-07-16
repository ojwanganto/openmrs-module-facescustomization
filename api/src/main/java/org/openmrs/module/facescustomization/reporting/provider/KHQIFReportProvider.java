package org.openmrs.module.facescustomization.reporting.provider;

import org.apache.commons.io.IOUtils;
import org.openmrs.Location;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.facescustomization.reporting.cohort.definition.Moh361ACohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.converter.PropertyConverter;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PreferredNameDataDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.ReportDesignResource;
import org.openmrs.module.reporting.report.definition.PeriodIndicatorReportDefinition;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.renderer.ExcelTemplateRenderer;
import org.openmrs.util.OpenmrsClassLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Provides mechanisms for rendering the MOH 361A Pre-ART Register
 */
public class KHQIFReportProvider extends ReportProvider {

	public static final String NAME = "KHQIF Report";

	public KHQIFReportProvider() {
		this.name = "KHQIF Report";
		this.visible = true;
	}

	@Override
	public ReportDefinition getReportDefinition() {

		String nullString = null;
		ObjectFormatter nullStringConverter = new ObjectFormatter();
		//MohCoreService service = Context.getService(MohCoreService.class);

		ReportDefinition report = new PeriodIndicatorReportDefinition();
		report.setName("MOH 361A Report");

		// set up the DSD
		PatientDataSetDefinition dsd = new PatientDataSetDefinition();
		dsd.setName("allPatients");

		// set up parameters
		Parameter facility = new Parameter();
		facility.setName("facility");
		facility.setType(Location.class);

		// add to report and data set definition
		report.addParameter(facility);
		dsd.addParameter(facility);

/*		// sort by transfer status (transfers go last), then by date, then serial number
		dsd.addSortCriteria("Transfer Status", SortCriteria.SortDirection.ASC);
		dsd.addSortCriteria("Date Chronic HIV Care Started", SortCriteria.SortDirection.ASC);
		dsd.addSortCriteria("Serial Number", SortCriteria.SortDirection.ASC);

		// set up the columns ...

		// patient id ... until we get this thing working proper
		dsd.addColumn("Person ID", new PersonIdDataDefinition(), nullString);

		// a. serial number
		dsd.addColumn("Serial Number", new SerialNumberDataDefinition(), "facility=${facility}");

		// b. date chronic HIV+ care started
		EnrollmentDateDataDefinition enrollmentDate = new EnrollmentDateDataDefinition();
		dsd.addColumn("Date Chronic HIV Care Started", enrollmentDate, nullString);

		// extra column to help understand reason for including in this cohort
		dsd.addColumn("First Encounter Date At Facility", new FirstEncounterAtFacilityDataDefinition(),
				"facility=${facility}", new EncounterDatetimeConverter());

		// c. Unique Patient Number
		PatientIdentifierType pit = service.getCCCNumberIdentifierType();
		PatientIdentifierDataDefinition cccColumn = new PatientIdentifierDataDefinition("CCC", pit);
		cccColumn.setIncludeFirstNonNullOnly(true);
		dsd.addColumn("Unique Patient Number", cccColumn, nullString,
				new PropertyConverter(PatientIdentifier.class, "identifier"));*/

		// AMRS Universal ID
		PatientIdentifierDataDefinition uidColumn = new PatientIdentifierDataDefinition(
				"AMRS Universal ID", Context.getPatientService().getPatientIdentifierType(8));
		uidColumn.setIncludeFirstNonNullOnly(true);
		dsd.addColumn("AMRS Universal ID", uidColumn, nullString,
				new PropertyConverter(PatientIdentifier.class, "identifier"));

		// AMRS Medical Record Number
		PatientIdentifierDataDefinition mrnColumn = new PatientIdentifierDataDefinition(
				"AMRS Medical Record Number", Context.getPatientService().getPatientIdentifierType(3));
		mrnColumn.setIncludeFirstNonNullOnly(true);
		dsd.addColumn("AMRS Medical Record Number", mrnColumn, nullString,
				new PropertyConverter(PatientIdentifier.class, "identifier"));

		// d. Patient's Name
		dsd.addColumn("Name", new PreferredNameDataDefinition(), nullString);

		// e1. Date of Birth
		dsd.addColumn("Date of Birth", new BirthdateDataDefinition(), nullString,
				new BirthdateConverter("yyyy-mm-dd"/*MOHReportUtil.DATE_FORMAT*/));

		// e2. Age at Enrollment
		/*MappedData<EnrollmentDateDataDefinition> mappedDef = new MappedData<EnrollmentDateDataDefinition>();
		mappedDef.setParameterizable(enrollmentDate);
		mappedDef.addConverter(new DateConverter());
		AgeAtDateOfOtherDataDefinition ageAtEnrollment = new AgeAtDateOfOtherDataDefinition();
		ageAtEnrollment.setEffectiveDateDefinition(mappedDef);
		dsd.addColumn("Age at Enrollment", ageAtEnrollment, nullString, new DecimalAgeConverter(2));*/

		// f. Sex
		dsd.addColumn("Sex", new GenderDataDefinition(), nullString);

/*		// g. Entry point: From where?
		PersonAttributeType pat = Context.getPersonService().getPersonAttributeTypeByName(MohEvaluableNameConstants.POINT_OF_HIV_TESTING);
		dsd.addColumn("Entry Point", new PersonAttributeDataDefinition("entryPoint", pat), nullString, new EntryPointConverter());

		// h. Confirmed HIV+ Date
		dsd.addColumn("Confirmed HIV Date", enrollmentDate, nullString);

		// k. CTX startdate and stopdate:
		dsd.addColumn("CTX Start Stop Date", new CtxStartStopDataDefinition(), nullString);

		// l. Fluconazole startdate and stopdate
		dsd.addColumn("Fluconazole Start Stop Date", new FluconazoleStartStopDataDefinition(), nullString);

		// m. TB treatment startdate and stopdate
		dsd.addColumn("TB Treatment Start Stop Date", new TbStartStopDataDefinition(), nullString);

		// n. Pregnancy Yes?, Due date, PMTCT refer
		dsd.addColumn("Pregnancy EDD and Referral", new PmtctPregnancyDataDefinition(), nullString, new FormattedDateSetConverter());

		// o. LTFU / TO / Dead and date when the event occurred
		dsd.addColumn("LTFU TO DEAD", new LTFUTODeadDataDefinition(), nullString, nullStringConverter);

		// p. WHO clinical Stage and date
		dsd.addColumn("WHO Clinical Stage", new FirstWHOStageDataDefinition(), nullString, new WHOStageAndDateConverter());

		// q. Date medically eligible for ART
		EligibilityForARTDataDefinition eligibility = new EligibilityForARTDataDefinition();
		dsd.addColumn("Date Medically Eligible for ART", eligibility, nullString, new ARVPatientSnapshotDateConverter());

		// r. Reason Medically Eligible for ART
		dsd.addColumn("Reason Medically Eligible for ART", eligibility, nullString, new ARVPatientSnapshotReasonConverter());

		// s. Date ART started (Transfer to ART register)
		dsd.addColumn("Date ART Started", new DateARTStartedDataDefinition(), nullString);

		// additional columns for troubleshooting
		LastHIVEncounterDataDefinition lastHIVEncounter = new LastHIVEncounterDataDefinition();

		dsd.addColumn("Last HIV Encounter Date", lastHIVEncounter, nullString,
				new PropertyConverter(EncounterRepresentation.class, "encounterDatetime"));

		dsd.addColumn("Last HIV Encounter Location", lastHIVEncounter, nullString,
				new PropertyConverter(EncounterRepresentation.class, "locationName"));

		// informative column for the destination clinics
		dsd.addColumn("Last Return to Clinic Date", new LastRTCDateDataDefinition(), nullString,
				new PropertyConverter(ObsRepresentation.class, "valueDatetime"));

		// transfer status column (used for sorting, not needed in output)
		dsd.addColumn("Transfer Status", new TransferStatusDataDefinition(), "facility=${facility}");*/

		Map<String, Object> mappings = new HashMap<String, Object>();
		mappings.put("facility", "${facility}");
		report.addDataSetDefinition(dsd, mappings);

		return report;
	}

	@Override
	public CohortDefinition getCohortDefinition() {
		return new Moh361ACohortDefinition();
	}

	@Override
	public ReportDesign getReportDesign() {
		ReportDesign design = new ReportDesign();
		design.setName("MOH 361A Register Design");
		design.setReportDefinition(this.getReportDefinition());
		design.setRendererType(ExcelTemplateRenderer.class);

		Properties props = new Properties();
		props.put("repeatingSections", "sheet:1,row:4,dataset:allPatients");

		design.setProperties(props);

		ReportDesignResource resource = new ReportDesignResource();
		resource.setName("template.xls");
		InputStream is = OpenmrsClassLoader.getInstance().getResourceAsStream("templates/MOH361AReportTemplate_0_1.xls");

		if (is == null)
			throw new APIException("Could not find report template.");

		try {
			resource.setContents(IOUtils.toByteArray(is));
		} catch (IOException ex) {
			throw new APIException("Could not create report design for MOH 361A Register.", ex);
		}

		IOUtils.closeQuietly(is);
		design.addResource(resource);

		return design;
	}
}
