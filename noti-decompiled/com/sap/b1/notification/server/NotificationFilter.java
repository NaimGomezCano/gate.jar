/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.bo.base.BoLogicLanguage
 *  com.sap.b1.db.DBTypeConfig
 *  com.sap.b1.infra.jpa.TenantContext
 *  com.sap.b1.infra.share.web.B1UserDetails
 *  com.sap.b1.notification.base.ApplicationContextUtil
 *  com.sap.b1.notification.cache.CacheObject
 *  com.sap.b1.notification.server.NotificationFilter
 *  jakarta.servlet.FilterChain
 *  jakarta.servlet.ServletException
 *  jakarta.servlet.ServletRequest
 *  jakarta.servlet.ServletResponse
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.i18n.LocaleContextHolder
 *  org.springframework.http.HttpStatus
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.web.filter.OncePerRequestFilter
 */
package com.sap.b1.notification.server;

import com.sap.b1.bo.base.BoLogicLanguage;
import com.sap.b1.db.DBTypeConfig;
import com.sap.b1.infra.jpa.TenantContext;
import com.sap.b1.infra.share.web.B1UserDetails;
import com.sap.b1.notification.base.ApplicationContextUtil;
import com.sap.b1.notification.cache.CacheObject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class NotificationFilter
extends OncePerRequestFilter {
    BoLogicLanguage logicLanguage;
    DBTypeConfig dbType;

    public NotificationFilter(BoLogicLanguage logicLanguage, DBTypeConfig dbType) {
        this.logicLanguage = logicLanguage;
        this.dbType = dbType;
    }

    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        } else {
            TenantContext.accquireAndSetTenantInfo((DBTypeConfig)this.dbType);
            B1UserDetails userDetails = (B1UserDetails)authentication.getPrincipal();
            Locale locale = this.logicLanguage.getLanguage(userDetails.getUsername());
            LocaleContextHolder.setDefaultLocale((Locale)locale);
            ApplicationContext context = ApplicationContextUtil.getApplicationContext();
            CacheObject cacheObject = (CacheObject)context.getBean(CacheObject.class);
            cacheObject.ensureCache(userDetails);
        }
        filterChain.doFilter((ServletRequest)httpServletRequest, (ServletResponse)httpServletResponse);
        TenantContext.clear();
    }
}

