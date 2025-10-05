/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.I18nFilter
 *  com.sap.b1.gate.i18n.I18nResourceLoader
 *  com.sap.b1.tcli.resource.domain.PropInfo
 *  org.reactivestreams.Publisher
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.core.io.buffer.DefaultDataBuffer
 *  org.springframework.core.io.buffer.DefaultDataBufferFactory
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.HttpStatusCode
 *  org.springframework.http.MediaType
 *  org.springframework.http.server.reactive.ServerHttpRequest
 *  org.springframework.http.server.reactive.ServerHttpResponse
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.server.WebFilter
 *  org.springframework.web.server.WebFilterChain
 *  reactor.core.publisher.Mono
 */
package com.sap.b1.gate;

import com.sap.b1.gate.i18n.I18nResourceLoader;
import com.sap.b1.tcli.resource.domain.PropInfo;
import java.net.URI;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/*
 * Exception performing whole class analysis ignored.
 */
public class I18nFilter
implements WebFilter {
    DefaultDataBufferFactory bufferFactory = new DefaultDataBufferFactory();
    static Pattern r = Pattern.compile("(/webx/[~0-9/]*)(.*/i18n/.*?)(_[a-zA-Z]+)?(_[a-zA-Z]+)?(_[a-zA-Z]+)?\\.properties");
    @Autowired
    I18nResourceLoader zipLoader;
    private static final Logger logger = LoggerFactory.getLogger(I18nFilter.class);

    public static PropInfo match(String path) {
        Matcher m = r.matcher(path);
        if (!m.find()) {
            return null;
        }
        PropInfo rt = new PropInfo();
        rt.file = "/webx/" + path.substring(m.group(1).length());
        String g2 = m.group(3);
        String g3 = m.group(4);
        String g5 = m.group(5);
        rt.locale = (g2 == null ? "" : g2.substring(1)) + (g3 == null ? "" : g3.substring(1)) + (g5 == null ? "" : g5.substring(1));
        return rt;
    }

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse resp = exchange.getResponse();
        URI uri = request.getURI();
        String path = uri.getPath();
        PropInfo mResult = I18nFilter.match((String)path);
        if (mResult == null) {
            return chain.filter(exchange);
        }
        String file = null;
        try {
            HashMap props = this.zipLoader.getLang(mResult.locale);
            file = (String)props.get(mResult.file);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), (Throwable)ex);
        }
        if (file != null) {
            DefaultDataBuffer db = this.bufferFactory.wrap(file.getBytes());
            resp.setStatusCode((HttpStatusCode)HttpStatus.OK);
            resp.getHeaders().setContentType(MediaType.TEXT_PLAIN);
            return resp.writeWith((Publisher)Mono.just((Object)db));
        }
        return chain.filter(exchange);
    }
}

