/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLog
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel
 *  com.sap.sme.anw.analytics.thin.rest.InitRest
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.sme.anw.analytics.thin.rest;

import com.sap.sme.anw.analytics.core.log.AnalyticsLog;
import com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/Init/v1"})
public class InitRest {
    @RequestMapping(value={"/TenantInit"}, method={RequestMethod.POST})
    public void handleTenantInitRequest() {
        AnalyticsLog.log(this.getClass(), (AnalyticsLogLevel)AnalyticsLogLevel.INFO, (String)"Tenant Initialized for Analytics");
    }
}

