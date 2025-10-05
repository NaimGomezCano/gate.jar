/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.CSRFFilter
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.cloud.gateway.filter.GatewayFilterChain
 *  org.springframework.cloud.gateway.filter.GlobalFilter
 *  org.springframework.core.Ordered
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.HttpStatusCode
 *  org.springframework.http.MediaType
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.http.server.reactive.ServerHttpResponse
 *  org.springframework.stereotype.Component
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebSession
 *  reactor.core.publisher.Mono
 */
package com.sap.b1.gate;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Component
public class CSRFFilter
implements GlobalFilter,
Ordered {
    public static final String B1_CSRF_TOKEN_NAME = "X-CSRF-Token";
    private static Logger logger = LoggerFactory.getLogger(CSRFFilter.class);
    private String[] CSRFCheckPaths = new String[]{"/tcli/", "/anly/", "/extn/", "/noti/", "/crpt/", "/svcl/"};

    private boolean methodToCheck(String method) {
        return HttpMethod.POST.name().equalsIgnoreCase(method) || HttpMethod.PUT.name().equalsIgnoreCase(method) || HttpMethod.DELETE.name().equalsIgnoreCase(method) || HttpMethod.PATCH.name().equalsIgnoreCase(method);
    }

    private boolean pathToCheck(String path) {
        for (String csrfPath : this.CSRFCheckPaths) {
            if (!path.contains(csrfPath)) continue;
            return true;
        }
        return false;
    }

    private boolean doCSRFCheck(WebSession session, ServerHttpRequest req) {
        String tokenInRequest;
        if (session == null) {
            return true;
        }
        HttpHeaders headers = req.getHeaders();
        String tokenInSession = (String)session.getAttribute(B1_CSRF_TOKEN_NAME);
        List csrfHeader = headers.getOrEmpty((Object)B1_CSRF_TOKEN_NAME);
        String string = tokenInRequest = csrfHeader.isEmpty() ? null : (String)csrfHeader.get(0);
        if (tokenInSession == null) {
            return false;
        }
        return tokenInSession.equals(tokenInRequest);
    }

    private Mono<Void> handleForbidden(ServerHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode((HttpStatusCode)HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getPath().toString();
        String method = String.valueOf(exchange.getRequest().getMethod());
        if (this.methodToCheck(method) && this.pathToCheck(path)) {
            return exchange.getSession().flatMap(session -> {
                if (!this.doCSRFCheck(session, request)) {
                    logger.warn("wrong CSRF token detected for request: {}", (Object)request.getURI());
                    return this.handleForbidden(response);
                }
                return chain.filter(exchange);
            });
        }
        return chain.filter(exchange);
    }

    public int getOrder() {
        return 0;
    }
}

