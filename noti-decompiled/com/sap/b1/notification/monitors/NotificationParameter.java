/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.monitors.NotificationParameter
 */
package com.sap.b1.notification.monitors;

public class NotificationParameter {
    private final Integer userId;
    private final String schema;
    private final String userCode;
    private final Boolean isMultiScheduling;
    private int oldShowDays;
    private int showDays;
    public static final int DEFAULT_SHOW_DAYS = 30;

    public NotificationParameter(String schema, String userCode, Integer userId, Boolean isMultiScheduling) {
        this.schema = schema;
        this.userCode = userCode;
        this.userId = userId;
        this.isMultiScheduling = isMultiScheduling;
    }

    public String getSchema() {
        return this.schema;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setShowDays(int showDays) {
        this.oldShowDays = this.showDays;
        this.showDays = showDays;
    }

    public int getShowDays() {
        return this.showDays;
    }

    public int getOldShowDays() {
        return this.oldShowDays;
    }

    public boolean isShowDayChanged() {
        return this.showDays != this.oldShowDays;
    }

    public Boolean getIsMultiScheduling() {
        return this.isMultiScheduling;
    }
}

