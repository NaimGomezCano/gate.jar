/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.monitors.IMonitor
 *  com.sap.b1.notification.monitors.MonitorBase
 *  com.sap.b1.notification.monitors.NotificationParameter
 *  com.sap.b1.notification.server.NotificationItem
 */
package com.sap.b1.notification.monitors;

import com.sap.b1.notification.monitors.IMonitor;
import com.sap.b1.notification.monitors.NotificationParameter;
import com.sap.b1.notification.server.NotificationItem;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class MonitorBase
implements IMonitor {
    protected NotificationParameter notificationParameter;

    public MonitorBase(NotificationParameter notificationParameter) {
        this.notificationParameter = notificationParameter;
    }

    protected void calculateUnseen(Set<String> unseens, List<NotificationItem> oldItems, List<NotificationItem> newItems) {
        if (oldItems == null) {
            return;
        }
        HashSet<String> oldIdSet = new HashSet<String>();
        for (NotificationItem notificationItem : oldItems) {
            oldIdSet.add(notificationItem.Id);
        }
        HashSet<String> newIdSet = new HashSet<String>();
        for (NotificationItem item : newItems) {
            newIdSet.add(item.Id);
        }
        HashSet<String> hashSet = new HashSet<String>(newIdSet);
        hashSet.removeAll(oldIdSet);
        unseens.addAll(hashSet);
        hashSet.clear();
        hashSet.addAll(oldIdSet);
        hashSet.removeAll(newIdSet);
        unseens.removeAll(hashSet);
    }

    protected boolean equals(List<?> olddt, List<?> newdt) {
        if (olddt == null) {
            return false;
        }
        String oldString = olddt.toString().replaceAll("\"__guid\"\\s*:\\s*\"[0-9a-f\\-]*\",", "");
        String newString = newdt.toString().replaceAll("\"__guid\"\\s*:\\s*\"[0-9a-f\\-]*\",", "");
        return oldString.equals(newString);
    }
}

