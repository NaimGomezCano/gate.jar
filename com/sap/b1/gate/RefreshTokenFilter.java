/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.RefreshTokenFilter
 *  com.sap.b1.gate.RefreshTokenFilter$Config
 *  com.sap.b1.sdk.oidc.core.dto.sld.JsonWebToken
 *  com.sap.b1.sdk.oidc.core.handler.OAuth2Handler
 *  com.sap.b1.sdk.oidc.core.handler.token.AccessToken
 *  com.sap.b1.sdk.oidc.core.handler.token.OAuthToken
 *  com.sap.b1.sdk.oidc.core.handler.token.RefreshableAccessToken
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.cloud.gateway.filter.GatewayFilter
 *  org.springframework.cloud.gateway.filter.GatewayFilterChain
 *  org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.HttpStatusCode
 *  org.springframework.stereotype.Component
 *  org.springframework.util.StringUtils
 *  org.springframework.web.reactive.function.client.WebClient
 *  reactor.core.publisher.Mono
 */
package com.sap.b1.gate;

import com.sap.b1.gate.RefreshTokenFilter;
import com.sap.b1.sdk.oidc.core.dto.sld.JsonWebToken;
import com.sap.b1.sdk.oidc.core.handler.OAuth2Handler;
import com.sap.b1.sdk.oidc.core.handler.token.AccessToken;
import com.sap.b1.sdk.oidc.core.handler.token.OAuthToken;
import com.sap.b1.sdk.oidc.core.handler.token.RefreshableAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/*
 * Exception performing whole class analysis ignored.
 */
@Component
public class RefreshTokenFilter
extends AbstractGatewayFilterFactory<Config> {
    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenFilter.class);
    private final WebClient client = WebClient.create((String)"http://localhost:9901");

    public RefreshTokenFilter() {
        super(Config.class);
    }

    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> exchange.getSession().flatMap(session -> {
            Object tokenObject = session.getAttributes().get("ACCESS_TOKEN");
            String cookies = (String)session.getAttributes().get("AUTH_COOKIE");
            if (!(tokenObject instanceof RefreshableAccessToken) || !StringUtils.hasText((String)cookies) || RefreshTokenFilter.isValidToken((AccessToken)((AccessToken)tokenObject))) {
                return chain.filter(exchange);
            }
            logger.info("Token is invalid, begin to refresh token");
            return this.client.get().uri("/auth/refreshToken", new Object[0]).header("Cookie", new String[]{cookies}).exchangeToMono(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    logger.info("Refresh token succeed");
                    return response.bodyToMono(OAuthToken.class);
                }
                logger.error("Refresh token failed, Response code:{}", (Object)response.statusCode());
                session.invalidate();
                exchange.getResponse().setStatusCode((HttpStatusCode)HttpStatus.UNAUTHORIZED);
                return Mono.empty();
            }).map(data -> {
                session.getAttributes().put("ACCESS_TOKEN", data);
                return exchange;
            }).flatMap(arg_0 -> ((GatewayFilterChain)chain).filter(arg_0));
        });
    }

    public static boolean isValidToken(AccessToken token) {
        OAuth2Handler oAuth2Handler = OAuth2Handler.getInstance();
        JsonWebToken jsonWebToken = oAuth2Handler.validateAccessToken(token, 15L);
        return jsonWebToken.getActive();
    }
}

