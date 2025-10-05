/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.sme.anw.analytics.thin.Application
 *  com.sap.sme.anw.analytics.thin.filter.TenantFilter
 *  jakarta.servlet.Filter
 *  org.springframework.boot.SpringApplication
 *  org.springframework.boot.autoconfigure.SpringBootApplication
 *  org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
 *  org.springframework.boot.web.servlet.FilterRegistrationBean
 *  org.springframework.boot.web.servlet.error.DefaultErrorAttributes
 *  org.springframework.context.annotation.Bean
 *  org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
 */
package com.sap.sme.anw.analytics.thin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.b1.boot.Bootstrap;
import com.sap.sme.anw.analytics.thin.filter.TenantFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@SpringBootApplication(exclude={UserDetailsServiceAutoConfiguration.class})
public class Application {
    private static final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Bean
    public FilterRegistrationBean<TenantFilter> userTenantFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        TenantFilter tenantFilter = new TenantFilter();
        registration.setFilter((Filter)tenantFilter);
        registration.addUrlPatterns(new String[]{"/api/*"});
        registration.setName("UserTenantFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    public DefaultErrorAttributes errorAttributes() {
        return new /* Unavailable Anonymous Inner Class!! */;
    }

    public static void main(String[] args) {
        Bootstrap.getInstance();
        SpringApplication.run(Application.class, (String[])args);
    }
}

