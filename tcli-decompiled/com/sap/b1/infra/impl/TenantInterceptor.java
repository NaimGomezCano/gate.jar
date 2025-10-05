/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.db.DBTypeConfig
 *  com.sap.b1.infra.impl.TenantInterceptor
 *  com.sap.b1.infra.jpa.TenantContext
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 *  org.springframework.web.servlet.HandlerInterceptor
 *  org.springframework.web.servlet.ModelAndView
 */
package com.sap.b1.infra.impl;

import com.sap.b1.db.DBTypeConfig;
import com.sap.b1.infra.jpa.TenantContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TenantInterceptor
implements HandlerInterceptor {
    @Autowired
    DBTypeConfig dbType;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TenantContext.accquireAndSetTenantInfo((DBTypeConfig)this.dbType);
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }
}

