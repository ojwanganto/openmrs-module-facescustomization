package org.openmrs.module.facescustomization.reporting.cohort.definition;

import org.openmrs.Location;
import org.openmrs.module.reporting.cohort.definition.BaseCohortDefinition;
import org.openmrs.module.reporting.common.Localized;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;
import org.openmrs.module.reporting.definition.configuration.ConfigurationPropertyCachingStrategy;
import org.openmrs.module.reporting.evaluation.caching.Caching;

/**
 * TODO:please provide a brief description for the class.
 */
@Caching(strategy = ConfigurationPropertyCachingStrategy.class)
@Localized("reporting.AMRSReportsCohortDefinition")
public class AMRSReportsCohortDefinition extends BaseCohortDefinition {

    @ConfigurationProperty
    private Location facility;

    public AMRSReportsCohortDefinition() {
        super();
    }

    public AMRSReportsCohortDefinition(Location facility) {
        super();
        this.facility = facility;
    }

    public Location getFacility() {
        return facility;
    }

    public void setFacility(Location facility) {
        this.facility = facility;
    }
}