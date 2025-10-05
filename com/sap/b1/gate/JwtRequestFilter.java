/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.JwtRequestFilter
 *  com.sap.b1.gate.JwtRequestFilter$SomeConfig
 *  com.sap.b1.sdk.oidc.core.handler.token.AccessToken
 *  com.sap.b1.sdk.oidc.core.handler.token.OAuthToken
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.cloud.gateway.filter.GatewayFilter
 *  org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.HttpStatusCode
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.http.server.reactive.ServerHttpRequest$Builder
 *  org.springframework.stereotype.Component
 *  org.springframework.util.StringUtils
 */
package com.sap.b1.gate;

import com.sap.b1.gate.JwtRequestFilter;
import com.sap.b1.sdk.oidc.core.handler.token.AccessToken;
import com.sap.b1.sdk.oidc.core.handler.token.OAuthToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/*
 * Exception performing whole class analysis ignored.
 */
@Component
public class JwtRequestFilter
extends AbstractGatewayFilterFactory<SomeConfig> {
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    public static final String SESSION_KEY = "GATE";
    public static final String LICENSE_KEY = "LICENSE";
    public static final String COMPANY_ID_KEY = "COMPANY_ID";
    public static final String SCHEMA_KEY = "SCHEMA";
    public static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN";
    public static final String EXPIRED_TIME_KEY = "EXPIRED_TIME";
    public static final String LANGUAGE_CODE = "Language";
    public static final String SVCL_COOKIE = "SVCL_COOKIE";
    @Value(value="${spring.profiles.active:default}")
    private String activeProfile;

    public JwtRequestFilter() {
        super(SomeConfig.class);
    }

    public GatewayFilter apply(SomeConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            return exchange.getSession().flatMap(session -> {
                if ("test".equals(this.activeProfile)) {
                    Object value = session.getAttributes().get("GATE");
                    String token = null;
                    if (value != null) {
                        token = value.toString();
                    }
                    if (token != null) {
                        ServerHttpRequest nRrequest = request.mutate().header("Authorization", new String[]{token}).build();
                        return chain.filter(exchange.mutate().request(nRrequest).build());
                    }
                    session.getAttributes().put("GATE", "");
                } else {
                    String expiredTime;
                    Object expiredTimeValue;
                    AccessToken token;
                    String path = exchange.getRequest().getURI().getPath();
                    if (path.startsWith("/auth/")) {
                        return chain.filter(exchange);
                    }
                    Object tokenObject = session.getAttributes().get("ACCESS_TOKEN");
                    try {
                        token = JwtRequestFilter.parseTokenObject(tokenObject);
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), (Throwable)e);
                        session.invalidate();
                        exchange.getResponse().setStatusCode((HttpStatusCode)HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                    Object licenseValue = session.getAttributes().get("LICENSE");
                    String license = null;
                    if (licenseValue != null) {
                        license = licenseValue.toString();
                    }
                    Object companyIdValue = session.getAttributes().get("COMPANY_ID");
                    String companyId = null;
                    if (companyIdValue != null) {
                        companyId = companyIdValue.toString();
                    }
                    Object schemaValue = session.getAttributes().get("SCHEMA");
                    String schema = null;
                    if (schemaValue != null) {
                        schema = schemaValue.toString();
                    }
                    Object languageCode = session.getAttributes().get("Language");
                    String language = null;
                    if (languageCode != null) {
                        language = languageCode.toString();
                    }
                    if ((expiredTimeValue = session.getAttributes().get("EXPIRED_TIME")) != null && StringUtils.hasText((String)(expiredTime = expiredTimeValue.toString()))) {
                        request.mutate().header("SupprtUserExpiredTime", new String[]{expiredTime});
                    }
                    String svclCookie = (String)session.getAttribute("SVCL_COOKIE");
                    if (StringUtils.hasText((String)license) && StringUtils.hasText((String)companyId) && StringUtils.hasText((String)schema) && token != null) {
                        ServerHttpRequest.Builder builder = request.mutate().header("licenses", new String[]{license}).header("X-b1-companyid", new String[]{companyId}).header("X-b1-companyschema", new String[]{schema}).header("Authorization", new String[]{"Bearer " + token.getAccessToken()}).header("Language", new String[]{language});
                        if (StringUtils.hasText((String)svclCookie)) {
                            builder.header("x-wc-svcl-state", new String[]{svclCookie});
                        }
                        ServerHttpRequest nRrequest = builder.build();
                        return chain.filter(exchange.mutate().request(nRrequest).build());
                    }
                    session.getAttributes().put("LICENSE", "");
                    session.getAttributes().put("COMPANY_ID", "");
                    session.getAttributes().put("SCHEMA", "");
                    session.getAttributes().put("ACCESS_TOKEN", "");
                    session.getAttributes().put("EXPIRED_TIME", "");
                    session.getAttributes().put("Language", "");
                }
                return chain.filter(exchange);
            });
        };
    }

    public static AccessToken parseTokenObject(Object tokenObject) {
        AccessToken accessToken = null;
        if (tokenObject != null && tokenObject instanceof OAuthToken) {
            accessToken = (AccessToken)tokenObject;
        }
        return accessToken;
    }
}

