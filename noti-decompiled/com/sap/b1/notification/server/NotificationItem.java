/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.sap.b1.debug.DebugJsonBuilder
 *  com.sap.b1.notification.server.Action
 *  com.sap.b1.notification.server.NavigationTargetParam
 *  com.sap.b1.notification.server.NotificationItem
 */
package com.sap.b1.notification.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.b1.debug.DebugJsonBuilder;
import com.sap.b1.notification.server.Action;
import com.sap.b1.notification.server.NavigationTargetParam;
import java.util.ArrayList;
import java.util.Date;

public class NotificationItem
implements Comparable<NotificationItem> {
    public static String orderBy = "Priority";
    @JsonProperty
    public String Id;
    @JsonProperty
    public String OriginId;
    @JsonProperty
    public Long CreatedAt;
    public Date StartDate;
    public Date SortDate;
    @JsonProperty
    public long StartTime;
    @JsonProperty
    public long SortTime;
    @JsonProperty
    public long ServerTime;
    @JsonProperty
    public String EndTime;
    @JsonProperty
    public boolean IsActionable;
    @JsonProperty
    public boolean IsGroupable;
    @JsonProperty
    public boolean IsGroupHeader;
    @JsonProperty
    public String NavigationTargetAction;
    @JsonProperty
    public String NavigationTargetObject;
    @JsonProperty
    public String NavigationIntent;
    @JsonProperty
    public String NotificationTypeId;
    @JsonProperty
    public String NotificationTypeKey;
    @JsonProperty
    public NavigationTargetParam[] NavigationTargetParams;
    @JsonProperty
    public String ParentId;
    @JsonProperty
    public String Priority;
    private String priorityNumber;
    @JsonProperty
    public String SensitiveText;
    @JsonProperty
    public String ReminderText;
    @JsonProperty
    public Boolean IsForReminder = false;
    @JsonProperty
    public String Text;
    @JsonProperty
    public String GroupHeaderText;
    @JsonProperty
    public int NotificationCount;
    @JsonProperty
    public NotificationItem[] aNotifications;
    @JsonProperty
    public String SubTitle;
    @JsonProperty
    public boolean IsRead;
    @JsonProperty
    public String NotificationTypeDesc;
    @JsonProperty
    public ArrayList<Action> Actions;
    @JsonProperty
    public boolean Reminder;
    @JsonProperty
    public long ReminderTime;
    @JsonProperty
    public Integer priorityOrderbyNumber;

    public Integer getPriorityOrderbyNumber() {
        return this.priorityOrderbyNumber;
    }

    public void setPriorityOrderbyNumber(Integer priorityOrderbyNumber) {
        this.priorityOrderbyNumber = priorityOrderbyNumber;
    }

    public Date getStartDate() {
        return this.StartDate;
    }

    public Date getSortDate() {
        return this.SortDate;
    }

    private String getPriority(String priorityNumber) {
        switch (priorityNumber) {
            case "1": {
                return "MEDIUM";
            }
            case "2": {
                return "HIGH";
            }
            case "3": {
                return "NONE";
            }
        }
        return "LOW";
    }

    public void setPriority(String priorityNumber) {
        this.priorityNumber = priorityNumber;
        this.Priority = this.getPriority(priorityNumber);
    }

    public String getPriorityNumber() {
        return this.priorityNumber;
    }

    @Override
    public int compareTo(NotificationItem item) {
        String sortType;
        int result = 0;
        switch (sortType = this.GroupHeaderText == null && Integer.valueOf(this.priorityNumber) == 3 ? "Priority" : orderBy) {
            case "Priority": {
                result = -this.priorityNumber.compareTo(item.priorityNumber);
                if (result == 0) {
                    result = -this.SortDate.compareTo(item.SortDate);
                }
                if (result == 0) {
                    result = -((int)(this.SortTime - item.SortTime));
                }
                if (result == 0) {
                    result = this.Id.compareTo(item.Id);
                }
                return result;
            }
        }
        result = -this.SortDate.compareTo(item.SortDate);
        if (result == 0) {
            result = (int)(item.SortTime - this.SortTime);
        }
        if (result == 0) {
            result = -this.priorityNumber.compareTo(item.priorityNumber);
        }
        if (result == 0) {
            result = this.Id.compareTo(item.Id);
        }
        return result;
    }

    public String toString() {
        return DebugJsonBuilder.toString((Object)this);
    }
}

