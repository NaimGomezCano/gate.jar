/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.google.gson.annotations.SerializedName
 *  com.sap.b1.thin.auth.AccessLog.SupportUserLoginRecord
 */
package com.sap.b1.thin.auth.AccessLog;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class SupportUserLoginRecord
implements Serializable {
    private static final long serialVersionUID = -4394089284213985974L;
    @SerializedName(value="ID")
    @JsonProperty(value="ID")
    String id;
    @SerializedName(value="RealName")
    @JsonProperty(value="RealName")
    String realName;
    @SerializedName(value="LogReason")
    @JsonProperty(value="LogReason")
    String logReason;
    @SerializedName(value="LogDetail")
    @JsonProperty(value="LogDetail")
    String logDetail;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLogReason() {
        return this.logReason;
    }

    public void setLogReason(String logReason) {
        this.logReason = logReason;
    }

    public String getLogDetail() {
        return this.logDetail;
    }

    public void setLogDetail(String logDetail) {
        this.logDetail = logDetail;
    }
}

