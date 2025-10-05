/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.CustomForwardedHeadersFilter
 *  org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter
 *  org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter$Type
 *  org.springframework.cloud.gateway.filter.headers.XForwardedHeadersFilter
 *  org.springframework.http.HttpHeaders
 *  org.springframework.stereotype.Component
 *  org.springframework.web.server.ServerWebExchange
 */
package com.sap.b1.gate;

import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.cloud.gateway.filter.headers.XForwardedHeadersFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class CustomForwardedHeadersFilter
implements HttpHeadersFilter {
    private final XForwardedHeadersFilter xForwardedHeadersFilter = new XForwardedHeadersFilter();

    public HttpHeaders filter(HttpHeaders input, ServerWebExchange exchange) {
        return this.xForwardedHeadersFilter.filter(input, exchange);
    }

    public boolean supports(HttpHeadersFilter.Type type) {
        return type == HttpHeadersFilter.Type.REQUEST;
    }
}

