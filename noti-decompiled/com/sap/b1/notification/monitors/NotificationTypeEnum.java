/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.base.IBaseEnum
 *  com.sap.b1.notification.monitors.NotificationTypeEnum
 */
package com.sap.b1.notification.monitors;

import com.sap.b1.notification.base.IBaseEnum;

public enum NotificationTypeEnum implements IBaseEnum<Integer>
{
    ACTIVITY(Integer.valueOf(0)),
    APPROVAL(Integer.valueOf(1)),
    SERVICECALL(Integer.valueOf(2));

    private final Integer value;

    private NotificationTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}

