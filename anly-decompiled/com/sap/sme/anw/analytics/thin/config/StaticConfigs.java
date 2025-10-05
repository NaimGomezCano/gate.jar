/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.config.Config
 *  com.sap.sme.anw.analytics.core.config.ConfigOption
 *  com.sap.sme.anw.analytics.core.config.CoreConfig
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLog
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel
 *  com.sap.sme.anw.analytics.thin.config.StaticConfigs
 *  org.apache.commons.lang3.StringUtils
 */
package com.sap.sme.anw.analytics.thin.config;

import com.sap.sme.anw.analytics.core.config.Config;
import com.sap.sme.anw.analytics.core.config.ConfigOption;
import com.sap.sme.anw.analytics.core.config.CoreConfig;
import com.sap.sme.anw.analytics.core.log.AnalyticsLog;
import com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import org.apache.commons.lang3.StringUtils;

public abstract class StaticConfigs {
    public static final Config SYSTEM_CONFIG = new Config();

    static {
        try (InputStream is = StaticConfigs.class.getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF");){
            Manifest manifest = new Manifest(is);
            Attributes attributes = manifest.getMainAttributes();
            String buildId = attributes.getValue("Build-Id");
            if (!StringUtils.isBlank((CharSequence)buildId)) {
                SYSTEM_CONFIG.setConfigValue(ConfigOption.OPTION_BUILD_ID.name(), (Object)buildId);
            }
        }
        catch (IOException e) {
            AnalyticsLog.log(StaticConfigs.class, (AnalyticsLogLevel)AnalyticsLogLevel.WARNING, (Throwable)e);
        }
        SYSTEM_CONFIG.addConfig(CoreConfig.CORE_CONFIG);
        SYSTEM_CONFIG.loadConfigFromEnv();
    }
}

