/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.gate.ExceptionResponse
 */
package com.sap.b1.gate;

import java.util.Date;

public class ExceptionResponse {
    private final Date timestamp = new Date();
    private int status;
    private String error;
    private String path;

    public Date getTimestamp() {
        return this.timestamp;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

