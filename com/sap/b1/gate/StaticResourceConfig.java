/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.StaticResourceConfig
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.http.CacheControl
 *  org.springframework.web.reactive.config.EnableWebFlux
 *  org.springframework.web.reactive.config.ResourceHandlerRegistry
 *  org.springframework.web.reactive.config.WebFluxConfigurer
 */
package com.sap.b1.gate;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
@ConfigurationProperties(prefix="spring.resources")
public class StaticResourceConfig
implements WebFluxConfigurer {
    private List<String> staticLocations;

    public List<String> getStaticLocations() {
        return this.staticLocations;
    }

    public void setStaticLocations(List<String> staticLocations) {
        this.staticLocations = staticLocations;
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"/**"}).addResourceLocations(this.getStaticLocations().toArray(new String[0])).setCacheControl(CacheControl.maxAge((long)1L, (TimeUnit)TimeUnit.DAYS));
    }
}

