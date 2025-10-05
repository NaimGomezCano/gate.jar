/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.tcli.observer.feign.AuthFeign
 *  com.sap.b1.tcli.observer.feign.OF365Feign
 *  com.sap.b1.util.HttpClientPoolUtil
 *  com.sap.b1.web.core.FeignConfigOf365
 *  com.sap.b1.web.core.FeignConfigOf365AuthErrorDecoder
 *  com.sap.b1.web.core.FeignConfigOf365AuthInterceptor
 *  com.sap.b1.web.core.FeignConfigOf365Encoder
 *  com.sap.b1.web.core.FeignConfigOf365ErrorDecoder
 *  com.sap.b1.web.core.FeignConfigOf365Interceptor
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
 *  feign.slf4j.Slf4jLogger
 *  org.apache.hc.client5.http.classic.HttpClient
 *  org.apache.hc.client5.http.impl.classic.CloseableHttpClient
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 */
package com.sap.b1.web.core;

import com.sap.b1.tcli.observer.feign.AuthFeign;
import com.sap.b1.tcli.observer.feign.OF365Feign;
import com.sap.b1.util.HttpClientPoolUtil;
import com.sap.b1.web.core.FeignConfigOf365AuthErrorDecoder;
import com.sap.b1.web.core.FeignConfigOf365AuthInterceptor;
import com.sap.b1.web.core.FeignConfigOf365Encoder;
import com.sap.b1.web.core.FeignConfigOf365ErrorDecoder;
import com.sap.b1.web.core.FeignConfigOf365Interceptor;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.hc5.ApacheHttp5Client;
import feign.jackson.JacksonDecoder;
import feign.slf4j.Slf4jLogger;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfigOf365 {
    @Value(value="${b1_o365_url:https://localhost:40000/of365}")
    private String of365Url;
    @Value(value="${auth.url:http://localhost:9901}")
    private String authUrl;
    @Value(value="${feign.client.config.default.loggerLevel:NONE}")
    private Logger.Level logLevel;
    private Feign.Builder of365Builder;
    private Feign.Builder authBuilder;

    @Autowired
    public void init() throws Exception {
        CloseableHttpClient httpClients = HttpClientPoolUtil.buildSDKClient();
        this.of365Builder = Feign.builder().client((Client)new ApacheHttp5Client((HttpClient)httpClients)).logger((Logger)new Slf4jLogger()).logLevel(this.logLevel).requestInterceptor((RequestInterceptor)new FeignConfigOf365Interceptor()).encoder((Encoder)new FeignConfigOf365Encoder()).decoder((Decoder)new JacksonDecoder()).errorDecoder((ErrorDecoder)new FeignConfigOf365ErrorDecoder(this.of365Url));
        this.authBuilder = Feign.builder().client((Client)new ApacheHttp5Client((HttpClient)httpClients)).logger((Logger)new Slf4jLogger()).logLevel(this.logLevel).requestInterceptor((RequestInterceptor)new FeignConfigOf365AuthInterceptor()).decoder((Decoder)new JacksonDecoder()).errorDecoder((ErrorDecoder)new FeignConfigOf365AuthErrorDecoder());
    }

    @Bean
    OF365Feign initOF365Feign() {
        return (OF365Feign)this.of365Builder.target(OF365Feign.class, this.of365Url);
    }

    @Bean
    AuthFeign initAuthFeign() {
        return (AuthFeign)this.authBuilder.target(AuthFeign.class, this.authUrl);
    }
}

