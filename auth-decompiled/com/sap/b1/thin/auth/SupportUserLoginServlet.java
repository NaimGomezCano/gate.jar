/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.boot.sld.SldClient
 *  com.sap.b1.boot.sld.WorkMode
 *  com.sap.b1.boot.sld.feign.SldJsonService
 *  com.sap.b1.boot.sld.feign.entity.ODataReturn
 *  com.sap.b1.boot.sld.feign.entity.SBOUserInfo
 *  com.sap.b1.boot.sld.feign.entity.SystemUser
 *  com.sap.b1.sdk.oidc.core.dto.DatabaseInfo
 *  com.sap.b1.sdk.oidc.core.dto.UserInfo
 *  com.sap.b1.sdk.oidc.core.handler.token.AccessToken
 *  com.sap.b1.sdk.oidc.core.http.HttpAgentFactory
 *  com.sap.b1.sdk.oidc.core.service.SLDClient
 *  com.sap.b1.sdk.oidc.web.LoginPrincipal
 *  com.sap.b1.sdk.oidc.web.SecurityContexts
 *  com.sap.b1.thin.auth.AccessLog.AccessLog
 *  com.sap.b1.thin.auth.AccessLog.AccessLogUtils
 *  com.sap.b1.thin.auth.AccessLog.SupportUserLoginRecord
 *  com.sap.b1.thin.auth.AccessLog.UserAccessLog
 *  com.sap.b1.thin.auth.LoginReason
 *  com.sap.b1.thin.auth.OIDCSession
 *  com.sap.b1.thin.auth.OIDCUtils
 *  com.sap.b1.thin.auth.SupportService
 *  com.sap.b1.thin.auth.SupportUserLoginResponse
 *  com.sap.b1.thin.auth.SupportUserLoginServlet
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  jakarta.servlet.http.HttpSession
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.validation.annotation.Validated
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.thin.auth;

