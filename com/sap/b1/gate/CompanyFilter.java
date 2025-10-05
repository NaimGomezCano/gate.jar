/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.CompanyFilter
 *  com.sap.b1.gate.CompanyFilter$SomeConfig
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.cloud.gateway.filter.GatewayFilter
 *  org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.HttpStatusCode
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.stereotype.Component
 *  reactor.core.publisher.Mono
 */
package com.sap.b1.gate;

import com.sap.b1.gate.CompanyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CompanyFilter
extends AbstractGatewayFilterFactory<SomeConfig> {
    @Value(value="${spring.profiles.active:default}")
    private String activeProfile;

    public CompanyFilter() {
        super(SomeConfig.class);
    }

    public GatewayFilter apply(SomeConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!"test".equals(this.activeProfile) && request.getURI().getPath().equals("/auth/companies.svc")) {
                exchange.getResponse().setStatusCode((HttpStatusCode)HttpStatus.NOT_FOUND);
                return Mono.empty();
            }
            return chain.filter(exchange);
        };
    }
}

