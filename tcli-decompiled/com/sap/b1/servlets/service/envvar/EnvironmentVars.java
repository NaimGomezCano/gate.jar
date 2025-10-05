/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.servlets.service.envvar.EnvironmentVars
 */
package com.sap.b1.servlets.service.envvar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnvironmentVars {
    @JsonProperty(value="GOOGLE_MAP_API_KEY")
    public String googleMapAPIKey;
    @JsonProperty(value="GOOGLE_MAP_CLIENT_ID")
    public String googleMapClientId;
}

