/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.csp.Debuggable
 *  com.sap.b1.gate.Gateway
 *  com.sap.b1.gate.HomePageFilter
 *  com.sap.b1.gate.JwtRequestFilter
 *  com.sap.b1.gate.TcliFeign
 *  com.sap.b1.sdk.oidc.core.handler.token.AccessToken
 *  com.sap.b1.util.HttpClientPoolUtil
 *  feign.Client
 *  feign.Feign
 *  feign.Feign$Builder
 *  feign.Logger
 *  feign.Logger$Level
 *  feign.hc5.ApacheHttp5Client
 *  feign.slf4j.Slf4jLogger
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.hc.client5.http.classic.HttpClient
 *  org.apache.hc.client5.http.impl.classic.CloseableHttpClient
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.cloud.gateway.route.RouteDefinition
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.HttpStatusCode
 *  org.springframework.http.server.reactive.ServerHttpResponse
 *  org.springframework.stereotype.Component
 *  org.springframework.util.MultiValueMap
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  org.springframework.web.server.WebSession
 *  reactor.core.publisher.Mono
 */
package com.sap.b1.gate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.csp.Debuggable;
import com.sap.b1.gate.Gateway;
import com.sap.b1.gate.JwtRequestFilter;
import com.sap.b1.gate.TcliFeign;
import com.sap.b1.sdk.oidc.core.handler.token.AccessToken;
import com.sap.b1.util.HttpClientPoolUtil;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.hc5.ApacheHttp5Client;
import feign.slf4j.Slf4jLogger;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@Component
public class HomePageFilter
implements WebFilter {
    private static final Logger logger = LoggerFactory.getLogger(HomePageFilter.class);
    @Value(value="${feign.client.config.default.loggerLevel:NONE}")
    private Logger.Level logLevel;
    @Autowired
    private Gateway gateway;
    @Autowired
    Map<String, Debuggable> cspStrategyMap;
    private TcliFeign tcliFeign;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String WEB_CLIENT_CSP_DEVELOPER_MODE = "WEB_CLIENT_CSP_DEVELOPER_MODE";
    private static final String WEB_CLIENT_CSP_DEBUG_SERVER = "WEB_CLIENT_CSP_DEBUG_SERVER";
    private static final String WEB_CLIENT_CSP = "WEB_CLIENT_CSP";
    private static final String WEB_CLIENT_USER_INFO = "WEB_CLIENT_USER_INFO";
    private static final String DEVELOPER_MODE = "developerMode";
    private static final String CSP = "csp";
    private static final String defaultCSP = "frame-ancestors 'self' teams.microsoft.com *.teams.microsoft.com *.skype.com; default-src 'self' *.sap.com *.hana.ondemand.com; style-src 'self' *.sap.com *.hana.ondemand.com 'unsafe-inline'; frame-src 'self' *.sap.com *.hana.ondemand.com blob:; worker-src 'self' *.sap.com *.hana.ondemand.com blob:";

    @Autowired
    private void initTcliFeign() throws Exception {
        String address = this.getTcliAddress();
        CloseableHttpClient httpClients = HttpClientPoolUtil.buildSDKClient();
        Feign.Builder builder = Feign.builder().client((Client)new ApacheHttp5Client((HttpClient)httpClients)).logger((feign.Logger)new Slf4jLogger());
        this.tcliFeign = (TcliFeign)builder.logLevel(this.logLevel).target(TcliFeign.class, address);
    }

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        MultiValueMap params;
        HttpHeaders headers = exchange.getResponse().getHeaders();
        this.addDefRespHdrs(headers);
        URI uri = exchange.getRequest().getURI();
        String path = uri.getPath();
        if (uri.getQuery() != null && uri.getQuery().length() >= 4096 && (params = exchange.getRequest().getQueryParams()).containsKey((Object)"url") && path.contains("login")) {
            String url = (String)params.getFirst((Object)"url");
            ServerHttpResponse resp = exchange.getResponse();
            resp.setStatusCode((HttpStatusCode)HttpStatus.PERMANENT_REDIRECT);
            String shortUrl = url.split("\\?")[0];
            shortUrl = shortUrl.replaceFirst("#webclient-(\\w+)&/", "%23webclient-$1%26/");
            URI truncatedUri = URI.create(uri.toString().split("\\?")[0] + "?url=" + shortUrl);
            resp.getHeaders().setLocation(truncatedUri);
            return resp.setComplete();
        }
        if (path.startsWith("/auth")) {
            this.assignCSPHeaders(headers, defaultCSP);
            return this.handleStaticFiles(exchange, chain, headers, path);
        }
        return exchange.getSession().flatMap(session -> {
            this.processCSP(exchange, headers, session, path);
            return this.handleStaticFiles(exchange, chain, headers, path);
        });
    }

    private void addDefRespHdrs(HttpHeaders respHdrs) {
        respHdrs.add("X-Frame-Options", "sameorigin");
        respHdrs.add("X-Content-Type-Options", "nosniff");
        respHdrs.add("Permissions-Policy", "camera=(self)");
    }

    private void processCSP(ServerWebExchange exchange, HttpHeaders headers, WebSession session, String path) {
        String finalCSP = defaultCSP;
        if (this.isIndex(path)) {
            Map user = this.getUser(session, exchange);
            String cspSetting = user != null ? (String)user.get(CSP) : defaultCSP;
            boolean devModeEnabled = user != null ? (Boolean)user.get(DEVELOPER_MODE) : false;
            String enableExtDbg = this.getParameter(exchange, "enable-extension-debug");
            String extDbgServer = this.getParameter(exchange, "extension-debug-server");
            Debuggable defaultCSPStrategy = (Debuggable)this.cspStrategyMap.get("DefaultCSP");
            defaultCSPStrategy.init(session, Boolean.valueOf(devModeEnabled), extDbgServer, enableExtDbg);
            finalCSP = defaultCSPStrategy.generateCSP(cspSetting);
        } else if (this.isSandbox(path) || this.isExteranl(path) || this.isCrystalReport(path)) {
            finalCSP = "";
        } else {
            boolean hasCSP = session.getAttributes().containsKey(WEB_CLIENT_CSP);
            if (hasCSP) {
                finalCSP = (String)session.getAttribute(WEB_CLIENT_CSP);
            }
        }
        this.assignCSPHeaders(headers, finalCSP);
    }

    private Mono<Void> handleStaticFiles(ServerWebExchange exchange, WebFilterChain chain, HttpHeaders headers, String path) {
        if (path.startsWith("/webx/~")) {
            headers.set("Cache-Control", "public, max-age=2592000");
        } else if (path.startsWith("/webx")) {
            headers.set("Cache-Control", "no-store");
        } else if (path.equals("/") || path.equals("/index.html")) {
            ServerHttpResponse resp = exchange.getResponse();
            headers.set("Location", "/webx/index.html");
            resp.setStatusCode((HttpStatusCode)HttpStatus.FOUND);
            return resp.setComplete();
        }
        return chain.filter(exchange);
    }

    private void assignCSPHeaders(HttpHeaders headers, String csp) {
        if (StringUtils.isNotEmpty((CharSequence)csp)) {
            headers.set("Content-Security-Policy", csp);
            headers.set("X-Content-Security-Policy", csp);
        }
    }

    private String getParameter(ServerWebExchange exchange, String name) {
        String result = null;
        List values = (List)exchange.getRequest().getQueryParams().get((Object)name);
        if (values != null && values.size() > 0) {
            result = (String)values.get(0);
        }
        return result;
    }

    private Map<String, Object> getUser(WebSession session, ServerWebExchange exchange) {
        Map user = (Map)exchange.getAttribute(WEB_CLIENT_USER_INFO);
        if (exchange.getAttribute(WEB_CLIENT_USER_INFO) != null && user.size() > 0) {
            return user;
        }
        user = this.getUser(session);
        if (user != null) {
            exchange.getAttributes().put(WEB_CLIENT_USER_INFO, user);
        }
        return user;
    }

    private Map<String, Object> getUser(WebSession session) {
        Map user = null;
        Object companyIdValue = session.getAttributes().get("COMPANY_ID");
        String companyId = null;
        if (companyIdValue != null) {
            companyId = companyIdValue.toString();
        }
        if (companyId == null || companyId.trim().equals("")) {
            return null;
        }
        Object tokenObject = session.getAttributes().get("ACCESS_TOKEN");
        AccessToken token = null;
        try {
            token = JwtRequestFilter.parseTokenObject(tokenObject);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        Object licenseValue = session.getAttributes().get("LICENSE");
        String license = null;
        if (licenseValue != null) {
            license = licenseValue.toString();
        }
        Object schemaValue = session.getAttributes().get("SCHEMA");
        String schema = null;
        if (schemaValue != null) {
            schema = schemaValue.toString();
        }
        HashMap<String, Object> headers = new HashMap<String, Object>();
        headers.put("licenses", license);
        headers.put("X-b1-companyid", companyId);
        headers.put("X-b1-companyschema", schema);
        headers.put("Authorization", "Bearer " + Objects.requireNonNull(token).getAccessToken());
        try {
            String res = this.tcliFeign.user(headers);
            if (res != null) {
                Map resMap = (Map)this.objectMapper.readValue(res, (TypeReference)new /* Unavailable Anonymous Inner Class!! */);
                user = (Map)resMap.get("data");
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return user;
    }

    private String getTcliAddress() {
        for (RouteDefinition route : this.gateway.getRoutes()) {
            if (!StringUtils.equals((CharSequence)"tcli", (CharSequence)route.getId())) continue;
            return route.getUri().toString() + "/" + route.getId();
        }
        return null;
    }

    private boolean isIndex(String path) {
        return path.equals("/") || path.equals("/index.html") || path.equals("/webx/index.html") || path.equals("/webx");
    }

    private boolean isSandbox(String path) {
        return path.equals("/webx/sandbox.html") || path.equals("/webx/sandbox-dev.html");
    }

    private boolean isCrystalReport(String path) {
        return path.equals("/webx/crystalreport/report.html");
    }

    private boolean isExteranl(String path) {
        return path.equals("/webx/external.html");
    }
}

