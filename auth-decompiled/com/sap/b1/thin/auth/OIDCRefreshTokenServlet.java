/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.sdk.oidc.core.exception.B1SDKRuntimeException
 *  com.sap.b1.sdk.oidc.core.handler.token.AccessToken
 *  com.sap.b1.sdk.oidc.core.handler.token.RefreshableAccessToken
 *  com.sap.b1.sdk.oidc.web.LoginPrincipal
 *  com.sap.b1.sdk.oidc.web.SecurityContexts
 *  com.sap.b1.thin.auth.OIDCRefreshTokenServlet
 *  jakarta.servlet.ServletRequest
 *  jakarta.servlet.ServletResponse
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.thin.auth;

import com.sap.b1.sdk.oidc.core.exception.B1SDKRuntimeException;
import com.sap.b1.sdk.oidc.core.handler.token.AccessToken;
import com.sap.b1.sdk.oidc.core.handler.token.RefreshableAccessToken;
import com.sap.b1.sdk.oidc.web.LoginPrincipal;
import com.sap.b1.sdk.oidc.web.SecurityContexts;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OIDCRefreshTokenServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(OIDCRefreshTokenServlet.class);

    @RequestMapping(method={RequestMethod.GET}, path={"/refreshToken"})
    public AccessToken refreshToken(ServletRequest request, ServletResponse response) {
        HttpServletResponse resp = (HttpServletResponse)response;
        try {
            LoginPrincipal loginPrincipal = SecurityContexts.getLoginPrincipal((HttpServletRequest)((HttpServletRequest)request));
            AccessToken accessToken = loginPrincipal.getAccessToken();
            if (accessToken instanceof RefreshableAccessToken) {
                RefreshableAccessToken authToken = (RefreshableAccessToken)accessToken;
                authToken.refresh();
                return accessToken;
            }
            throw new B1SDKRuntimeException("This token is not refreshable");
        }
        catch (Exception e) {
            LOGGER.error("Failed to refresh token, {}", (Object)e.getMessage());
            resp.setStatus(500);
            return null;
        }
    }
}

