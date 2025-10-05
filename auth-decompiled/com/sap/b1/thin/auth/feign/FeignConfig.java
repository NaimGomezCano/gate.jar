/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.MapperFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.SerializationFeature
 *  com.sap.b1.thin.auth.feign.FeignConfig
 *  com.sap.b1.thin.auth.feign.SvclAuthService
 *  com.sap.b1.util.HttpClientPoolUtil
 *  feign.Client
 *  feign.Feign
 *  feign.Feign$Builder
 *  feign.Logger
 *  feign.Logger$Level
 *  feign.codec.Decoder
 *  feign.codec.Encoder
 *  feign.hc5.ApacheHttp5Client
 *  feign.jackson.JacksonDecoder
 *  feign.jackson.JacksonEncoder
 *  feign.slf4j.Slf4jLogger
 *  org.apache.hc.client5.http.classic.HttpClient
 *  org.apache.hc.client5.http.impl.classic.CloseableHttpClient
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 */
package com.sap.b1.thin.auth.feign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sap.b1.thin.auth.feign.SvclAuthService;
import com.sap.b1.util.HttpClientPoolUtil;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hc5.ApacheHttp5Client;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Value(value="${b1_svcl_url}")
    String url;

    @Bean
    CloseableHttpClient httpClient() throws Exception {
        CloseableHttpClient httpClients = HttpClientPoolUtil.buildSDKClient();
        return httpClients;
    }

    @Bean
    Feign.Builder builder(CloseableHttpClient httpClient) {
        Feign.Builder builder = Feign.builder().client((Client)new ApacheHttp5Client((HttpClient)httpClient)).logger((Logger)new Slf4jLogger()).logLevel(Logger.Level.FULL);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        builder = builder.decoder((Decoder)new JacksonDecoder()).encoder((Encoder)new JacksonEncoder());
        return builder;
    }

    @Bean
    SvclAuthService initSvclAuthService(Feign.Builder builder) {
        return (SvclAuthService)builder.target(SvclAuthService.class, this.url);
    }
}