import com.sap.b1.boot.Bootstrap;
import com.sap.b1.boot.sld.SldClient;
import com.sap.b1.boot.sld.WorkMode;
import com.sap.b1.boot.sld.feign.SldJsonService;
import com.sap.b1.boot.sld.feign.entity.ODataReturn;
import com.sap.b1.boot.sld.feign.entity.SBOUserInfo;
import com.sap.b1.boot.sld.feign.entity.SystemUser;
import com.sap.b1.sdk.oidc.core.dto.DatabaseInfo;
import com.sap.b1.sdk.oidc.core.dto.UserInfo;
import com.sap.b1.sdk.oidc.core.handler.token.AccessToken;
import com.sap.b1.sdk.oidc.core.http.HttpAgentFactory;
import com.sap.b1.sdk.oidc.core.service.SLDClient;
import com.sap.b1.sdk.oidc.web.LoginPrincipal;
import com.sap.b1.sdk.oidc.web.SecurityContexts;
import com.sap.b1.thin.auth.AccessLog.AccessLog;
import com.sap.b1.thin.auth.AccessLog.AccessLogUtils;
import com.sap.b1.thin.auth.AccessLog.SupportUserLoginRecord;
import com.sap.b1.thin.auth.AccessLog.UserAccessLog;
import com.sap.b1.thin.auth.LoginReason;
import com.sap.b1.thin.auth.OIDCSession;
import com.sap.b1.thin.auth.OIDCUtils;
import com.sap.b1.thin.auth.SupportService;
import com.sap.b1.thin.auth.SupportUserLoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.lang.invoke.CallSite;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupportUserLoginServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupportUserLoginServlet.class);
    @Autowired
    OIDCUtils oidcUtils;
    @Autowired
    SupportService supportService;
    @Autowired
    AccessLogUtils accessLogUtils;
    private Map<String, String> REASON_ID_OP = new /* Unavailable Anonymous Inner Class!! */;
    private Map<String, Integer> REASON_ID_OD = new /* Unavailable Anonymous Inner Class!! */;

    @RequestMapping(method={RequestMethod.POST}, path={"/support/login.svc"})
    public SupportUserLoginResponse service(HttpServletRequest request, HttpServletResponse response, @Validated @RequestBody LoginReason loginReasonParams) {
        SupportUserLoginResponse supportUserLoginResponse = new SupportUserLoginResponse();
        HttpSession session = request.getSession();
        OIDCSession oidcSession = (OIDCSession)session.getAttribute("session");
        if (oidcSession != null) {
            String sboUserCode = oidcSession.getSboUserCode();
            if (!this.supportService.isOPSupportUser(sboUserCode) && !this.supportService.isODSupportUser(oidcSession)) {
                LOGGER.error("Invalid user");
                response.setStatus(401);
                supportUserLoginResponse.setErrorCode("INVALID_USER");
                return supportUserLoginResponse;
            }
            try {
                LOGGER.info("Set login reason");
                AccessLog loginAccessLog = oidcSession.getLoginAccessLog();
                if (loginAccessLog == null) {
                    LOGGER.error("LoginAccessLog in ssoSession is null");
                    return supportUserLoginResponse;
                }
                if (WorkMode.isOnPremise()) {
                    SupportUserLoginRecord supportUserLoginRecord = loginAccessLog.getSupportUserLoginRecord();
                    if (supportUserLoginRecord == null) {
                        LOGGER.error("SupportUserLoginRecord in accessLog of ssoSession is null");
                        return supportUserLoginResponse;
                    }
                    String loginReasonId = (String)this.REASON_ID_OP.get(loginReasonParams.getLoginReason());
                    supportUserLoginRecord.setLogReason(loginReasonId);
                    supportUserLoginRecord.setRealName(loginReasonParams.getRealName());
                    supportUserLoginRecord.setLogDetail(loginReasonParams.getComments());
                    loginAccessLog.setSupportUserLoginRecord(supportUserLoginRecord);
                } else if (WorkMode.isOnDemand()) {
                    Integer loginReasonId = (Integer)this.REASON_ID_OD.get(loginReasonParams.getLoginReason());
                    UserAccessLog userAccessLog = loginAccessLog.getUserAccessLog();
                    userAccessLog.setReasonID(loginReasonId);
                    userAccessLog.setReasonDesc(loginReasonParams.getComments());
                }
                oidcSession.setSupportUserLogin(true);
                this.accessLogUtils.requestServiceLayerLogLoginAction(request, oidcSession, loginAccessLog);
                return supportUserLoginResponse;
            }
            catch (Exception e) {
                LOGGER.error(e.getMessage(), (Throwable)e);
                LOGGER.error("Support user login reason failed");
                supportUserLoginResponse.setErrorCode("INTERNAL_SERVER_ERROR");
            }
        } else {
            LOGGER.error("Session not exist");
            response.setStatus(401);
            supportUserLoginResponse.setErrorCode("UNAUTHORIZED");
        }
        return supportUserLoginResponse;
    }

    @RequestMapping(method={RequestMethod.GET}, path={"/support/getloginstatus.svc"})
    public Boolean service(HttpServletRequest request) {
        HttpSession session = request.getSession();
        OIDCSession oidcSession = (OIDCSession)session.getAttribute("session");
        if (oidcSession != null) {
            return oidcSession.getSupportUserLogin();
        }
        LOGGER.info("Session not exist");
        return false;
    }

    @RequestMapping(method={RequestMethod.GET}, path={"/support/getSystemUser.svc"})
    public SystemUser getSystemUser(HttpServletRequest request) {
        LoginPrincipal loginPrincipal = SecurityContexts.getLoginPrincipal((HttpServletRequest)request);
        if (null == loginPrincipal) {
            LOGGER.error("User is not authorized to get system user");
            return null;
        }
        try {
            SystemUser d;
            UserInfo userInfo = loginPrincipal.getUserInfo();
            AccessToken accessToken = loginPrincipal.getAccessToken();
            SLDClient sldAgent = HttpAgentFactory.createSLDClient();
            DatabaseInfo databaseInfo = sldAgent.getInstanceDatabaseInfo();
            LOGGER.info("Get System User");
            HashMap<String, CallSite> headerMap = new HashMap<String, CallSite>();
            headerMap.put("Authorization", (CallSite)((Object)("Bearer " + accessToken.getAccessToken())));
            SBOUserInfo sboUserInfo = new SBOUserInfo();
            sboUserInfo.setDbInstance(databaseInfo.getName());
            sboUserInfo.setCompanyDb(userInfo.getCompanySchema());
            sboUserInfo.setSboUser(userInfo.getB1UserCode());
            Bootstrap bootstrap = Bootstrap.getInstance();
            SldClient sldClient = bootstrap.getSldClient();
            SldJsonService sldJsonService = sldClient.getSldJsonService();
            LOGGER.info("Requesting to /sld/sld0100.svc/GetSystemUserBySBOUserOnCompany?$format=json");
            ODataReturn systemUserReturn = sldJsonService.getSystemUserBySBOUserOnCompany(sboUserInfo, headerMap);
            if (systemUserReturn != null && (d = (SystemUser)systemUserReturn.getD()) != null) {
                return d;
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), (Throwable)e);
        }
        return null;
    }
}

