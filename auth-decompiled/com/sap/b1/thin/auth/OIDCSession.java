/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.boot.sld.feign.entity.CompanyDatabase
 *  com.sap.b1.thin.auth.AccessLog.AccessLog
 *  com.sap.b1.thin.auth.AccessLog.LoginType
 *  com.sap.b1.thin.auth.OIDCSession
 */
package com.sap.b1.thin.auth;

import com.sap.b1.boot.sld.feign.entity.CompanyDatabase;
import com.sap.b1.thin.auth.AccessLog.AccessLog;
import com.sap.b1.thin.auth.AccessLog.LoginType;
import java.io.Serializable;
import java.util.Date;

public class OIDCSession
implements Serializable {
    private String dbServerName;
    private String companyDatabaseName;
    private String sboUserCode;
    private String language;
    private String installationNumber;
    private CompanyDatabase companyDatabase;
    private Date expireDate;
    private boolean isSupportUserLogin;
    private AccessLog loginAccessLog;
    private LoginType loginType;
    private String accessToken;
    private String svclCookies;

    public String getDbServerName() {
        return this.dbServerName;
    }

    public void setDbServerName(String dbServerName) {
        this.dbServerName = dbServerName;
    }

    public String getCompanyDatabaseName() {
        return this.companyDatabaseName;
    }

    public void setCompanyDatabaseName(String companyDatabaseName) {
        this.companyDatabaseName = companyDatabaseName;
    }

    public String getSboUserCode() {
        return this.sboUserCode;
    }

    public void setSboUserCode(String sboUserCode) {
        this.sboUserCode = sboUserCode;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getInstallationNumber() {
        return this.installationNumber;
    }

    public void setInstallationNumber(String installationNumber) {
        this.installationNumber = installationNumber;
    }

    public CompanyDatabase getCompanyDatabase() {
        return this.companyDatabase;
    }

    public void setCompanyDatabase(CompanyDatabase companyDatabase) {
        this.companyDatabase = companyDatabase;
    }

    public Date getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public boolean getSupportUserLogin() {
        return this.isSupportUserLogin;
    }

    public void setSupportUserLogin(boolean supportUserLogin) {
        this.isSupportUserLogin = supportUserLogin;
    }

    public AccessLog getLoginAccessLog() {
        return this.loginAccessLog;
    }

    public void setLoginAccessLog(AccessLog loginAccessLog) {
        this.loginAccessLog = loginAccessLog;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginType getLoginType() {
        return this.loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getSvclCookies() {
        return this.svclCookies;
    }

    public void setSvclCookies(String svclCookies) {
        this.svclCookies = svclCookies;
    }
}

