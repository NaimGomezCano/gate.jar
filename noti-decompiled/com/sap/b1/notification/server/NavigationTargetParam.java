/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.notification.server.NavigationTargetParam
 */
package com.sap.b1.notification.server;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NavigationTargetParam {
    @JsonProperty
    public String Key;
    @JsonProperty
    public String Value;

    public NavigationTargetParam() {
    }

    public NavigationTargetParam(String key, String value) {
        this.Key = key;
        this.Value = value;
    }
}

