/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.JwtRequestFilter
 *  com.sap.b1.gate.SlRequestFilter
 *  com.sap.b1.gate.SlRequestFilter$SomeConfig
 *  com.sap.b1.infra.share.web.JwtObject
 *  com.sap.b1.infra.share.web.JwtUtils
 *  com.sap.b1.infra.share.web.Keys
 *  com.sap.b1.sdk.oidc.core.handler.token.AccessToken
 *  io.jsonwebtoken.Claims
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.cloud.gateway.filter.GatewayFilter
 *  org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.stereotype.Component
 *  org.springframework.util.StringUtils
 */
package com.sap.b1.gate;

import com.sap.b1.gate.JwtRequestFilter;
import com.sap.b1.gate.SlRequestFilter;
import com.sap.b1.infra.share.web.JwtObject;
import com.sap.b1.infra.share.web.JwtUtils;
import com.sap.b1.infra.share.web.Keys;
import com.sap.b1.sdk.oidc.core.handler.token.AccessToken;
import io.jsonwebtoken.Claims;
import java.security.PublicKey;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SlRequestFilter
extends AbstractGatewayFilterFactory<SomeConfig> {
    @Autowired
    public Keys keys;
    @Value(value="${spring.profiles.active:default}")
    private String activeProfile;

    public SlRequestFilter() {
        super(SomeConfig.class);
    }

    public GatewayFilter apply(SomeConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            return exchange.getSession().flatMap(session -> {
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    return chain.filter(exchange);
                }
                if ("test".equals(this.activeProfile)) {
                    Object value = session.getAttributes().get("GATE");
                    if (value != null) {
                        String jwtToken = value.toString();
                        PublicKey publicKey = this.keys.getJwtPublicKey();
                        JwtObject jwtObject = JwtUtils.parseJwt((String)jwtToken, (PublicKey)publicKey);
                        Claims claims = jwtObject.getClaims();
                        List cookies = (List)claims.get((Object)"cookies");
                        StringBuilder sb = new StringBuilder();
                        Iterator i = cookies.iterator();
                        while (i.hasNext()) {
                            sb.append(((String)i.next()).split(";")[0]);
                            if (!i.hasNext()) continue;
                            sb.append(";");
                        }
                        ServerHttpRequest nRequest = request.mutate().header("Cookie", new String[]{sb.toString()}).build();
                        return chain.filter(exchange.mutate().request(nRequest).build());
                    }
                    session.getAttributes().put("GATE", "");
                } else {
                    Object tokenObject = session.getAttributes().get("ACCESS_TOKEN");
                    AccessToken token = JwtRequestFilter.parseTokenObject(tokenObject);
                    Object companyIdValue = session.getAttributes().get("COMPANY_ID");
                    Object languageCodeValue = session.getAttributes().get("Language");
                    String companyId = null;
                    String languageCode = null;
                    if (companyIdValue != null) {
                        companyId = companyIdValue.toString();
                    }
                    if (languageCodeValue != null) {
                        languageCode = languageCodeValue.toString();
                    }
                    if (!StringUtils.isEmpty((Object)companyId) && !StringUtils.isEmpty(languageCodeValue) && token != null) {
                        ServerHttpRequest nRrequest = request.mutate().header("CompanyId", new String[]{companyId}).header("Authorization", new String[]{"Bearer " + token.getAccessToken()}).header("Language", new String[]{languageCode}).build();
                        session.getAttributes().put("ACCESS_TOKEN", token);
                        return chain.filter(exchange.mutate().request(nRrequest).build());
                    }
                    session.getAttributes().put("COMPANY_ID", "");
                    session.getAttributes().put("ACCESS_TOKEN", "");
                }
                return chain.filter(exchange);
            });
        };
    }
}

