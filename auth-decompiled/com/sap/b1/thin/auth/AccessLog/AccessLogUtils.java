/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.boot.sld.WorkMode
 *  com.sap.b1.thin.auth.AccessLog.AccessLog
 *  com.sap.b1.thin.auth.AccessLog.AccessLogThread
 *  com.sap.b1.thin.auth.AccessLog.AccessLogUtils
 *  com.sap.b1.thin.auth.AccessLog.LoginType
 *  com.sap.b1.thin.auth.AccessLog.SupportUserLoginRecord
 *  com.sap.b1.thin.auth.AccessLog.UserAccessLog
 *  com.sap.b1.thin.auth.DbService
 *  com.sap.b1.thin.auth.OIDCServiceLayerService
 *  com.sap.b1.thin.auth.OIDCSession
 *  com.sap.b1.thin.auth.OIDCUtils
 *  com.sap.b1.thin.auth.SupportService
 *  feign.Client
 *  feign.Feign
 *  feign.Logger
 *  feign.Logger$Level
 *  feign.Response
 *  feign.Response$Body
 *  feign.Util
 *  feign.codec.Encoder
 *  feign.hc5.ApacheHttp5Client
 *  feign.jackson.JacksonEncoder
 *  feign.slf4j.Slf4jLogger
 *  jakarta.servlet.ServletRequest
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpSession
 *  org.apache.hc.client5.http.classic.HttpClient
 *  org.apache.hc.client5.http.impl.classic.CloseableHttpClient
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.http.HttpStatus
 *  org.springframework.util.StringUtils
 */
package com.sap.b1.thin.auth.AccessLog;

