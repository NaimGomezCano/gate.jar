/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.sdk.oidc.web.filter.OIDCFilterChainProxy
 *  com.sap.b1.sdk.oidc.web.listener.BackChannelLogoutSessionListener
 *  com.sap.b1.thin.auth.AccessLog.AccessLogUtils
 *  com.sap.b1.thin.auth.OIDCFilterConfig
 *  com.sap.b1.thin.auth.OIDCLogoutFilter
 *  com.sap.b1.thin.auth.OIDCUtils
 *  com.sap.b1.thin.auth.oidc.OIDCAcsFilter
 *  jakarta.servlet.Filter
 *  org.springframework.boot.web.servlet.FilterRegistrationBean
 *  org.springframework.boot.web.servlet.ServletListenerRegistrationBean
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Profile
 */
package com.sap.b1.thin.auth;

import com.sap.b1.sdk.oidc.web.filter.OIDCFilterChainProxy;
import com.sap.b1.sdk.oidc.web.listener.BackChannelLogoutSessionListener;
import com.sap.b1.thin.auth.AccessLog.AccessLogUtils;
import com.sap.b1.thin.auth.OIDCLogoutFilter;
import com.sap.b1.thin.auth.OIDCUtils;
import com.sap.b1.thin.auth.oidc.OIDCAcsFilter;
import jakarta.servlet.Filter;
import java.util.EventListener;
import java.util.HashMap;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class OIDCFilterConfig {
    @Bean
    public OIDCUtils oidcUtils() {
        return new OIDCUtils();
    }

    @Bean
    public AccessLogUtils accessLogUtils() {
        return new AccessLogUtils();
    }

    @Bean
    @Profile(value={"!test"})
    public FilterRegistrationBean<OIDCFilterChainProxy> registerOIDCFilter() {
        FilterRegistrationBean frb = new FilterRegistrationBean();
        frb.setFilter((Filter)new OIDCFilterChainProxy());
        frb.setOrder(1);
        frb.addUrlPatterns(new String[]{"/*"});
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("urlAfterLogout", "/webx/index.html");
        map.put("disableHandlePageCache", "true");
        map.put("logoutListener", "com.sap.b1.sdk.oidc.web.listener.BackChannelLogoutListener");
        frb.setInitParameters(map);
        return frb;
    }

    @Bean
    public FilterRegistrationBean<OIDCAcsFilter> addOIDCAcsFilter(OIDCUtils ssoUtils, AccessLogUtils accessLogUtils) {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        OIDCAcsFilter oidcAcsFilter = new OIDCAcsFilter();
        oidcAcsFilter.setOidcUtils(ssoUtils);
        oidcAcsFilter.setAccessLogUtils(accessLogUtils);
        bean.setFilter((Filter)oidcAcsFilter);
        bean.addUrlPatterns(new String[]{"/sp/login"});
        return bean;
    }

    @Bean
    public FilterRegistrationBean<OIDCLogoutFilter> oidcLogoutFilter(AccessLogUtils accessLogUtils) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        OIDCLogoutFilter oidcLogoutFilter = new OIDCLogoutFilter();
        oidcLogoutFilter.setAccessLogUtils(accessLogUtils);
        registrationBean.setFilter((Filter)oidcLogoutFilter);
        registrationBean.addUrlPatterns(new String[]{"/auth/logout"});
        registrationBean.setOrder(0);
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<BackChannelLogoutSessionListener> addLogoutSessionListener() {
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener((EventListener)new BackChannelLogoutSessionListener());
        return bean;
    }
}

