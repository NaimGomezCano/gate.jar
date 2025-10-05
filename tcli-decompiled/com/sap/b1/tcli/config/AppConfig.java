/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.infra.impl.TenantInterceptor
 *  com.sap.b1.tcli.config.AppConfig
 *  com.sap.b1.tcli.config.EnvInterceptor
 *  com.sap.b1.ui.base.UiJacksonBuilder
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.jpa.repository.config.EnableJpaRepositories
 *  org.springframework.http.MediaType
 *  org.springframework.http.converter.HttpMessageConverter
 *  org.springframework.http.converter.StringHttpMessageConverter
 *  org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
 *  org.springframework.web.client.RestTemplate
 *  org.springframework.web.filter.CommonsRequestLoggingFilter
 *  org.springframework.web.servlet.HandlerInterceptor
 *  org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
 *  org.springframework.web.servlet.config.annotation.EnableWebMvc
 *  org.springframework.web.servlet.config.annotation.InterceptorRegistry
 *  org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 */
package com.sap.b1.tcli.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.infra.impl.TenantInterceptor;
import com.sap.b1.tcli.config.EnvInterceptor;
import com.sap.b1.ui.base.UiJacksonBuilder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaRepositories(value={"gen.dao", "com.sap.b1.doc.snb.dao", "com.sap.b1.tcli.service.oinc.dao"})
@EnableWebMvc
public class AppConfig
implements WebMvcConfigurer {
    @Autowired
    EnvInterceptor envInterceptor;
    @Autowired
    TenantInterceptor tenantInterceptor;

    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("svc", MediaType.APPLICATION_JSON);
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        stringConverter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.TEXT_HTML, MediaType.APPLICATION_JSON));
        converters.add((HttpMessageConverter<?>)stringConverter);
        HttpMessageConverter jsonConverter = this.createJacksonHttpMessageConverter();
        converters.add(jsonConverter);
    }

    private HttpMessageConverter<Object> createJacksonHttpMessageConverter() {
        ObjectMapper om = UiJacksonBuilder.createObjectMapper();
        MappingJackson2HttpMessageConverter gsonConverter = new MappingJackson2HttpMessageConverter(om);
        return gsonConverter;
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeQueryString(true);
        return loggingFilter;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor((HandlerInterceptor)this.tenantInterceptor);
        registry.addInterceptor((HandlerInterceptor)this.envInterceptor);
    }

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }
}

