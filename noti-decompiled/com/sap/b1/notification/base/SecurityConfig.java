/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.bo.base.BoLogicLanguage
 *  com.sap.b1.db.DBTypeConfig
 *  com.sap.b1.infra.share.web.JwtAuthenticationFilter
 *  com.sap.b1.infra.share.web.Keys
 *  com.sap.b1.notification.base.SecurityConfig
 *  com.sap.b1.notification.server.NotificationFilter
 *  com.sap.b1.sdk.oidc.web.filter.MDCFilter
 *  jakarta.servlet.Filter
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.security.authentication.AuthenticationManager
 *  org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
 *  org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
 *  org.springframework.security.config.annotation.web.builders.HttpSecurity
 *  org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
 *  org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer$AuthorizedUrl
 *  org.springframework.security.web.SecurityFilterChain
 *  org.springframework.security.web.util.matcher.AnyRequestMatcher
 *  org.springframework.web.context.request.RequestContextListener
 */
package com.sap.b1.notification.base;

import com.sap.b1.bo.base.BoLogicLanguage;
import com.sap.b1.db.DBTypeConfig;
import com.sap.b1.infra.share.web.JwtAuthenticationFilter;
import com.sap.b1.infra.share.web.Keys;
import com.sap.b1.notification.server.NotificationFilter;
import com.sap.b1.sdk.oidc.web.filter.MDCFilter;
import jakarta.servlet.Filter;
import java.security.PublicKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@EnableWebSecurity
@Import(value={Keys.class})
public class SecurityConfig {
    private static final long MAX_AGE_IN_SECONDS = 31536000L;
    @Autowired
    Keys keys;
    @Autowired
    BoLogicLanguage logicLanguage;
    @Autowired
    DBTypeConfig dbType;
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        PublicKey publicKey = this.keys.getJwtPublicKey();
        NotificationFilter notificationFilter = new NotificationFilter(this.logicLanguage, this.dbType);
        http.authorizeHttpRequests(authorize -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)authorize.anyRequest()).authenticated()).addFilter((Filter)new JwtAuthenticationFilter(this.authenticationConfiguration.getAuthenticationManager(), publicKey)).addFilterAfter((Filter)new MDCFilter(), JwtAuthenticationFilter.class).addFilterAfter((Filter)notificationFilter, JwtAuthenticationFilter.class);
        http.headers(headers -> headers.httpStrictTransportSecurity(hstsConfig -> hstsConfig.requestMatcher(AnyRequestMatcher.INSTANCE).includeSubDomains(true).maxAgeInSeconds(31536000L)));
        return (SecurityFilterChain)http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) {
        return (AuthenticationManager)auth.getOrBuild();
    }
}

