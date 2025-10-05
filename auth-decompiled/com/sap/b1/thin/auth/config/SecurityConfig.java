/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.auth.config.SecurityConfig
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Profile
 *  org.springframework.core.io.Resource
 *  org.springframework.security.config.annotation.web.builders.HttpSecurity
 *  org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
 *  org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer$AuthorizedUrl
 *  org.springframework.security.rsa.crypto.KeyStoreKeyFactory
 *  org.springframework.security.web.SecurityFilterChain
 *  org.springframework.security.web.util.matcher.AnyRequestMatcher
 */
package com.sap.b1.thin.auth.config;

import java.security.KeyPair;
import java.security.PrivateKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final long MAX_AGE_IN_SECONDS = 31536000L;
    @Value(value="${jwt.certificate.store.file}")
    private Resource keystore;
    @Value(value="${jwt.certificate.store.password}")
    private String keystorePassword;
    @Value(value="${jwt.certificate.key.alias}")
    private String keyAlias;
    @Value(value="${jwt.certificate.key.password}")
    private String keyPassword;

    @Bean
    @Profile(value={"test"})
    public PrivateKey jwtAccessTokenConverter() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(this.keystore, this.keystorePassword.toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(this.keyAlias, this.keyPassword.toCharArray());
        return keyPair.getPrivate();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(authorize -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)authorize.anyRequest()).permitAll());
        http.headers(headers -> headers.httpStrictTransportSecurity(hstsConfig -> hstsConfig.requestMatcher(AnyRequestMatcher.INSTANCE).includeSubDomains(true).maxAgeInSeconds(31536000L)));
        return (SecurityFilterChain)http.build();
    }
}

