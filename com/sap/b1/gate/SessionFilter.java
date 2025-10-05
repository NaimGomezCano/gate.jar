/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.SessionFilter
 *  com.sap.b1.gate.SessionFilter$SomeConfig
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.cloud.gateway.filter.GatewayFilter
 *  org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
 *  org.springframework.http.HttpStatus
 *  org.springframework.stereotype.Component
 *  reactor.core.publisher.Mono
 */
package com.sap.b1.gate;

import com.sap.b1.gate.SessionFilter;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SessionFilter
extends AbstractGatewayFilterFactory<SomeConfig> {
    private static final String AUTH_LOGIN = "/auth/sp/login";
    private static final String AUTH_LOGOUT = "/auth/auth/logout";
    private static final String AUTH_LOGIN_TEST = "/auth/login.svc";
    private static final String AUTH_LOGOUT_TEST = "/auth/saml2/sp/logout";
    private static List<String> authPaths = Arrays.asList("/auth/sp/login", "/auth/auth/logout");
    @Value(value="${spring.profiles.active:default}")
    private String activeProfile;

    public SessionFilter() {
        super(SomeConfig.class);
    }

    public GatewayFilter apply(SomeConfig config) {
        return (exchange, chain) -> {
            exchange.getResponse().beforeCommit(() -> {
                String path = exchange.getRequest().getURI().getPath();
                HttpStatus statusCode = (HttpStatus)exchange.getResponse().getStatusCode();
                return exchange.getSession().flatMap(session -> {
                    if (this.activeProfile.equals("test")) {
                        if (path.equals(AUTH_LOGIN_TEST) && statusCode.is2xxSuccessful()) {
                            session.changeSessionId();
                        } else if (path.equals(AUTH_LOGOUT_TEST) && statusCode.is3xxRedirection()) {
                            session.invalidate();
                        }
                    } else if (path.equals(AUTH_LOGIN) && statusCode.is3xxRedirection()) {
                        session.changeSessionId();
                    } else if (path.equals(AUTH_LOGOUT) && !statusCode.isError()) {
                        session.invalidate();
                    }
                    return Mono.empty();
                });
            });
            return chain.filter(exchange);
        };
    }
}

