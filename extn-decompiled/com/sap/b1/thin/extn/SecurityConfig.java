/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.share.web.JwtAuthenticationFilter
 *  com.sap.b1.infra.share.web.Keys
 *  com.sap.b1.sdk.oidc.web.filter.MDCFilter
 *  com.sap.b1.thin.extn.CorsFilter
 *  com.sap.b1.thin.extn.SecurityConfig
 *  com.sap.b1.thin.extn.StaticResourceFilter
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
 */
package com.sap.b1.thin.extn;

import com.sap.b1.infra.share.web.JwtAuthenticationFilter;
import com.sap.b1.infra.share.web.Keys;
import com.sap.b1.sdk.oidc.web.filter.MDCFilter;
import com.sap.b1.thin.extn.CorsFilter;
import com.sap.b1.thin.extn.StaticResourceFilter;
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

@Configuration
@EnableWebSecurity
@Import(value={Keys.class})
public class SecurityConfig {
    private static final long MAX_AGE_IN_SECONDS = 31536000L;
    @Autowired
    public Keys keys;
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        PublicKey publicKey = this.keys.getJwtPublicKey();
        http.authorizeHttpRequests(authorize -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)authorize.anyRequest()).authenticated()).addFilter((Filter)new JwtAuthenticationFilter(this.authenticationConfiguration.getAuthenticationManager(), publicKey)).addFilterAfter((Filter)new MDCFilter(), JwtAuthenticationFilter.class).addFilterAfter((Filter)new StaticResourceFilter(), JwtAuthenticationFilter.class).addFilterAfter((Filter)new CorsFilter(), JwtAuthenticationFilter.class);
        http.securityContext(securityContext -> securityContext.requireExplicitSave(false));
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        http.headers(headers -> headers.httpStrictTransportSecurity(hstsConfig -> hstsConfig.requestMatcher(AnyRequestMatcher.INSTANCE).includeSubDomains(true).maxAgeInSeconds(31536000L)));
        return (SecurityFilterChain)http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) {
        return (AuthenticationManager)auth.getOrBuild();
    }
}

