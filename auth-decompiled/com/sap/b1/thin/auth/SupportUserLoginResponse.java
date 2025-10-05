/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.thin.auth.SupportUserLoginResponse
 */
package com.sap.b1.thin.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupportUserLoginResponse {
    @JsonProperty(value="error")
    private String errorCode;

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}

