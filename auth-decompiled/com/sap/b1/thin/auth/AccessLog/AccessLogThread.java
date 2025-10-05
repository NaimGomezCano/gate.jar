/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.auth.AccessLog.AccessLog
 *  com.sap.b1.thin.auth.AccessLog.AccessLogThread
 *  com.sap.b1.thin.auth.AccessLog.AccessLogUtils
 *  com.sap.b1.thin.auth.AccessLog.LoginType
 *  com.sap.b1.thin.auth.AccessLog.SupportUserLoginRecord
 *  com.sap.b1.thin.auth.AccessLog.UserAccessLog
 *  com.sap.b1.thin.auth.CloneUtil
 *  com.sap.b1.thin.auth.OIDCSession
 *  com.sap.b1.thin.auth.SupportService
 *  jakarta.servlet.ServletRequest
 *  jakarta.servlet.http.HttpServletRequest
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.sap.b1.thin.auth.AccessLog;

import com.sap.b1.thin.auth.AccessLog.AccessLog;
import com.sap.b1.thin.auth.AccessLog.AccessLogUtils;
import com.sap.b1.thin.auth.AccessLog.LoginType;
import com.sap.b1.thin.auth.AccessLog.SupportUserLoginRecord;
import com.sap.b1.thin.auth.AccessLog.UserAccessLog;
import com.sap.b1.thin.auth.CloneUtil;
import com.sap.b1.thin.auth.OIDCSession;
import com.sap.b1.thin.auth.SupportService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AccessLogThread
implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogThread.class);
    private OIDCSession oidcSession;
    private AccessLogUtils accessLogUtils;
    private SupportService supportService;
    private ServletRequest request;

    public AccessLogThread(SupportService supportService, AccessLogUtils accessLogUtils, OIDCSession oidcSession, ServletRequest request) {
        this.supportService = supportService;
        this.accessLogUtils = accessLogUtils;
        this.oidcSession = oidcSession;
        this.request = request;
    }

    @Override
    public void run() {
        try {
            AccessLog loginAccessLog = this.oidcSession.getLoginAccessLog();
            if (loginAccessLog == null) {
                LOGGER.error("LoginAccessLog is empty in ssoSession");
                return;
            }
            AccessLog logoffAccessLog = new AccessLog();
            logoffAccessLog.setUserAccessLog((UserAccessLog)CloneUtil.clone((Serializable)loginAccessLog.getUserAccessLog()));
            UserAccessLog userAccessLog = logoffAccessLog.getUserAccessLog();
            if (userAccessLog == null) {
                LOGGER.error("UserAccessLog is empty after clone from loginAccessLog");
                return;
            }
            userAccessLog.setAction(LoginType.LOGOFF_SUCCEED.getValue());
            if (this.supportService.isOPSupportUser(this.oidcSession.getSboUserCode())) {
                logoffAccessLog.setSupportUserLoginRecord((SupportUserLoginRecord)CloneUtil.clone((Serializable)loginAccessLog.getSupportUserLoginRecord()));
            }
            Thread.sleep(20000L);
            this.accessLogUtils.requestServiceLayerLogoffAction((HttpServletRequest)this.request, this.oidcSession, logoffAccessLog);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}

