/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.NettyServerConfiguration
 *  org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
 *  org.springframework.boot.web.embedded.netty.NettyServerCustomizer
 *  org.springframework.boot.web.server.WebServerFactoryCustomizer
 *  org.springframework.context.annotation.Configuration
 *  reactor.netty.http.server.HttpRequestDecoderSpec
 */
package com.sap.b1.gate;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.server.HttpRequestDecoderSpec;

@Configuration
public class NettyServerConfiguration
implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {
    private int maxInitialLineLength = 8192;

    public void customize(NettyReactiveWebServerFactory factory) {
        factory.addServerCustomizers(new NettyServerCustomizer[]{httpServer -> httpServer.httpRequestDecoder(spec -> (HttpRequestDecoderSpec)spec.maxInitialLineLength(this.maxInitialLineLength))});
    }
}

