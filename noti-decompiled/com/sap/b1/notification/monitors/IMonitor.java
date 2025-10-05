/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.cache.Parameter
 *  com.sap.b1.notification.monitors.IMonitor
 *  com.sap.b1.notification.server.NotificationItem
 */
package com.sap.b1.notification.monitors;

import com.sap.b1.notification.cache.Parameter;
import com.sap.b1.notification.server.NotificationItem;
import java.util.List;
import java.util.Set;

public interface IMonitor {
    public boolean refresh(Set<String> var1) throws Exception;

    public List<NotificationItem> getNotifications(Parameter var1) throws Exception;
}

