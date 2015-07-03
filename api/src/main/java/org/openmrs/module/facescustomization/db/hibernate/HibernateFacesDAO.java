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
package org.openmrs.module.facescustomization.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.openmrs.Location;
import org.openmrs.module.facescustomization.db.FacesDAO;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * It is a default implementation of  {@link FacesDAO}.
 */
public class HibernateFacesDAO implements FacesDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }
    
	/**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }

    @Transactional
    @Override
    public String removeSyncDuplicates() {

        String syncTable = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'openmrs' AND table_name in('sync_server_record', 'sync_record');";
        String q = "SELECT MIN(record_id) as record_id FROM sync_record s group by original_uuid having count(*)>1;";
        String delete_sync_server_record = " Delete from sync_server_record where record_id in (:recordIds);";
        String delete_sync_record = " Delete from sync_record where record_id in (:recordIds);";
        String result = "";

        SQLQuery checkSyncInstallation = sessionFactory.getCurrentSession().createSQLQuery(syncTable);
        List noOfSyncTables = checkSyncInstallation.list();
        Integer res = ((BigInteger)noOfSyncTables.get(0)).intValue();
        if (res == 0){
            return "Sync Module is not installed. Please install it if needed";
        }

        SQLQuery selectDuplicatesQuery = sessionFactory.getCurrentSession().createSQLQuery(q);
        List<Integer> duplicateIds = selectDuplicatesQuery.list();

        if (duplicateIds.isEmpty()) {
            return "No duplicates were found.";
        }
        SQLQuery deleteDuplicateSyncServerRecordQuery = sessionFactory.getCurrentSession().createSQLQuery(delete_sync_server_record);
        SQLQuery deleteDuplicateSyncRecordQuery = sessionFactory.getCurrentSession().createSQLQuery(delete_sync_record);

        deleteDuplicateSyncServerRecordQuery.setParameter("recordIds", duplicateIds);
        deleteDuplicateSyncRecordQuery.setParameter("recordIds", duplicateIds);

        int syncServerRecordResult = deleteDuplicateSyncServerRecordQuery.executeUpdate();
        result += (syncServerRecordResult>=0)? syncServerRecordResult + " duplicates in sync_server_record successfully removed\n": "Could not remove sync_server_record duplicates\n";

        int syncRecordResult = deleteDuplicateSyncRecordQuery.executeUpdate();
        result += (syncRecordResult>=0)? syncRecordResult + " duplicates in sync_record successfully removed\n": "Could not remove sync_record duplicates\n";
        return result;
    }

    @Transactional
    @Override
    public Map<Location, String> checkSyncStatus() {
        Query query = sessionFactory.getCurrentSession().createQuery("");
        return null;
    }
}