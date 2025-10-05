/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.db.DBType
 *  com.sap.b1.db.DBTypeConfig
 *  com.sap.b1.servlets.service.anly.AnalyticsViewAdapter
 *  com.sap.b1.tcli.observer.feign.B1ahFeign
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.servlets.service.anly;

import com.sap.b1.db.DBType;
import com.sap.b1.db.DBTypeConfig;
import com.sap.b1.tcli.observer.feign.B1ahFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsViewAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AnalyticsViewAdapter.class);
    @Autowired
    private B1ahFeign b1ahFeign;
    @Autowired
    private DBTypeConfig dbTypeConfig;

    public boolean isB1ahEnabled() {
        if (this.dbTypeConfig.get() != DBType.HANA) {
            return false;
        }
        try {
            this.b1ahFeign.getES();
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
    }
}

