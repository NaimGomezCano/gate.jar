/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.share.feign.FeignConfiguration
 *  com.sap.b1.infra.share.feign.tcli.FeignModelInfoService
 *  com.sap.b1.infra.share.feign.tcli.FeignPermissionService
 *  com.sap.b1.infra.share.feign.tcli.FeignTileService
 *  com.sap.sme.anw.analytics.thin.springconfig.AppConfig
 *  com.sap.sme.anw.analytics.thin.springconfig.ProfileConfig
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.boot.jdbc.DataSourceBuilder
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.env.Environment
 *  org.springframework.stereotype.Component
 */
package com.sap.sme.anw.analytics.thin.springconfig;

import com.sap.b1.infra.share.feign.FeignConfiguration;
import com.sap.b1.infra.share.feign.tcli.FeignModelInfoService;
import com.sap.b1.infra.share.feign.tcli.FeignPermissionService;
import com.sap.b1.infra.share.feign.tcli.FeignTileService;
import com.sap.sme.anw.analytics.thin.springconfig.ProfileConfig;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Import(value={FeignConfiguration.class})
public class AppConfig {
    @Autowired
    private Environment env;
    @Autowired
    FeignPermissionService feignPermissionService;
    @Autowired
    FeignModelInfoService feignModelInfoService;
    @Autowired
    FeignTileService feignTileService;
    @Value(value="${spring.profiles.active:default}")
    private String activeProfile;

    @Bean
    public ProfileConfig profileConfig() {
        ProfileConfig profileConfig = new ProfileConfig();
        profileConfig.setActiveProfile(this.activeProfile);
        return profileConfig;
    }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url(this.env.getProperty("b1_jdbc_url"));
        builder.username(this.env.getProperty("b1_jdbc_user"));
        builder.password(this.env.getProperty("b1_jbdc_password"));
        return builder.build();
    }
}

