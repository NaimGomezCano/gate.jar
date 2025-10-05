/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.model.context.impl.CoreAnalyticsModelContext
 *  com.sap.sme.anw.analytics.thin.model.context.AnalyticsModelContext
 *  com.sap.sme.anw.analytics.thin.model.context.AnalyticsModelContext$1
 *  com.sap.sme.anw.analytics.type.model.ModelType
 *  org.apache.commons.lang3.StringUtils
 */
package com.sap.sme.anw.analytics.thin.model.context;

import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.model.context.impl.CoreAnalyticsModelContext;
import com.sap.sme.anw.analytics.thin.model.context.AnalyticsModelContext;
import com.sap.sme.anw.analytics.type.model.ModelType;
import org.apache.commons.lang3.StringUtils;

public class AnalyticsModelContext
extends CoreAnalyticsModelContext {
    public String getPackagePrefix(IContextData contextData, ModelType modelType) {
        if (modelType == null) {
            return super.getPackagePrefix(contextData, modelType);
        }
        String tenantId = contextData.getTenantId();
        if (tenantId == null) {
            return null;
        }
        String packagePrefix = null;
        switch (1.$SwitchMap$com$sap$sme$anw$analytics$type$model$ModelType[modelType.ordinal()]) {
            case 1: 
            case 2: 
            case 3: {
                packagePrefix = "sap." + StringUtils.remove((String)StringUtils.lowerCase((String)tenantId), (char)'_');
                break;
            }
        }
        return packagePrefix;
    }
}

