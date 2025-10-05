/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.share.web.JwtAuthenticationFilter
 *  com.sap.b1.infra.share.web.Keys
 *  com.sap.b1.sdk.oidc.web.filter.MDCFilter
 *  com.sap.b1.web.core.SecurityConfig
 *  jakarta.servlet.Filter
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.http.HttpStatus
 *  org.springframework.security.authentication.AuthenticationManager
 *  org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
 *  org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
 *  org.springframework.security.config.annotation.web.builders.HttpSecurity
 *  org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
 *  org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer$AuthorizedUrl
 *  org.springframework.security.web.AuthenticationEntryPoint
 *  org.springframework.security.web.SecurityFilterChain
 *  org.springframework.security.web.authentication.HttpStatusEntryPoint
 *  org.springframework.security.web.util.matcher.AnyRequestMatcher
 */
package com.sap.b1.web.core;

import com.sap.b1.infra.share.web.JwtAuthenticationFilter;
import com.sap.b1.infra.share.web.Keys;
import com.sap.b1.sdk.oidc.web.filter.MDCFilter;
import jakarta.servlet.Filter;
import java.security.PublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final long MAX_AGE_IN_SECONDS = 31536000L;
    @Autowired
    Keys keys;
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;
    @Value(value="${spring.profiles.active:default}")
    private String activeProfile;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        PublicKey publicKey = this.keys.getJwtPublicKey();
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(this.authenticationConfiguration.getAuthenticationManager(), publicKey);
        jwtAuthenticationFilter.setActiveProfile(this.activeProfile);
        http.authorizeHttpRequests(authorize -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)authorize.anyRequest()).authenticated()).addFilter((Filter)jwtAuthenticationFilter).addFilterAfter((Filter)new MDCFilter(), JwtAuthenticationFilter.class);
        http.exceptionHandling(exception -> exception.authenticationEntryPoint((AuthenticationEntryPoint)new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        http.headers(headers -> headers.httpStrictTransportSecurity(hstsConfig -> hstsConfig.requestMatcher(AnyRequestMatcher.INSTANCE).includeSubDomains(true).maxAgeInSeconds(31536000L)));
        return (SecurityFilterChain)http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) {
        return (AuthenticationManager)auth.getOrBuild();
    }
}

