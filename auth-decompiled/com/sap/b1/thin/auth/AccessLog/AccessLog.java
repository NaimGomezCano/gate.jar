/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.thin.auth.AccessLog.AccessLog
 *  com.sap.b1.thin.auth.AccessLog.SupportUserLoginRecord
 *  com.sap.b1.thin.auth.AccessLog.UserAccessLog
 */
package com.sap.b1.thin.auth.AccessLog;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.b1.thin.auth.AccessLog.SupportUserLoginRecord;
import com.sap.b1.thin.auth.AccessLog.UserAccessLog;
import java.io.Serializable;

public class AccessLog
implements Serializable {
    private static final long serialVersionUID = -3659571783795750205L;
    @JsonProperty(value="UserAccessLog")
    UserAccessLog userAccessLog;
    @JsonProperty(value="SupportUserLoginRecord")
    SupportUserLoginRecord supportUserLoginRecord;

    public UserAccessLog getUserAccessLog() {
        return this.userAccessLog;
    }

    public void setUserAccessLog(UserAccessLog userAccessLog) {
        this.userAccessLog = userAccessLog;
    }

    public SupportUserLoginRecord getSupportUserLoginRecord() {
        return this.supportUserLoginRecord;
    }

    public void setSupportUserLoginRecord(SupportUserLoginRecord supportUserLoginRecord) {
        this.supportUserLoginRecord = supportUserLoginRecord;
    }
}

