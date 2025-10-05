/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.base.FormatUtil
 *  com.sap.b1.notification.cache.Parameter
 *  com.sap.b1.notification.monitors.ActivityMonitor
 *  com.sap.b1.notification.monitors.ApprovalMonitor
 *  com.sap.b1.notification.monitors.IMonitor
 *  com.sap.b1.notification.monitors.MonitorCenter
 *  com.sap.b1.notification.monitors.NotificationParameter
 *  com.sap.b1.notification.monitors.ServiceCallMonitor
 *  com.sap.b1.notification.server.NotificationItem
 *  com.sap.b1.tcli.resources.MessageId
 *  com.sap.b1.tcli.resources.MessageUtil
 *  gen.bean.BmoOWLPD
 *  gen.dao.DaoOWLPD
 *  gen.str.Notifications
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.annotation.Scope
 *  org.springframework.stereotype.Component
 *  org.springframework.web.socket.TextMessage
 *  org.springframework.web.socket.WebSocketMessage
 *  org.springframework.web.socket.WebSocketSession
 */
package com.sap.b1.notification.monitors;

import com.sap.b1.notification.base.FormatUtil;
import com.sap.b1.notification.cache.Parameter;
import com.sap.b1.notification.monitors.ActivityMonitor;
import com.sap.b1.notification.monitors.ApprovalMonitor;
import com.sap.b1.notification.monitors.IMonitor;
import com.sap.b1.notification.monitors.NotificationParameter;
import com.sap.b1.notification.monitors.ServiceCallMonitor;
import com.sap.b1.notification.server.NotificationItem;
import com.sap.b1.tcli.resources.MessageId;
import com.sap.b1.tcli.resources.MessageUtil;
import gen.bean.BmoOWLPD;
import gen.dao.DaoOWLPD;
import gen.str.Notifications;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@Scope(value="prototype")
public class MonitorCenter {
    private static final Logger logger = LoggerFactory.getLogger(MonitorCenter.class);
    private final Set<String> unseenIds = new HashSet();
    private long lastDisconnectTime = 0L;
    private final Set<WebSocketSession> webSocketSessions = new HashSet();
    private NotificationParameter notificationParameter;
    private List<IMonitor> monitors = new ArrayList();
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    DaoOWLPD daoTCCFLP;
    @Autowired
    MessageUtil message;

    public MonitorCenter(String schema, String userCode, Integer userId, Boolean isMultiScheduling) {
        this.notificationParameter = new NotificationParameter(schema, userCode, userId, isMultiScheduling);
    }

    @Autowired
    public void init() {
        this.monitors.add((IMonitor)this.appContext.getBean(ActivityMonitor.class, new Object[]{this.notificationParameter}));
        this.monitors.add((IMonitor)this.appContext.getBean(ServiceCallMonitor.class, new Object[]{this.notificationParameter}));
        this.monitors.add((IMonitor)this.appContext.getBean(ApprovalMonitor.class, new Object[]{this.notificationParameter}));
    }

    public String getSchema() {
        return this.notificationParameter.getSchema();
    }

    public String getUserCode() {
        return this.notificationParameter.getUserCode();
    }

    public int getBadgeNumber() {
        return this.unseenIds.size();
    }

    public void resetUnseen() {
        this.unseenIds.clear();
    }

    public boolean removeSession(WebSocketSession session) {
        return this.webSocketSessions.remove(session);
    }

    public boolean addSession(WebSocketSession session) {
        return this.webSocketSessions.add(session);
    }

    public int getSessionCount() {
        return this.webSocketSessions.size();
    }

    public long getLastDisconnectTime() {
        return this.lastDisconnectTime;
    }

    public void setLastDisconnectTime(long lastDisconnectTime) {
        this.lastDisconnectTime = lastDisconnectTime;
    }

    public void notifyDataChange() {
        for (WebSocketSession session : this.webSocketSessions) {
            try {
                session.sendMessage((WebSocketMessage)new TextMessage((CharSequence)("Command:Notification\n\nShowDays:" + this.notificationParameter.getShowDays())));
            }
            catch (Exception e) {
                logger.error("Failed to notify change to schema: " + this.notificationParameter.getSchema() + ", user:" + this.notificationParameter.getUserCode(), (Throwable)e);
            }
        }
    }

    public void refresh() throws Exception {
        boolean hasChange = false;
        this.updateShowDays();
        for (IMonitor notification : this.monitors) {
            hasChange = notification.refresh(this.unseenIds) || hasChange;
        }
        if (hasChange) {
            this.notifyDataChange();
        }
    }

    private void updateShowDays() {
        List dtTCCFLP = this.daoTCCFLP.loadByUserId(this.notificationParameter.getUserId());
        Integer newShowDays = null;
        if (dtTCCFLP.size() > 0) {
            newShowDays = ((BmoOWLPD)dtTCCFLP.get(0)).getNtfShowDay();
        }
        if (newShowDays == null) {
            newShowDays = 30;
        }
        this.notificationParameter.setShowDays(newShowDays.intValue());
    }

    public List<NotificationItem> getNotifications(Parameter parameter) throws Exception {
        List<Object> notifications = new ArrayList<NotificationItem>();
        for (IMonitor monitor : this.monitors) {
            notifications.addAll(monitor.getNotifications(parameter));
        }
        if (parameter.isGroupHeader) {
            notifications = this.mergeGroup(notifications, parameter);
        }
        return notifications;
    }

    private List<NotificationItem> mergeGroup(List<NotificationItem> notifications, Parameter parameter) {
        NotificationItem groupItem = null;
        notifications.sort(Comparator.comparing(NotificationItem::getSortDate).reversed());
        ArrayList<NotificationItem> items = new ArrayList<NotificationItem>();
        int size = 0;
        for (NotificationItem notification : notifications) {
            if (groupItem == null || !groupItem.SortDate.equals(notification.SortDate)) {
                groupItem = notification;
                size = this.getReceivedSize(groupItem);
                if (size != 0) {
                    items.add(groupItem);
                }
            } else if (groupItem.SortDate.equals(notification.SortDate)) {
                size = this.getReceivedSize(groupItem) + this.getReceivedSize(notification);
                groupItem.aNotifications = (NotificationItem[])ArrayUtils.addAll((Object[])groupItem.aNotifications, (Object[])notification.aNotifications);
                if ("3".equals(notification.getPriorityNumber())) {
                    groupItem.setPriority(notification.getPriorityNumber());
                }
            }
            groupItem.GroupHeaderText = this.message.getMessage((MessageId)Notifications.notificationsReceivedToday, new Object[]{size}) + "\u3000\u3000" + FormatUtil.formatDate((Date)groupItem.SortDate, (Locale)parameter.locale);
        }
        return items;
    }

    private Integer getReceivedSize(NotificationItem groupItem) {
        int notiSize = 0;
        for (NotificationItem notificationChild : groupItem.aNotifications) {
            if (notificationChild.IsForReminder.booleanValue()) continue;
            ++notiSize;
        }
        return notiSize;
    }
}

