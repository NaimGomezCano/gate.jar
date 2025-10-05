/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.notification.server.NotificationType
 */
package com.sap.b1.notification.server;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationType {
    @JsonProperty
    public String NotificationTypeDesc;
    @JsonProperty
    public boolean DoNotDeliverMob;
    @JsonProperty
    public String PriorityDefault;
    @JsonProperty
    public boolean DoNotDeliver;
}

