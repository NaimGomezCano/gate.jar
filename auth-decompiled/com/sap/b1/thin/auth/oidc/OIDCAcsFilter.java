/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.sap.b1.logger.AuditLoggerUtil
 *  com.sap.b1.sdk.logger.audit.SecurityEventCategory$SubCategory
 *  com.sap.b1.sdk.oidc.core.dto.UserInfo
 *  com.sap.b1.sdk.oidc.core.dto.sld.OauthClientInfo
 *  com.sap.b1.sdk.oidc.core.dto.sld.ResultSet
 *  com.sap.b1.sdk.oidc.core.exception.B1SDKRuntimeException
 *  com.sap.b1.sdk.oidc.core.handler.token.AccessToken
 *  com.sap.b1.sdk.oidc.core.http.HttpAgentFactory
 *  com.sap.b1.sdk.oidc.web.LoginPrincipal
 *  com.sap.b1.sdk.oidc.web.SecurityContexts
 *  com.sap.b1.sdk.oidc.web.WebUtils
 *  com.sap.b1.sdk.oidc.web.handler.UserAccessHandler
 *  com.sap.b1.thin.auth.AccessLog.AccessLogUtils
 *  com.sap.b1.thin.auth.AccessLog.LoginType
 *  com.sap.b1.thin.auth.LicenseException
 *  com.sap.b1.thin.auth.OIDCSession
 *  com.sap.b1.thin.auth.OIDCUtils
 *  com.sap.b1.thin.auth.oidc.OIDCAcsFilter
 *  jakarta.servlet.Filter
 *  jakarta.servlet.FilterChain
 *  jakarta.servlet.FilterConfig
 *  jakarta.servlet.ServletException
 *  jakarta.servlet.ServletRequest
 *  jakarta.servlet.ServletResponse
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  jakarta.servlet.http.HttpSession
 *  org.apache.commons.lang3.StringEscapeUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.hc.core5.http.ContentType
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.slf4j.MDC
 *  org.springframework.http.HttpStatus
 */
package com.sap.b1.thin.auth.oidc;

import com.google.gson.Gson;
import com.sap.b1.logger.AuditLoggerUtil;
import com.sap.b1.sdk.logger.audit.SecurityEventCategory;
import com.sap.b1.sdk.oidc.core.dto.UserInfo;
import com.sap.b1.sdk.oidc.core.dto.sld.OauthClientInfo;
import com.sap.b1.sdk.oidc.core.dto.sld.ResultSet;
import com.sap.b1.sdk.oidc.core.exception.B1SDKRuntimeException;
import com.sap.b1.sdk.oidc.core.handler.token.AccessToken;
import com.sap.b1.sdk.oidc.core.http.HttpAgentFactory;
import com.sap.b1.sdk.oidc.web.LoginPrincipal;
import com.sap.b1.sdk.oidc.web.SecurityContexts;
import com.sap.b1.sdk.oidc.web.WebUtils;
import com.sap.b1.sdk.oidc.web.handler.UserAccessHandler;
import com.sap.b1.thin.auth.AccessLog.AccessLogUtils;
import com.sap.b1.thin.auth.AccessLog.LoginType;
import com.sap.b1.thin.auth.LicenseException;
import com.sap.b1.thin.auth.OIDCSession;
import com.sap.b1.thin.auth.OIDCUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

