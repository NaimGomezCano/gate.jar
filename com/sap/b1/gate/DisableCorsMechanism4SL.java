/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.DisableCorsMechanism4SL
 *  org.springframework.cloud.gateway.config.GlobalCorsProperties
 *  org.springframework.cloud.gateway.handler.FilteringWebHandler
 *  org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping
 *  org.springframework.cloud.gateway.route.RouteLocator
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Primary
 *  org.springframework.core.env.Environment
 */
package com.sap.b1.gate;

import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.cloud.gateway.handler.FilteringWebHandler;
import org.springframework.cloud.gateway.handler.RoutePredicateHandlerMapping;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

@Configuration
class DisableCorsMechanism4SL {
    DisableCorsMechanism4SL() {
    }

    @Bean
    @Primary
    public RoutePredicateHandlerMapping DisableCorsRoutePredicateHandlerMapping(FilteringWebHandler webHandler, RouteLocator routeLocator, GlobalCorsProperties globalCorsProperties, Environment environment) {
        return new /* Unavailable Anonymous Inner Class!! */;
    }
}

