/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.connection.IAnalyticsDataSource
 *  com.sap.sme.anw.analytics.sql.common.DbType
 *  com.sap.sme.anw.analytics.thin.connection.AnalyticsDataSource
 *  com.sap.sme.anw.analytics.thin.springcontext.AppContext
 *  org.apache.tomcat.jdbc.pool.DataSource
 */
package com.sap.sme.anw.analytics.thin.connection;

import com.sap.sme.anw.analytics.core.connection.IAnalyticsDataSource;
import com.sap.sme.anw.analytics.sql.common.DbType;
import com.sap.sme.anw.analytics.thin.springcontext.AppContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.tomcat.jdbc.pool.DataSource;

public class AnalyticsDataSource
implements IAnalyticsDataSource {
    private static final Map<String, DataSource> dataSources = new ConcurrentHashMap();

    public javax.sql.DataSource getDataSourceById(String dataSourceId) {
        if (dataSourceId == null) {
            return (javax.sql.DataSource)AppContext.getContext().getBean(javax.sql.DataSource.class);
        }
        DataSource dataSource = (DataSource)dataSources.get(dataSourceId);
        if (dataSource != null) {
            return dataSource;
        }
        String dbDriver = System.getProperty("DB_DRIVER/" + dataSourceId);
        String databaseName = System.getProperty("DB_DBNAME/" + dataSourceId);
        String host = System.getProperty("DB_HOST/" + dataSourceId);
        String port = System.getProperty("DB_PORT/" + dataSourceId);
        String username = System.getProperty("DB_USR/" + dataSourceId);
        String password = System.getProperty("DB_PWD/" + dataSourceId);
        DbType dbType = DbType.fromDriverClassName((String)dbDriver);
        String connectionStr = dbType.getConnectionStr(host, port, databaseName);
        dataSource = new DataSource();
        dataSource.setDriverClassName(dbType.getDriverClassName());
        dataSource.setUrl(connectionStr);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(500);
        dataSource.setMaxIdle(200);
        dataSources.put(dataSourceId, dataSource);
        return dataSource;
    }
}

