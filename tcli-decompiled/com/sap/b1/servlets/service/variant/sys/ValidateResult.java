/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.servlets.service.variant.sys.ValidateResult
 */
package com.sap.b1.servlets.service.variant.sys;

public class ValidateResult {
    private boolean valid;
    private String errMsg;

    public ValidateResult(boolean valid) {
        this.valid = valid;
    }

    public ValidateResult(boolean valid, String errMsg) {
        this.valid = valid;
        this.errMsg = errMsg;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}

