/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.thin.auth.AccessLog.UserAccessLog
 */
package com.sap.b1.thin.auth.AccessLog;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class UserAccessLog
implements Serializable {
    private static final long serialVersionUID = 3177503985626723560L;
    @JsonProperty(value="UserCode")
    String userCode;
    @JsonProperty(value="Action")
    String action;
    @JsonProperty(value="ActionBy")
    String actionBy;
    @JsonProperty(value="ClientIP")
    String clientIP;
    @JsonProperty(value="ClientName")
    String clientName;
    @JsonProperty(value="ActionDate")
    String actionDate;
    @JsonProperty(value="ActionTime")
    String actionTime;
    @JsonProperty(value="WinUsrName")
    String winUsrName;
    @JsonProperty(value="WinSessnID")
    Integer winSessnID;
    @JsonProperty(value="ProcName")
    String procName;
    @JsonProperty(value="ProcessID")
    Integer processID;
    @JsonProperty(value="SessionID")
    Integer sessionID;
    @JsonProperty(value="ReasonID")
    Integer reasonID;
    @JsonProperty(value="ReasonDesc")
    String reasonDesc;
    @JsonProperty(value="Source")
    String source;
    @JsonProperty(value="UserID")
    Integer userId;

    public String getUserCode() {
        return this.userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionBy() {
        return this.actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getClientIP() {
        return this.clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getActionDate() {
        return this.actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }

    public String getActionTime() {
        return this.actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getWinUsrName() {
        return this.winUsrName;
    }

    public void setWinUsrName(String winUsrName) {
        this.winUsrName = winUsrName;
    }

    public Integer getWinSessnID() {
        return this.winSessnID;
    }

    public void setWinSessnID(Integer winSessnID) {
        this.winSessnID = winSessnID;
    }

    public String getProcName() {
        return this.procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public Integer getProcessID() {
        return this.processID;
    }

    public void setProcessID(Integer processID) {
        this.processID = processID;
    }

    public Integer getSessionID() {
        return this.sessionID;
    }

    public void setSessionID(Integer sessionID) {
        this.sessionID = sessionID;
    }

    public Integer getReasonID() {
        return this.reasonID;
    }

    public void setReasonID(Integer reasonID) {
        this.reasonID = reasonID;
    }

    public String getReasonDesc() {
        return this.reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

