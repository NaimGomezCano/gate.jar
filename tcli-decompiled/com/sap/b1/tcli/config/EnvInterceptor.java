/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.app.base.AppEnv
 *  com.sap.b1.tcli.config.EnvInterceptor
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 *  org.springframework.web.servlet.HandlerInterceptor
 */
package com.sap.b1.tcli.config;

import com.sap.b1.app.base.AppEnv;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class EnvInterceptor
implements HandlerInterceptor {
    @Autowired
    AppEnv env;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.env.getSchema();
        return true;
    }
}

