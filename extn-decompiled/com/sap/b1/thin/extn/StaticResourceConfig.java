/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.extn.CustomPathResourceResolver
 *  com.sap.b1.thin.extn.StaticResourceConfig
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
 *  org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 *  org.springframework.web.servlet.resource.ResourceResolver
 */
package com.sap.b1.thin.extn;

import com.sap.b1.thin.extn.CustomPathResourceResolver;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceResolver;

@Configuration
public class StaticResourceConfig
implements WebMvcConfigurer {
    private static final String URL_PATTERNS = "/ui-static/**";
    private static final String THIN_CLIENT_CACHE_DIR = "/b1-thinclient-cache/";
    private static final String B1_CACHE_HOME = System.getenv("B1_CACHE_HOME");
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final Logger logger = LoggerFactory.getLogger(StaticResourceConfig.class);

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String cacheHome;
        String string = cacheHome = B1_CACHE_HOME != null ? B1_CACHE_HOME : TEMP_DIR;
        if (cacheHome.endsWith(File.separator)) {
            cacheHome = cacheHome.substring(0, cacheHome.length() - 1);
        }
        String resourcePath = cacheHome + THIN_CLIENT_CACHE_DIR;
        resourcePath = OS_NAME.indexOf("win") >= 0 ? "file:///" + resourcePath.replace('\\', '/') : "file:" + resourcePath;
        logger.info("static resource path is:" + resourcePath);
        registry.addResourceHandler(new String[]{URL_PATTERNS}).addResourceLocations(new String[]{resourcePath}).resourceChain(true).addResolver((ResourceResolver)new CustomPathResourceResolver());
    }
}

