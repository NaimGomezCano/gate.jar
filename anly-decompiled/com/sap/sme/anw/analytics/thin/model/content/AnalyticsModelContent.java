/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.model.Model
 *  com.sap.sme.anw.analytics.core.model.content.DataSourceUtils
 *  com.sap.sme.anw.analytics.core.model.content.impl.CoreAnalyticsModelContent
 *  com.sap.sme.anw.analytics.thin.model.content.AnalyticsModelContent
 *  com.sap.sme.anw.analytics.type.datasource.DataSourceObject
 *  com.sap.sme.anw.analytics.type.model.ModelType
 */
package com.sap.sme.anw.analytics.thin.model.content;

import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.model.Model;
import com.sap.sme.anw.analytics.core.model.content.DataSourceUtils;
import com.sap.sme.anw.analytics.core.model.content.impl.CoreAnalyticsModelContent;
import com.sap.sme.anw.analytics.type.datasource.DataSourceObject;
import com.sap.sme.anw.analytics.type.model.ModelType;

public class AnalyticsModelContent
extends CoreAnalyticsModelContent {
    public Object getContent(Model model, IContextData contextData) {
        if (model.getModelType() != ModelType.DATASOURCE) {
            return super.getContent(model, contextData);
        }
        String modelId = model.getModelId();
        DataSourceObject dataSourceObject = DataSourceUtils.readDataSource((String)modelId, (String)"meta/datasource");
        return dataSourceObject;
    }
}

