/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.boot.Bootstrap
 *  com.sap.b1.boot.sld.AlgorithmEnum
 *  com.sap.b1.boot.sld.SldClient
 *  com.sap.b1.boot.sld.WorkMode
 *  com.sap.b1.boot.sld.feign.SldJsonService
 *  com.sap.b1.boot.sld.feign.entity.GetRemainingTimeParameter
 *  com.sap.b1.boot.sld.feign.entity.ODataReturn
 *  com.sap.b1.boot.sld.feign.entity.RemainingServiceConnectionTime
 *  com.sap.b1.sdk.oidc.core.handler.OAuth2Handler
 *  com.sap.b1.sdk.oidc.core.handler.token.ClientToken
 *  com.sap.b1.thin.auth.DbService
 *  com.sap.b1.thin.auth.OIDCSession
 *  com.sap.b1.thin.auth.SupportService
 *  jakarta.xml.bind.DatatypeConverter
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.thin.auth;

import com.sap.b1.boot.Bootstrap;
import com.sap.b1.boot.sld.AlgorithmEnum;
import com.sap.b1.boot.sld.SldClient;
import com.sap.b1.boot.sld.WorkMode;
import com.sap.b1.boot.sld.feign.SldJsonService;
import com.sap.b1.boot.sld.feign.entity.GetRemainingTimeParameter;
import com.sap.b1.boot.sld.feign.entity.ODataReturn;
import com.sap.b1.boot.sld.feign.entity.RemainingServiceConnectionTime;
import com.sap.b1.sdk.oidc.core.handler.OAuth2Handler;
import com.sap.b1.sdk.oidc.core.handler.token.ClientToken;
import com.sap.b1.thin.auth.DbService;
import com.sap.b1.thin.auth.OIDCSession;
import jakarta.xml.bind.DatatypeConverter;
import java.lang.invoke.CallSite;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="supportService")
public class SupportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupportService.class);
    public static final String LAST_SSR_UPLOAD_TIMESTAMP = "LastSsrUploadDateTimestamp";
    public static final String LAST_SSR_UPLOAD_TIME = "LastSsrUploadTime";
    public static final String LAST_SSR_HASH = "LastSsrHsh";
    private static final long HOURS_SLD_IS_ACTIVE = 4L;
    @Autowired
    DbService dbService;

    public Long getRemainingTime(OIDCSession oidcSession) {
        RemainingServiceConnectionTime d;
        LOGGER.info("Get Remaining Time");
        Bootstrap bootstrap = Bootstrap.getInstance();
        SldClient sldClient = bootstrap.getSldClient();
        SldJsonService sldJsonService = sldClient.getSldJsonService();
        ClientToken token = OAuth2Handler.getInstance().fetchClientCredential();
        HashMap<String, CallSite> headerMap = new HashMap<String, CallSite>();
        headerMap.put("Authorization", (CallSite)((Object)("Bearer " + token.getAccessToken())));
        LOGGER.info("Requesting to /sld/sld0100.svc/CheckRemainingServiceConnectionTime?$format=json");
        GetRemainingTimeParameter getRemainingTimeParameter = new GetRemainingTimeParameter();
        getRemainingTimeParameter.setDbInstance(oidcSession.getDbServerName());
        getRemainingTimeParameter.setAccount(oidcSession.getSboUserCode());
        getRemainingTimeParameter.setCompanyDb(oidcSession.getCompanyDatabaseName());
        ODataReturn remainingTimeReturn = sldJsonService.checkRemainingServiceConnectionTime(getRemainingTimeParameter, headerMap);
        if (remainingTimeReturn != null && (d = (RemainingServiceConnectionTime)remainingTimeReturn.getD()) != null) {
            long results = d.getRemainingServiceConnectionTime();
            return results * 60L * 1000L;
        }
        return null;
    }

    public boolean isOPSupportUser(String sboUserCode) {
        return WorkMode.isOnPremise() && "Support".equals(sboUserCode);
    }

    public boolean isODSupportUser(OIDCSession oidcSession) {
        if (!WorkMode.isOnDemand()) {
            return false;
        }
        try {
            String companyDatabaseName = oidcSession.getCompanyDatabaseName();
            String sboUserCode = oidcSession.getSboUserCode();
            String supportUsr = this.dbService.getSupportUsr(companyDatabaseName, sboUserCode);
            if ("Y".equals(supportUsr) && ("_PSU_1".equals(sboUserCode) || "_PSU_2".equals(sboUserCode))) {
                return true;
            }
        }
        catch (SQLException e) {
            LOGGER.error(e.getMessage(), (Throwable)e);
        }
        return false;
    }

    public String calculateHash(Date date) throws NoSuchAlgorithmException {
        String algorithm = AlgorithmEnum.ALG_MD5.getAlgorithm();
        String salt = "SSR Upload Date";
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = sdf2.format(date);
        String source = dateStr + salt;
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(source.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary((byte[])digest);
    }

    public boolean checkOPSupportUserActive(OIDCSession oidcSession) {
        try {
            String companyDatabaseName = oidcSession.getCompanyDatabaseName();
            Map ssrUploadInfoMap = this.dbService.getSsrUploadInfo(companyDatabaseName);
            Timestamp lastSsrUploadTimestamp = (Timestamp)ssrUploadInfoMap.get(LAST_SSR_UPLOAD_TIMESTAMP);
            long lastSsrUploadTime = (Long)ssrUploadInfoMap.get(LAST_SSR_UPLOAD_TIME);
            String lastSsrUploadHash = (String)ssrUploadInfoMap.get(LAST_SSR_HASH);
            if (lastSsrUploadTimestamp == null || lastSsrUploadTime <= 0L || StringUtils.isBlank((CharSequence)lastSsrUploadHash)) {
                return false;
            }
            try {
                String hash = this.calculateHash((Date)lastSsrUploadTimestamp);
                if (!hash.toUpperCase(Locale.ENGLISH).equals(lastSsrUploadHash)) {
                    LOGGER.error("The Hash of RSP status is incorrect");
                    return false;
                }
            }
            catch (Exception e) {
                LOGGER.error("Calculate Hash Failed", (Throwable)e);
                return false;
            }
            if (this.isNotExpired(lastSsrUploadTimestamp)) {
                oidcSession.setExpireDate(Date.from(this.calculateExpirationTime(lastSsrUploadTimestamp).toInstant()));
                return true;
            }
        }
        catch (SQLException e) {
            LOGGER.error(e.getMessage(), (Throwable)e);
        }
        return false;
    }

    private ZonedDateTime calculateExpirationTime(Timestamp timestamp) {
        ZonedDateTime expireDateTime = timestamp.toInstant().atZone(ZoneId.systemDefault());
        return expireDateTime.plusHours(4L);
    }

    private boolean isNotExpired(Timestamp timestamp) {
        ZonedDateTime expirationTime = this.calculateExpirationTime(timestamp);
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        return now.isBefore(expirationTime);
    }

    public boolean checkODSupportUserActive(OIDCSession oidcSession) {
        try {
            Long timeToExpire = this.getRemainingTime(oidcSession);
            if (timeToExpire == null || timeToExpire <= 0L) {
                return false;
            }
            oidcSession.setExpireDate(new Date(System.currentTimeMillis() + timeToExpire));
            return true;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), (Throwable)e);
            return false;
        }
    }
}

