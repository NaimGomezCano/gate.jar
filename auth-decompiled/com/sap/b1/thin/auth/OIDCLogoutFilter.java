/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.logger.AuditLoggerUtil
 *  com.sap.b1.sdk.logger.audit.SecurityEventCategory$SubCategory
 *  com.sap.b1.thin.auth.AccessLog.AccessLogUtils
 *  com.sap.b1.thin.auth.OIDCLogoutFilter
 *  com.sap.b1.thin.auth.OIDCSession
 *  jakarta.servlet.Filter
 *  jakarta.servlet.FilterChain
 *  jakarta.servlet.ServletException
 *  jakarta.servlet.ServletRequest
 *  jakarta.servlet.ServletResponse
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpSession
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.sap.b1.thin.auth;

import com.sap.b1.logger.AuditLoggerUtil;
import com.sap.b1.sdk.logger.audit.SecurityEventCategory;
import com.sap.b1.thin.auth.AccessLog.AccessLogUtils;
import com.sap.b1.thin.auth.OIDCSession;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OIDCLogoutFilter
implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(OIDCLogoutFilter.class);
    private AccessLogUtils accessLogUtils;

    public void setAccessLogUtils(AccessLogUtils accessLogUtils) {
        this.accessLogUtils = accessLogUtils;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpSession session = req.getSession(false);
        OIDCSession oidcSession = null;
        if (session != null) {
            oidcSession = (OIDCSession)session.getAttribute("session");
        }
        this.accessLogUtils.logLogoffAction(request, oidcSession);
        if (null != oidcSession) {
            AuditLoggerUtil.logAudit((SecurityEventCategory.SubCategory)SecurityEventCategory.SubCategory.LOGOUT, (String)String.format("Security Event:\t%s:%s:%s\t%s\tLogoutSucceed: Logout webclient succeeded", oidcSession.getDbServerName(), oidcSession.getCompanyDatabaseName(), oidcSession.getSboUserCode(), request.getParameter("url")));
        }
        chain.doFilter(request, response);
    }
}

