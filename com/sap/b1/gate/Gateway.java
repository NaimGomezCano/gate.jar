/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.Gateway
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.cloud.gateway.route.RouteDefinition
 *  org.springframework.context.annotation.Configuration
 */
package com.sap.b1.gate;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix="spring.cloud.gateway")
public class Gateway {
    List<RouteDefinition> routes;

    public List<RouteDefinition> getRoutes() {
        return this.routes;
    }

    public void setRoutes(List<RouteDefinition> routes) {
        this.routes = routes;
    }
}

