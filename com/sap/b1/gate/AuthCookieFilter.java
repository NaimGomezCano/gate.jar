/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.AuthCookieFilter
 *  com.sap.b1.gate.AuthCookieFilter$SomeConfig
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.cloud.gateway.filter.GatewayFilter
 *  org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.ResponseCookie
 *  org.springframework.http.ResponseCookie$ResponseCookieBuilder
 *  org.springframework.stereotype.Component
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 *  reactor.core.publisher.Mono
 */
package com.sap.b1.gate;

import com.sap.b1.gate.AuthCookieFilter;
import java.net.HttpCookie;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Component
public class AuthCookieFilter
extends AbstractGatewayFilterFactory<SomeConfig> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthCookieFilter.class);
    public static final String AUTH_COOKIE_KEY = "AUTH_COOKIE";

    public AuthCookieFilter() {
        super(SomeConfig.class);
    }

    public GatewayFilter apply(SomeConfig config) {
        return (exchange, chain) -> {
            exchange.getResponse().beforeCommit(() -> exchange.getSession().flatMap(session -> {
                HttpHeaders allHeaders = exchange.getResponse().getHeaders();
                List authCookies = allHeaders.get((Object)"Set-Cookie");
                String cookies = this.parseCookies(authCookies);
                if (StringUtils.hasText((String)cookies)) {
                    LOGGER.info("Set Auth Cookie");
                    session.getAttributes().put(AUTH_COOKIE_KEY, cookies);
                    String[] authCookie = cookies.split(";");
                    String[] firstCookie = authCookie[0].split("=");
                    if (firstCookie.length == 2) {
                        ResponseCookie.ResponseCookieBuilder respCookieBuilder = ResponseCookie.from((String)firstCookie[0], (String)firstCookie[1]);
                        if ("SESSION.AUTH".equals(firstCookie[0])) {
                            respCookieBuilder.httpOnly(true);
                            allHeaders.remove((Object)"Set-Cookie");
                        }
                        respCookieBuilder.sameSite("None").secure(true);
                        exchange.getResponse().addCookie(respCookieBuilder.build());
                    }
                }
                return Mono.empty();
            }));
            return chain.filter(exchange);
        };
    }

    public String parseCookies(List<String> cookies) {
        if (ObjectUtils.isEmpty(cookies)) {
            return null;
        }
        StringBuilder cookieStr = new StringBuilder();
        for (String c : cookies) {
            List<HttpCookie> r = HttpCookie.parse(c);
            for (HttpCookie httpCookie : r) {
                if (cookieStr.length() > 0) {
                    cookieStr.append(";");
                }
                cookieStr.append(httpCookie.getName()).append("=").append(httpCookie.getValue());
            }
        }
        return cookieStr.toString();
    }
}

