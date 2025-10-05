/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.notification.server.NotificationResponse
 */
package com.sap.b1.notification.server;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationResponse {
    @JsonProperty(value="@odata.context")
    public String context = null;
    @JsonProperty
    public Object value;
}

