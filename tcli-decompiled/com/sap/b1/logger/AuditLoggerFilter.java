/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.logger.AuditLoggerFilter
 *  com.sap.b1.sdk.oidc.core.dto.UserInfo
 *  com.sap.b1.sdk.oidc.web.LoginPrincipal
 *  com.sap.b1.sdk.oidc.web.SecurityContexts
 *  com.sap.b1.sdk.oidc.web.WebUtils
 *  jakarta.servlet.FilterChain
 *  jakarta.servlet.ServletException
 *  jakarta.servlet.ServletRequest
 *  jakarta.servlet.ServletResponse
 *  jakarta.servlet.http.HttpFilter
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  org.slf4j.MDC
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.logger;

import com.sap.b1.sdk.oidc.core.dto.UserInfo;
import com.sap.b1.sdk.oidc.web.LoginPrincipal;
import com.sap.b1.sdk.oidc.web.SecurityContexts;
import com.sap.b1.sdk.oidc.web.WebUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class AuditLoggerFilter
extends HttpFilter {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            LoginPrincipal loginPrincipal = SecurityContexts.getLoginPrincipal((HttpServletRequest)request);
            if (loginPrincipal != null && loginPrincipal.getUserInfo() != null) {
                UserInfo userInfo = loginPrincipal.getUserInfo();
                MDC.put((String)"CompanySchema", (String)userInfo.getCompanySchema());
                MDC.put((String)"TenantId", (String)userInfo.getCompanyId());
                MDC.put((String)"UserName", (String)userInfo.getUserName());
                MDC.put((String)"B1UserCode", (String)userInfo.getB1UserCode());
                MDC.put((String)"SourceIp", (String)WebUtils.getClientIpAddress((HttpServletRequest)request));
            }
            chain.doFilter((ServletRequest)request, (ServletResponse)response);
        }
        finally {
            MDC.remove((String)"CompanySchema");
            MDC.remove((String)"TenantId");
            MDC.remove((String)"UserName");
            MDC.remove((String)"B1UserCode");
            MDC.remove((String)"SourceIp");
        }
    }
}

