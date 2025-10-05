/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.auth.LoginReason
 *  jakarta.validation.constraints.NotBlank
 *  jakarta.validation.constraints.Size
 */
package com.sap.b1.thin.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

public class LoginReason
implements Serializable {
    private static final long serialVersionUID = -7948825772916694642L;
    @NotBlank
    @Size(max=100)
    private @NotBlank @Size(max=100) String realName;
    @NotBlank
    private String loginReason;
    @Size(max=256000)
    private @Size(max=256000) String comments;

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLoginReason() {
        return this.loginReason;
    }

    public void setLoginReason(String loginReason) {
        this.loginReason = loginReason;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

