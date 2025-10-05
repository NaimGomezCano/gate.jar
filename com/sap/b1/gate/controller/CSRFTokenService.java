/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.RefreshTokenFilter
 *  com.sap.b1.gate.controller.CSRFTokenService
 *  com.sap.b1.sdk.oidc.core.handler.token.AccessToken
 *  org.apache.commons.lang3.ObjectUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.HttpStatusCode
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebSession
 *  reactor.core.publisher.Mono
 */
package com.sap.b1.gate.controller;

import com.sap.b1.gate.RefreshTokenFilter;
import com.sap.b1.sdk.oidc.core.handler.token.AccessToken;
import java.io.IOException;
import java.util.UUID;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@RestController
public class CSRFTokenService {
    public static final String B1_CSRF_TOKEN_NAME = "X-CSRF-Token";
    private static final String TOKEN_FORMAT = "{\"token\": \"%s\"}";
    private static Logger logger = LoggerFactory.getLogger(CSRFTokenService.class);
    private final String activeProfile;

    public CSRFTokenService(@Value(value="${spring.profiles.active:default}") String activeProfile) {
        this.activeProfile = activeProfile;
    }

    private String createToken() {
        return UUID.randomUUID().toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String getOrCreateToken(WebSession session) {
        String existedToken = (String)session.getAttribute(B1_CSRF_TOKEN_NAME);
        WebSession webSession = session;
        synchronized (webSession) {
            if (existedToken == null) {
                existedToken = this.createToken();
                session.getAttributes().put(B1_CSRF_TOKEN_NAME, existedToken);
            }
        }
        return existedToken;
    }

    private Mono<String> handleCSRFTokenEndpoint(WebSession session) throws IOException {
        String token = this.getOrCreateToken(session);
        return Mono.just((Object)String.format(TOKEN_FORMAT, token));
    }

    @RequestMapping(path={"/gate/api/csrf"})
    public Mono<String> getCSRFToken(ServerWebExchange exchange) {
        return exchange.getSession().flatMap(session -> {
            try {
                if (!this.activeProfile.equals("test") && !this.isUserAuthenticated(session)) {
                    exchange.getResponse().setStatusCode((HttpStatusCode)HttpStatus.UNAUTHORIZED);
                    return Mono.empty();
                }
                return this.handleCSRFTokenEndpoint(session);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), (Throwable)e);
                throw new RuntimeException(e);
            }
        });
    }

    private boolean isUserAuthenticated(WebSession session) {
        Object tokenObject = session.getAttributes().get("ACCESS_TOKEN");
        if (ObjectUtils.isEmpty(tokenObject) || !(tokenObject instanceof AccessToken)) {
            return false;
        }
        return RefreshTokenFilter.isValidToken((AccessToken)((AccessToken)tokenObject));
    }
}

