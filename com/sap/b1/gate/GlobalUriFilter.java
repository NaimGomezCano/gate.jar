/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.GlobalUriFilter
 *  org.springframework.cloud.gateway.filter.GatewayFilterChain
 *  org.springframework.cloud.gateway.filter.GlobalFilter
 *  org.springframework.cloud.gateway.route.Route
 *  org.springframework.cloud.gateway.support.ServerWebExchangeUtils
 *  org.springframework.core.Ordered
 *  org.springframework.stereotype.Component
 *  org.springframework.web.server.ServerWebExchange
 *  reactor.core.publisher.Mono
 */
package com.sap.b1.gate;

import java.net.URI;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*
 * Exception performing whole class analysis ignored.
 */
@Component
public class GlobalUriFilter
implements GlobalFilter,
Ordered {
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI incomingUri = exchange.getRequest().getURI();
        if (GlobalUriFilter.isUriEncoded((URI)incomingUri)) {
            Route route = (Route)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            if (route == null) {
                return chain.filter(exchange);
            }
            URI routeUri = route.getUri();
            URI mergedUri = this.createUri(incomingUri, routeUri);
            exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, mergedUri);
        }
        return chain.filter(exchange);
    }

    private URI createUri(URI incomingUri, URI routeUri) {
        String port = routeUri.getPort() != -1 ? ":" + routeUri.getPort() : "";
        String rawPath = incomingUri.getRawPath() != null ? incomingUri.getRawPath() : "";
        String query = incomingUri.getRawQuery() != null ? "?" + incomingUri.getRawQuery() : "";
        return URI.create(routeUri.getScheme() + "://" + routeUri.getHost() + port + rawPath + query);
    }

    private static boolean isUriEncoded(URI uri) {
        return uri.getRawQuery() != null && uri.getRawQuery().contains("%") || uri.getRawPath() != null && uri.getRawPath().contains("%");
    }

    public int getOrder() {
        return 10001;
    }
}

