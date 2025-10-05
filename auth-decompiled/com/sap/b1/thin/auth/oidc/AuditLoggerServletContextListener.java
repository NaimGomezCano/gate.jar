/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.sdk.logger.db.DataSourceProvider
 *  com.sap.b1.sdk.logger.db.DataSourceProviderRegister
 *  com.sap.b1.thin.auth.oidc.AuditLoggerServletContextListener
 *  jakarta.servlet.ServletContextEvent
 *  jakarta.servlet.ServletContextListener
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.thin.auth.oidc;

import com.sap.b1.sdk.logger.db.DataSourceProvider;
import com.sap.b1.sdk.logger.db.DataSourceProviderRegister;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditLoggerServletContextListener
implements ServletContextListener {
    @Autowired
    private DataSourceProvider datasourceProviderImpl;

    public void contextInitialized(ServletContextEvent sce) {
        DataSourceProviderRegister.register((DataSourceProvider)this.datasourceProviderImpl);
    }
}

