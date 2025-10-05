/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.auth.AccessLog.LoginType
 */
package com.sap.b1.thin.auth.AccessLog;

public enum LoginType {
    LOGIN_SUCCEED("I"),
    LOGON_FAILED("F"),
    LOGOFF_SUCCEED("O");

    private final String value;

    private LoginType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

