/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.sme.anw.analytics.core.common.Common
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLog
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel
 *  com.sap.sme.anw.analytics.thin.odata.ODataServiceUtils
 *  com.sap.sme.anw.analytics.type.model.ModelListResponse
 *  com.sap.sme.anw.analytics.type.model.ModelObject
 */
package com.sap.sme.anw.analytics.thin.odata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.sme.anw.analytics.core.common.Common;
import com.sap.sme.anw.analytics.core.log.AnalyticsLog;
import com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel;
import com.sap.sme.anw.analytics.type.model.ModelListResponse;
import com.sap.sme.anw.analytics.type.model.ModelObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ODataServiceUtils {
    public static final String NAMESPACE_DATASOURCE = "DataSource";
    public static final String ENTITY_CONTAINER_DATASOURCE = "EntityContainerDataSource";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<ModelObject> getDataSourceModelObjects() {
        ArrayList<ModelObject> dataSourceModelObjects = new ArrayList<ModelObject>();
        ModelListResponse modelListResponse = null;
        try {
            modelListResponse = (ModelListResponse)objectMapper.readValue(Common.readResourceFile((String)"meta/datasource/models.json"), ModelListResponse.class);
        }
        catch (IOException e) {
            AnalyticsLog.log(ODataServiceUtils.class, (AnalyticsLogLevel)AnalyticsLogLevel.DEBUG, (Throwable)e);
        }
        List modelObjects = modelListResponse.getModelObjects();
        if (modelObjects != null) {
            dataSourceModelObjects.addAll(modelObjects);
        }
        return dataSourceModelObjects;
    }
}

