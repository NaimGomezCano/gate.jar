/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.ChangePathAPIFilter
 *  com.sap.b1.gate.ChangePathAPIFilter$Config
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.cloud.gateway.filter.GatewayFilter
 *  org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.HttpStatusCode
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.stereotype.Component
 *  reactor.core.publisher.Mono
 */
package com.sap.b1.gate;

import com.sap.b1.gate.ChangePathAPIFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ChangePathAPIFilter
extends AbstractGatewayFilterFactory<Config> {
    private static final Logger logger = LoggerFactory.getLogger(ChangePathAPIFilter.class);
    private final String CHANGE_API_URL_PATTERN = "/v\\d+/webclient/";

    public ChangePathAPIFilter() {
        super(Config.class);
    }

    public GatewayFilter apply(Config config) throws RuntimeException {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            Matcher matcher = Pattern.compile("/v\\d+/webclient/").matcher(request.getURI().getPath());
            if (matcher.find()) {
                logger.warn("[Gate.ChangePathAPIFilter] Attempting to access an exposed Change Path API: " + request.getURI().getPath());
                exchange.getResponse().setStatusCode((HttpStatusCode)HttpStatus.FORBIDDEN);
                return Mono.empty();
            }
            return chain.filter(exchange);
        };
    }
}

