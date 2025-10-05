/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.headers.response.GeneralHeaderRspFilter
 *  com.sap.b1.util.HeaderFileReaderUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.cloud.gateway.filter.GatewayFilter
 *  org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
 *  org.springframework.http.HttpHeaders
 *  org.springframework.stereotype.Component
 *  org.springframework.util.CollectionUtils
 */
package com.sap.b1.headers.response;

import com.sap.b1.util.HeaderFileReaderUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class GeneralHeaderRspFilter
extends AbstractGatewayFilterFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralHeaderRspFilter.class);

    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            exchange.getResponse().beforeCommit(() -> {
                String pathPrefix = exchange.getRequest().getPath().pathWithinApplication().value().split("/")[1];
                Set headerKeyBlackSet = (Set)HeaderFileReaderUtils.rspHeaderKeySetMap.get(pathPrefix);
                LOGGER.info("GeneralHeaderRspFilter for path prefix: {}, headerKeyBlackSet: {}", (Object)pathPrefix, (Object)headerKeyBlackSet);
                if (!CollectionUtils.isEmpty((Collection)headerKeyBlackSet)) {
                    HttpHeaders headers = exchange.getResponse().getHeaders();
                    return exchange.getSession().flatMap(session -> {
                        ArrayList keys2Remove = new ArrayList();
                        headers.forEach((k, v) -> {
                            if (headerKeyBlackSet.contains(k)) {
                                LOGGER.info("Remove invalid response headers for path prefix: {}, key: {}", (Object)pathPrefix, k);
                                keys2Remove.add(k);
                            }
                        });
                        keys2Remove.forEach(arg_0 -> ((HttpHeaders)headers).remove(arg_0));
                        return chain.filter(exchange);
                    });
                }
                return chain.filter(exchange);
            });
            return chain.filter(exchange);
        };
    }
}

