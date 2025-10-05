/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.tcli.observer.feign.B1ahFeign
 *  com.sap.b1.util.HttpClientPoolUtil
 *  com.sap.b1.web.core.FeignConfigB1ah
 *  com.sap.b1.web.core.FeignConfigB1ahErrorDecoder
 *  com.sap.b1.web.core.FeignConfigB1ahInterceptor
 *  feign.Client
 *  feign.Feign
 *  feign.Feign$Builder
 *  feign.Logger
 *  feign.Logger$Level
 *  feign.RequestInterceptor
 *  feign.codec.Decoder
 *  feign.codec.Encoder
 *  feign.codec.ErrorDecoder
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

import com.sap.b1.tcli.observer.feign.B1ahFeign;
import com.sap.b1.util.HttpClientPoolUtil;
import com.sap.b1.web.core.FeignConfigB1ahErrorDecoder;
import com.sap.b1.web.core.FeignConfigB1ahInterceptor;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
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
public class FeignConfigB1ah {
    @Value(value="${b1_b1ah_url:http://localhost:0}")
    String b1ahUrl;
    @Value(value="${feign.client.config.default.loggerLevel:NONE}")
    Logger.Level logLevel;
    private Feign.Builder builder;

    @Autowired
    public void init() throws Exception {
        CloseableHttpClient httpClients = HttpClientPoolUtil.buildSDKClient();
        this.builder = Feign.builder().client((Client)new ApacheHttp5Client((HttpClient)httpClients)).logger((Logger)new Slf4jLogger()).logLevel(this.logLevel).requestInterceptor((RequestInterceptor)new FeignConfigB1ahInterceptor()).encoder((Encoder)new JacksonEncoder()).decoder((Decoder)new JacksonDecoder()).errorDecoder((ErrorDecoder)new FeignConfigB1ahErrorDecoder(this.b1ahUrl));
    }

    @Bean
    B1ahFeign initB1AHFeign() {
        return (B1ahFeign)this.builder.target(B1ahFeign.class, this.b1ahUrl + "/IMCC");
    }
}

