/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.container.IAnalyticsContainer
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.thin.container.AnalyticsContainer
 *  com.sap.sme.anw.analytics.thin.container.AnalyticsContainer$1
 */
package com.sap.sme.anw.analytics.thin.container;

import com.sap.sme.anw.analytics.core.container.IAnalyticsContainer;
import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.thin.container.AnalyticsContainer;

public class AnalyticsContainer
implements IAnalyticsContainer {
    public String getContainerDatabaseName(IContextData contextData) {
        switch (1.$SwitchMap$com$sap$sme$anw$analytics$sql$common$DbType[contextData.getDbType().ordinal()]) {
            case 1: {
                return null;
            }
            case 2: {
                return contextData.getAnalyticsSchema();
            }
        }
        return null;
    }

    public String getContainerSchemaName(IContextData contextData) {
        switch (1.$SwitchMap$com$sap$sme$anw$analytics$sql$common$DbType[contextData.getDbType().ordinal()]) {
            case 1: {
                return contextData.getAnalyticsSchema();
            }
            case 2: {
                return "dbo";
            }
        }
        return null;
    }
}

