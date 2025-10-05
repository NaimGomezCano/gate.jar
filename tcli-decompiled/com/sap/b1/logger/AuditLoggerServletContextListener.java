/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.logger.AuditLoggerServletContextListener
 *  com.sap.b1.logger.AuditLoggerUtil
 *  com.sap.b1.sdk.logger.db.DataSourceProvider
 *  com.sap.b1.sdk.logger.db.DataSourceProviderRegister
 *  jakarta.servlet.ServletContextEvent
 *  jakarta.servlet.ServletContextListener
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.logger;

import com.sap.b1.logger.AuditLoggerUtil;
import com.sap.b1.sdk.logger.db.DataSourceProvider;
import com.sap.b1.sdk.logger.db.DataSourceProviderRegister;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuditLoggerServletContextListener
implements ServletContextListener {
    @Autowired
    private DataSourceProvider datasourceProviderImpl;
    @Value(value="${spring.profiles.active:default}")
    private String activeProfile;

    public void contextInitialized(ServletContextEvent sce) {
        AuditLoggerUtil.setTestProfile((String)this.activeProfile);
        DataSourceProviderRegister.register((DataSourceProvider)this.datasourceProviderImpl);
    }
}

