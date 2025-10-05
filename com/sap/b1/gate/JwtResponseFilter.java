/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.Cache
 *  com.google.common.cache.CacheBuilder
 *  com.google.gson.Gson
 *  com.sap.b1.gate.JwtResponseFilter
 *  com.sap.b1.sdk.oidc.core.handler.token.OAuthToken
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.cloud.gateway.filter.GatewayFilter
 *  org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
 *  org.springframework.http.HttpHeaders
 *  org.springframework.stereotype.Component
 *  org.springframework.util.StringUtils
 *  org.springframework.web.server.WebSession
 *  reactor.core.publisher.Mono
 */
package com.sap.b1.gate;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import com.sap.b1.sdk.oidc.core.handler.token.OAuthToken;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Component
public class JwtResponseFilter
extends AbstractGatewayFilterFactory<String> {
    private static final Logger logger = LoggerFactory.getLogger(JwtResponseFilter.class);
    @Value(value="${spring.profiles.active:default}")
    private String activeProfile;
    private Cache<String, WebSession> sIdMap = CacheBuilder.newBuilder().expireAfterAccess(30L, TimeUnit.MINUTES).build();

    public JwtResponseFilter() {
        super(String.class);
    }

    public GatewayFilter apply(String config) {
        return (exchange, chain) -> {
            exchange.getResponse().beforeCommit(() -> {
                HttpHeaders allHeaders = exchange.getResponse().getHeaders();
                List licenseHeaders = allHeaders.get((Object)"License");
                List companyIdHeaders = allHeaders.get((Object)"CompanyId");
                List schemaHeaders = allHeaders.get((Object)"Schema");
                List tokenHeaders = allHeaders.get((Object)"Y-Authorization");
                List expiredTimeHeaders = allHeaders.get((Object)"SupprtUserExpiredTime");
                List languageCodeHeaders = allHeaders.get((Object)"Language");
                List logoutSid = allHeaders.get((Object)"logout-sid");
                List svclCookies = allHeaders.get((Object)"x-wc-svcl-state");
                return exchange.getSession().flatMap(session -> {
                    if ("test".equals(this.activeProfile)) {
                        List headers = allHeaders.get((Object)"Y-Authorization");
                        if (headers != null) {
                            String token = (String)headers.get(0);
                            session.getAttributes().put("GATE", token);
                            exchange.getResponse().getHeaders().remove((Object)"Y-Authorization");
                        }
                    } else {
                        String value;
                        if (languageCodeHeaders != null) {
                            value = (String)languageCodeHeaders.get(0);
                            session.getAttributes().put("Language", value);
                            exchange.getResponse().getHeaders().remove((Object)"Language");
                        }
                        if (licenseHeaders != null) {
                            value = (String)licenseHeaders.get(0);
                            session.getAttributes().put("LICENSE", value);
                            exchange.getResponse().getHeaders().remove((Object)"License");
                        }
                        if (companyIdHeaders != null) {
                            value = (String)companyIdHeaders.get(0);
                            session.getAttributes().put("COMPANY_ID", value);
                            exchange.getResponse().getHeaders().remove((Object)"CompanyId");
                        }
                        if (schemaHeaders != null) {
                            value = (String)schemaHeaders.get(0);
                            session.getAttributes().put("SCHEMA", value);
                            exchange.getResponse().getHeaders().remove((Object)"Schema");
                        }
                        if (tokenHeaders != null) {
                            value = (String)tokenHeaders.get(0);
                            OAuthToken accessToken = this.fetchAccessToken(value);
                            if (accessToken != null) {
                                session.getAttributes().put("ACCESS_TOKEN", accessToken);
                                String sId = accessToken.getSessionState();
                                if (sId != null && this.sIdMap.getIfPresent((Object)sId) == null) {
                                    this.sIdMap.put((Object)sId, session);
                                }
                            }
                            exchange.getResponse().getHeaders().remove((Object)"Y-Authorization");
                        }
                        if (logoutSid != null) {
                            String sId = (String)logoutSid.get(0);
                            WebSession expiredSession = (WebSession)this.sIdMap.getIfPresent((Object)sId);
                            if (expiredSession != null) {
                                this.sIdMap.invalidate((Object)sId);
                                expiredSession.invalidate();
                            }
                            exchange.getResponse().getHeaders().remove((Object)"logout-sid");
                        }
                        if (expiredTimeHeaders != null && StringUtils.hasText((String)(value = (String)expiredTimeHeaders.get(0)))) {
                            session.getAttributes().put("EXPIRED_TIME", value);
                            exchange.getResponse().getHeaders().remove((Object)"EXPIRED_TIME");
                        }
                        if (svclCookies != null && !svclCookies.isEmpty() && StringUtils.hasText((String)(value = (String)svclCookies.get(0)))) {
                            session.getAttributes().put("SVCL_COOKIE", value);
                            exchange.getResponse().getHeaders().remove((Object)"x-wc-svcl-state");
                        }
                    }
                    return Mono.empty();
                });
            });
            return chain.filter(exchange);
        };
    }

    public OAuthToken fetchAccessToken(String tokenJson) {
        OAuthToken accessToken = null;
        if (tokenJson != null) {
            try {
                Gson gson = new Gson();
                accessToken = (OAuthToken)gson.fromJson(tokenJson, OAuthToken.class);
                return accessToken;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
        return accessToken;
    }
}

