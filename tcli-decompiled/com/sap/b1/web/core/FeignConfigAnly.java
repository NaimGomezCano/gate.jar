/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.tcli.observer.feign.AnlyFeign
 *  com.sap.b1.util.HttpClientPoolUtil
 *  com.sap.b1.web.core.FeignConfigAnly
 *  com.sap.b1.web.core.FeignConfigAnlyInterceptor
 *  feign.Client
 *  feign.Feign
 *  feign.Feign$Builder
 *  feign.Logger
 *  feign.Logger$Level
 *  feign.RequestInterceptor
 *  feign.codec.Decoder
 *  feign.codec.Encoder
 *  feign.hc5.ApacheHttp5Client
 *  feign.jackson.JacksonDecoder
 *  feign.jackson.JacksonEncoder
 *  feign.slf4j.Slf4jLogger
 *  org.apache.hc.client5.http.classic.HttpClient
 *  org.apache.hc.client5.http.impl.classic.CloseableHttpClient
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 */
package com.sap.b1.web.core;

import com.sap.b1.tcli.observer.feign.AnlyFeign;
import com.sap.b1.util.HttpClientPoolUtil;
import com.sap.b1.web.core.FeignConfigAnlyInterceptor;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hc5.ApacheHttp5Client;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfigAnly {
    @Value(value="${anly.url:http://localhost:9903}")
    String anlyUrl;
    @Value(value="${feign.client.config.default.loggerLevel:NONE}")
    Logger.Level logLevel;
    private Feign.Builder builder;

    @Autowired
    public void init() throws Exception {
        CloseableHttpClient httpClients = HttpClientPoolUtil.buildSDKClient();
        this.builder = Feign.builder().client((Client)new ApacheHttp5Client((HttpClient)httpClients)).logger((Logger)new Slf4jLogger()).logLevel(this.logLevel).requestInterceptor((RequestInterceptor)new FeignConfigAnlyInterceptor()).encoder((Encoder)new JacksonEncoder()).decoder((Decoder)new JacksonDecoder());
    }

    @Bean
    AnlyFeign initAnlyFeign() {
        return (AnlyFeign)this.builder.target(AnlyFeign.class, this.anlyUrl + "/anly");
    }
}

