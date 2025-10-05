/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.servlets.service.support.LoginReason
 */
package com.sap.b1.servlets.service.support;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginReason {
    @JsonProperty
    private String id;
    @JsonProperty
    private String desc;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