public class OIDCAcsFilter
implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(OIDCAcsFilter.class);
    private OIDCUtils oidcUtils;
    private AccessLogUtils accessLogUtils;

    public void setOidcUtils(OIDCUtils oidcUtils) {
        this.oidcUtils = oidcUtils;
    }

    public void setAccessLogUtils(AccessLogUtils accessLogUtils) {
        this.accessLogUtils = accessLogUtils;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        OIDCSession oidcSession = null;
        String originalUrl = null;
        try {
            LOGGER.info("Begin auth login");
            originalUrl = this.fetchRedirectUrl(req);
            if (StringUtils.isNotEmpty((CharSequence)originalUrl) && !this.isRedirectUrlValid(originalUrl)) {
                resp.setStatus(HttpStatus.FORBIDDEN.value());
                resp.getWriter().write("Redirection to the specified URL is not allowed.");
                return;
            }
            LoginPrincipal loginPrincipal = SecurityContexts.getLoginPrincipal((HttpServletRequest)req);
            AccessToken accessToken = loginPrincipal.getAccessToken();
            UserInfo userInfo = loginPrincipal.getUserInfo();
            String companyDatabaseName = userInfo.getCompanySchema();
            String sboUserCode = userInfo.getB1UserCode();
            MDC.put((String)"CompanySchema", (String)companyDatabaseName);
            MDC.put((String)"TenantId", (String)userInfo.getCompanyId());
            MDC.put((String)"UserName", (String)userInfo.getUserName());
            MDC.put((String)"B1UserCode", (String)sboUserCode);
            MDC.put((String)"SourceIp", (String)WebUtils.getClientIpAddress((HttpServletRequest)((HttpServletRequest)request)));
            oidcSession = new OIDCSession();
            oidcSession.setCompanyDatabaseName(companyDatabaseName);
            oidcSession.setSboUserCode(sboUserCode);
            oidcSession.setAccessToken(accessToken.getAccessToken());
            HttpSession session = req.getSession();
            session.setAttribute("session", (Object)oidcSession);
            this.oidcUtils.retrieveCompanyDatabaseInfo(oidcSession, companyDatabaseName, req);
            this.oidcUtils.setLanguage(oidcSession);
            this.oidcUtils.retrieveInstallationNumber(oidcSession, req);
            String licenses = this.oidcUtils.checkLicense(oidcSession, sboUserCode, req);
            this.oidcUtils.checkLocalization(oidcSession);
            if (this.oidcUtils.getB1UserIsLocked(companyDatabaseName, sboUserCode)) {
                throw new RuntimeException("User is locked in B1Client.");
            }
            try {
                AuditLoggerUtil.logAudit((SecurityEventCategory.SubCategory)SecurityEventCategory.SubCategory.LOGIN, (String)String.format("Security Event:\t%s:%s:%s\t%s\t LoginSucceed: Login webclient succeeded", oidcSession.getDbServerName(), oidcSession.getCompanyDatabaseName(), oidcSession.getSboUserCode(), originalUrl));
            }
            catch (B1SDKRuntimeException e) {
                LOGGER.error("AuditLog error: Read b1app config failed in oidc");
            }
            resp.addHeader("License", licenses);
            resp.addHeader("CompanyId", oidcSession.getCompanyDatabase().getId());
            resp.addHeader("Schema", companyDatabaseName);
            resp.addHeader("Language", oidcSession.getLanguage());
            if (oidcSession.getExpireDate() != null) {
                resp.addHeader("SupprtUserExpiredTime", String.valueOf(oidcSession.getExpireDate().getTime()));
            }
            if (oidcSession.getSvclCookies() != null) {
                resp.addHeader("x-wc-svcl-state", oidcSession.getSvclCookies());
            }
            resp.addHeader("Y-Authorization", new Gson().toJson((Object)accessToken));
            resp.sendRedirect(originalUrl);
            this.accessLogUtils.logLoginAction(req, oidcSession, LoginType.LOGIN_SUCCEED);
        }
        catch (Exception e) {
            this.handleException(req, resp, oidcSession, originalUrl, e);
        }
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp, OIDCSession oidcSession, String originalUrl, Exception e) throws IOException {
        try {
            HttpSession session = req.getSession();
            if (e instanceof LicenseException) {
                session.setAttribute("logonLicenseException", (Object)Boolean.TRUE);
            }
            this.accessLogUtils.logLoginAction(req, oidcSession, LoginType.LOGON_FAILED);
        }
        catch (Exception logEx) {
            LOGGER.error("Access Log: failed to log login failed: [{}]", (Object)logEx.getMessage());
        }
        LOGGER.error("Access Log: failed, {}", (Object)e.getMessage());
        LOGGER.info("SLD login succeeded, but webclient login failed");
        try {
            AuditLoggerUtil.logAudit((SecurityEventCategory.SubCategory)SecurityEventCategory.SubCategory.LOGIN, (String)String.format("Security Event:\t%s:%s:%s\t%s\tLoginFailed: Login webclient failed", oidcSession != null ? oidcSession.getDbServerName() : null, oidcSession != null ? oidcSession.getCompanyDatabaseName() : null, oidcSession != null ? oidcSession.getSboUserCode() : null, originalUrl));
        }
        catch (B1SDKRuntimeException logE) {
            LOGGER.error("AuditLog error: Read b1app config failed in oidc");
        }
        String rootUrl = this.oidcUtils.getRootUrl(req);
        String backUrl = rootUrl + "/auth/auth/logout";
        resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ContentType contentType = ContentType.TEXT_HTML.withCharset(StandardCharsets.UTF_8);
        resp.setContentType(contentType.toString());
        resp.getWriter().write("<!DOCTYPE html><html>\n<head>\n<meta http-equiv=\"refresh\" content=\"20;url=" + StringEscapeUtils.escapeHtml4((String)backUrl) + "\" /></head>\n<body>\n" + StringEscapeUtils.escapeHtml4((String)e.getMessage()) + "<br/><br/><a id=\"logoffLink\" href=\"" + backUrl + "\">Log Out</a></body></html>");
        resp.addHeader("License", "");
        resp.addHeader("CompanyId", "");
        resp.addHeader("Schema", "");
        resp.addHeader("Y-Authorization", "");
    }

    private boolean isRedirectUrlValid(String redirectUrl) {
        OauthClientInfo authClientInfo = HttpAgentFactory.createSLDClient().getOAuthClientInfo();
        ResultSet redirectUris = authClientInfo.getRedirectUris();
        List validUrls = redirectUris != null ? redirectUris.getResults() : Collections.emptyList();
        return UserAccessHandler.isValidAccess((String)redirectUrl, (List)validUrls);
    }

    public void destroy() {
    }

    public String fetchRedirectUrl(HttpServletRequest request) {
        String originalUrl = request.getParameter("url");
        if (originalUrl == null) {
            return this.oidcUtils.getRootUrl(request);
        }
        return originalUrl;
    }
}

