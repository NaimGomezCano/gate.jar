/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.extn.ExceptionResponse
 */
package com.sap.b1.thin.extn;

import java.util.Date;

public class ExceptionResponse {
    private Date timestamp = new Date();
    private int status;
    private String error;
    private String path;

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}

