/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.jpa.QuickJpaVendorAdapter
 *  com.sap.b1.noti.config.JpaConfig
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration
 *  org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter
 *  org.springframework.transaction.jta.JtaTransactionManager
 */
package com.sap.b1.noti.config;

import com.sap.b1.infra.jpa.QuickJpaVendorAdapter;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
public class JpaConfig
extends JpaBaseConfiguration {
    JpaConfig(DataSource dataSource, JpaProperties properties, ObjectProvider<JtaTransactionManager> jtaTransactionManager) {
        super(dataSource, properties, jtaTransactionManager);
    }

    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new QuickJpaVendorAdapter();
    }

    protected Map<String, Object> getVendorProperties() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        return map;
    }
}