import com.google.gson.Gson;
import com.sap.b1.boot.Bootstrap;
import com.sap.b1.boot.sld.WorkMode;
import com.sap.b1.thin.auth.AccessLog.AccessLog;
import com.sap.b1.thin.auth.AccessLog.AccessLogThread;
import com.sap.b1.thin.auth.AccessLog.LoginType;
import com.sap.b1.thin.auth.AccessLog.SupportUserLoginRecord;
import com.sap.b1.thin.auth.AccessLog.UserAccessLog;
import com.sap.b1.thin.auth.DbService;
import com.sap.b1.thin.auth.OIDCServiceLayerService;
import com.sap.b1.thin.auth.OIDCSession;
import com.sap.b1.thin.auth.OIDCUtils;
import com.sap.b1.thin.auth.SupportService;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.Response;
import feign.Util;
import feign.codec.Encoder;
import feign.hc5.ApacheHttp5Client;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Reader;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class AccessLogUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogUtils.class);
    @Autowired
    DbService dbService;
    @Autowired
    SupportService supportService;
    @Autowired
    OIDCUtils oidcUtils;
    @Autowired
    CloseableHttpClient httpClient;

    private void init(ServletRequest request, OIDCSession oidcSession, AccessLog accessLog, LoginType loginType) {
        try {
            UserAccessLog userAccessLog = new UserAccessLog();
            if (WorkMode.isOnPremise()) {
                SupportUserLoginRecord supportUserLoginRecord = new SupportUserLoginRecord();
                accessLog.setSupportUserLoginRecord(supportUserLoginRecord);
            }
            userAccessLog.setUserCode(oidcSession.getSboUserCode());
            userAccessLog.setAction(loginType.getValue());
            userAccessLog.setActionBy(oidcSession.getSboUserCode());
            userAccessLog.setClientIP(this.getIpAddress((HttpServletRequest)request));
            userAccessLog.setClientName(this.getHostName(userAccessLog.getClientIP()));
            userAccessLog.setProcName("Web Client");
            userAccessLog.setProcessID(this.getPId());
            userAccessLog.setSessionID(this.getSessionId());
            userAccessLog.setSource("SBO_Web_Client");
            Integer userId = this.dbService.getUserId(oidcSession.getCompanyDatabaseName(), oidcSession.getSboUserCode());
            userAccessLog.setUserId(userId);
            accessLog.setUserAccessLog(userAccessLog);
        }
        catch (Exception e) {
            LOGGER.error("Init UserAccessLog failed.");
        }
    }

    public void requestServiceLayerLogLoginAction(HttpServletRequest request, OIDCSession oidcSession, AccessLog loginAccessLog) {
        if (oidcSession.getCompanyDatabase() == null) {
            LOGGER.error("CompanyDatabase is empty");
            return;
        }
        UserAccessLog userAccessLog = loginAccessLog.getUserAccessLog();
        String companyDatabaseName = oidcSession.getCompanyDatabaseName();
        int tzOffset = 0;
        try {
            tzOffset = this.dbService.getTimeZoneOffset(companyDatabaseName);
        }
        catch (SQLException e) {
            LOGGER.error("Read ZoneTime failed.");
        }
        ZoneId zoneId = ZoneId.ofOffset("", ZoneOffset.ofTotalSeconds(tzOffset));
        LocalDateTime now = ZonedDateTime.now(zoneId).toLocalDateTime();
        userAccessLog.setActionDate(this.getDate(now));
        userAccessLog.setActionTime(this.getTime(now));
        LOGGER.info("Log Login Action");
        try {
            Bootstrap bootstrap = Bootstrap.getInstance();
            String svclUrl = bootstrap.getUrl("svcl");
            ApacheHttp5Client apacheHttpClient = new ApacheHttp5Client((HttpClient)this.httpClient);
            OIDCServiceLayerService serviceLayerService = (OIDCServiceLayerService)Feign.builder().encoder((Encoder)new JacksonEncoder()).client((Client)apacheHttpClient).logger((feign.Logger)new Slf4jLogger(this.getClass())).logLevel(Logger.Level.FULL).target(OIDCServiceLayerService.class, svclUrl);
            LOGGER.info("Requesting to /b1s/v1/CompanyService_LogLoginAction");
            HashMap<String, Object> headerMap = new HashMap<String, Object>();
            headerMap.put("Authorization", "Bearer " + this.oidcUtils.getAccessToken(request, oidcSession));
            headerMap.put("CompanyId", oidcSession.getCompanyDatabase().getId());
            headerMap.put("Language", oidcSession.getLanguage());
            try (Response response = serviceLayerService.requestLogLoginAction(loginAccessLog, headerMap);){
                Response.Body body = response.body();
                try (Reader reader = body.asReader();){
                    String result = Util.toString((Reader)reader);
                    if (response.status() != HttpStatus.OK.value()) {
                        throw new RuntimeException(result);
                    }
                    if (this.supportService.isOPSupportUser(oidcSession.getSboUserCode())) {
                        Gson gson = new Gson();
                        SupportUserLoginRecord supportUserLoginRecord = (SupportUserLoginRecord)gson.fromJson(result, SupportUserLoginRecord.class);
                        SupportUserLoginRecord supportUserLoginRecordInSession = loginAccessLog.getSupportUserLoginRecord();
                        if (supportUserLoginRecordInSession != null) {
                            supportUserLoginRecordInSession.setId(supportUserLoginRecord.getId());
                        } else {
                            LOGGER.error("No SupportUserLoginRecord in Response body of Service Layer API /CompanyService_LogLoginAction");
                        }
                    }
                }
                catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
            catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void requestServiceLayerLogoffAction(HttpServletRequest request, OIDCSession oidcSession, AccessLog logoffAccessLog) {
        block21: {
            UserAccessLog logoffUserAccessLog = logoffAccessLog.getUserAccessLog();
            AccessLog loginAccessLog = oidcSession.getLoginAccessLog();
            if (loginAccessLog == null) {
                LOGGER.error("No login Access log in ssoSession when logoff");
                return;
            }
            UserAccessLog loginUserAccessLog = loginAccessLog.getUserAccessLog();
            logoffUserAccessLog.setActionDate(loginUserAccessLog.getActionDate());
            logoffUserAccessLog.setActionTime(loginUserAccessLog.getActionTime());
            if (oidcSession.getCompanyDatabase() == null) {
                LOGGER.error("CompanyDatabase is empty");
                return;
            }
            try {
                Bootstrap bootstrap = Bootstrap.getInstance();
                String svclUrl = bootstrap.getUrl("svcl");
                ApacheHttp5Client apacheHttpClient = new ApacheHttp5Client((HttpClient)this.httpClient);
                OIDCServiceLayerService serviceLayerService = (OIDCServiceLayerService)Feign.builder().encoder((Encoder)new JacksonEncoder()).client((Client)apacheHttpClient).logger((feign.Logger)new Slf4jLogger(this.getClass())).logLevel(Logger.Level.FULL).target(OIDCServiceLayerService.class, svclUrl);
                LOGGER.info("Requesting to /b1s/v1/CompanyService_LogLogoffAction");
                HashMap<String, Object> headerMap = new HashMap<String, Object>();
                headerMap.put("Authorization", "Bearer " + this.oidcUtils.getAccessToken(request, oidcSession));
                headerMap.put("CompanyId", oidcSession.getCompanyDatabase().getId());
                headerMap.put("Language", oidcSession.getLanguage());
                try (Response response = serviceLayerService.requestLogLogoffAction(logoffAccessLog, headerMap);){
                    request.getSession().removeAttribute("logonLicenseException");
                    Response.Body body = response.body();
                    if (response.status() == HttpStatus.NO_CONTENT.value()) {
                        return;
                    }
                    if (body == null) break block21;
                    try {
                        Reader reader = body.asReader();
                        try {
                            String result = Util.toString((Reader)reader);
                            throw new RuntimeException(result);
                        }
                        catch (Throwable throwable) {
                            if (reader != null) {
                                try {
                                    reader.close();
                                }
                                catch (Throwable throwable2) {
                                    throwable.addSuppressed(throwable2);
                                }
                            }
                            throw throwable;
                        }
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
                catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }
            catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    private String getIpAddress(HttpServletRequest request) throws UnknownHostException {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-Ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (!StringUtils.isEmpty((Object)ip) && ip.substring(0, 3).equals("127")) {
            LOGGER.info("Get local address");
            InetAddress address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
        }
        if (ip.contains(",")) {
            return ip.split(",")[0];
        }
        return ip;
    }

    private String getDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(date);
    }

    private String getTime(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        return formatter.format(date);
    }

    private Integer getPId() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        return Integer.valueOf(pid);
    }

    private String getHostName(String ip) {
        try {
            InetAddress addr = InetAddress.getByName(ip);
            byte[] ipBytes = addr.getAddress();
            InetAddress inetAddr = InetAddress.getByAddress(ipBytes);
            return inetAddr.getHostName();
        }
        catch (UnknownHostException e) {
            LOGGER.error(e.getMessage());
            LOGGER.error("Get HostName Failed, return empty");
            return "";
        }
    }

    private Integer getSessionId() throws SQLException {
        return this.dbService.getSessionId();
    }

    public void logLogoffAction(ServletRequest request, OIDCSession oidcSession) {
        LOGGER.info("Start to log logoff action to access log");
        if (oidcSession == null) {
            LOGGER.warn("User not login, no need log logoff action");
            return;
        }
        AccessLog logoffAccessLog = new AccessLog();
        this.init(request, oidcSession, logoffAccessLog, LoginType.LOGOFF_SUCCEED);
        if (LoginType.LOGON_FAILED == oidcSession.getLoginType()) {
            Boolean isLicenseEx;
            HttpServletRequest req = (HttpServletRequest)request;
            HttpSession session = req.getSession();
            Boolean bl = isLicenseEx = session.getAttribute("logonLicenseException") != null ? Boolean.TRUE : Boolean.FALSE;
            if (isLicenseEx.booleanValue()) {
                this.requestServiceLayerLogoffAction((HttpServletRequest)request, oidcSession, logoffAccessLog);
            }
            LOGGER.warn("Login failed, no need log logoff action twice");
            return;
        }
        if (this.supportService.isOPSupportUser(oidcSession.getSboUserCode())) {
            if (!oidcSession.getSupportUserLogin()) {
                LOGGER.error("Support user not input login reason");
                return;
            }
            AccessLog loginAccessLog = oidcSession.getLoginAccessLog();
            if (loginAccessLog == null) {
                LOGGER.error("No loginAccessLog in ssoSession for OP Support user");
                return;
            }
            UserAccessLog userAccessLog = loginAccessLog.getUserAccessLog();
            SupportUserLoginRecord supportUserLoginRecord = loginAccessLog.getSupportUserLoginRecord();
            if (userAccessLog == null || supportUserLoginRecord == null) {
                LOGGER.error("No login access log in ssoSession");
                return;
            }
            String loginRecordId = supportUserLoginRecord.getId();
            if (!StringUtils.hasText((String)loginRecordId)) {
                LOGGER.error("No SupportUserLoginRecord ID in ssoSession");
                return;
            }
            SupportUserLoginRecord logoffSupportUserLog = logoffAccessLog.getSupportUserLoginRecord();
            logoffSupportUserLog.setId(loginRecordId);
        }
        this.requestServiceLayerLogoffAction((HttpServletRequest)request, oidcSession, logoffAccessLog);
    }

    public void logLoginAction(HttpServletRequest request, OIDCSession oidcSession, LoginType loginType) {
        LOGGER.info("Start to log login action to access log: loginType [{}]", (Object)loginType);
        if (oidcSession == null) {
            LOGGER.warn("Fail to find user login info");
            return;
        }
        if (oidcSession.getLoginAccessLog() != null) {
            return;
        }
        AccessLog loginAccessLog = new AccessLog();
        try {
            if (loginType == LoginType.LOGON_FAILED) {
                this.init((ServletRequest)request, oidcSession, loginAccessLog, LoginType.LOGIN_SUCCEED);
                this.requestServiceLayerLogLoginAction(request, oidcSession, loginAccessLog);
                oidcSession.setLoginAccessLog(loginAccessLog);
                oidcSession.setLoginType(LoginType.LOGON_FAILED);
                Thread thread = new Thread((Runnable)new AccessLogThread(this.supportService, this, oidcSession, (ServletRequest)request));
                thread.start();
            } else if (loginType == LoginType.LOGIN_SUCCEED) {
                this.init((ServletRequest)request, oidcSession, loginAccessLog, loginType);
                if (!this.supportService.isOPSupportUser(oidcSession.getSboUserCode()) && !this.supportService.isODSupportUser(oidcSession)) {
                    this.requestServiceLayerLogLoginAction(request, oidcSession, loginAccessLog);
                }
                oidcSession.setLoginAccessLog(loginAccessLog);
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}

