/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.headers.request.GeneralHeaderReqFilter
 *  com.sap.b1.util.HeaderFileReaderUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.cloud.gateway.filter.GatewayFilter
 *  org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.http.server.reactive.ServerHttpRequest$Builder
 *  org.springframework.stereotype.Component
 *  org.springframework.util.CollectionUtils
 */
package com.sap.b1.headers.request;

import com.sap.b1.util.HeaderFileReaderUtils;
import java.util.Collection;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class GeneralHeaderReqFilter
extends AbstractGatewayFilterFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralHeaderReqFilter.class);
    @Value(value="${spring.profiles.active:default}")
    private String activeProfile;

    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String pathPrefix = request.getPath().pathWithinApplication().value().split("/")[1];
            Set headerKeyWhiteSet = (Set)HeaderFileReaderUtils.reqHeaderKeySetMap.get(pathPrefix);
            LOGGER.info("GeneralHeaderReqFilter for path prefix: {}, headerKeyWhiteSet: {}", (Object)pathPrefix, (Object)headerKeyWhiteSet);
            if (!this.activeProfile.equals("test") && !CollectionUtils.isEmpty((Collection)headerKeyWhiteSet)) {
                ServerHttpRequest.Builder builder = request.mutate();
                builder.headers(httpHeaders -> httpHeaders.keySet().removeIf(k -> {
                    if (!headerKeyWhiteSet.contains(k)) {
                        LOGGER.info("Remove invalid request headers for path prefix: {}, key: {}", (Object)pathPrefix, k);
                        return true;
                    }
                    return false;
                }));
                return chain.filter(exchange.mutate().request(builder.build()).build());
            }
            return chain.filter(exchange);
        };
    }
}

