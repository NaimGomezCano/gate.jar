/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.base.NotiEnv
 *  com.sap.b1.notification.base.NotificationManager
 *  com.sap.b1.notification.cache.CacheObject
 *  com.sap.b1.notification.cache.Parameter
 *  com.sap.b1.notification.server.NotificationResponse
 *  com.sap.b1.notification.server.NotificationServlet
 *  com.sap.b1.notification.server.RequestBodyObject
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.sap.b1.notification.server;

import com.sap.b1.notification.base.NotiEnv;
import com.sap.b1.notification.base.NotificationManager;
import com.sap.b1.notification.cache.CacheObject;
import com.sap.b1.notification.cache.Parameter;
import com.sap.b1.notification.server.NotificationResponse;
import com.sap.b1.notification.server.RequestBodyObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationServlet {
    @Autowired
    @Qualifier(value="RequestEnv")
    private NotiEnv env;
    @Autowired
    private CacheObject cacheObject;
    @Autowired
    private NotificationManager notificationManager;

    @RequestMapping(path={"/notification.svc/Notifications"})
    @ResponseBody
    public NotificationResponse notifications(@RequestParam(value="$orderby", required=false) String orderBy, @RequestParam(value="$filter") String filter, @RequestParam(value="$skip", required=false) String skip, @RequestParam(value="$top", required=false) String top) throws Exception {
        Parameter parameter = new Parameter(this.env.getUserId());
        if (orderBy != null) {
            String[] strings = orderBy.split(" ");
            parameter.orderby = strings[0];
            parameter.desending = "desc".equals(strings[1]);
        }
        for (String values : filter.split("and")) {
            String[] value = values.split("eq");
            if (value.length != 2) continue;
            if (value[0].trim().equals("IsGroupHeader")) {
                parameter.isGroupHeader = value[1].trim().equals("true");
                continue;
            }
            if (!value[0].trim().equals("ParentId")) continue;
            parameter.parentId = value[1].trim();
        }
        if (skip != null) {
            parameter.skip = Integer.parseInt(skip);
        }
        if (top != null) {
            parameter.top = Integer.parseInt(top);
        }
        NotificationResponse response = new NotificationResponse();
        response.context = "$metadata#Notifications";
        response.value = this.cacheObject.getNotifications(this.env, parameter);
        return response;
    }

    @RequestMapping(path={"/notification.svc/Notifications/$count"})
    @ResponseBody
    public NotificationResponse notificationCount() {
        NotificationResponse response = new NotificationResponse();
        response.context = "$metadata#Edm.Int32";
        response.value = 0;
        return response;
    }

    @RequestMapping(path={"/notification.svc/GetBadgeNumber()"})
    @ResponseBody
    public NotificationResponse getBadgeNumber() {
        NotificationResponse response = new NotificationResponse();
        response.context = "$metadata#Edm.Int32";
        response.value = this.cacheObject.getBadgeNumber(this.env);
        return response;
    }

    @RequestMapping(path={"/notification.svc/ResetBadgeNumber"})
    public void resetBadgeNumber() throws Exception {
    }

    @RequestMapping(path={"/notification.svc/MarkRead"})
    public void markRead(@RequestBody RequestBodyObject body) throws Exception {
        this.notificationManager.markRead(this.env, body.NotificationId);
    }

    @RequestMapping(path={"/notification.svc/Dismiss"})
    public void dismiss(@RequestBody RequestBodyObject body) throws Exception {
        this.notificationManager.dismissNotification(body.NotificationId, this.env.getUserId());
    }

    @RequestMapping(path={"/notification.svc/DismissAll"})
    public void dismissAll(@RequestBody RequestBodyObject body) throws Exception {
        List notifications = this.cacheObject.getNotificationsByGroup(this.env, body.ParentId);
        this.notificationManager.dismissNotifications(this.env, notifications);
    }

    @RequestMapping(path={"/notification.svc/NotificationTypePersonalizationSet"})
    @ResponseBody
    public NotificationResponse notificationTypePersonalizationSet() {
        NotificationResponse response = new NotificationResponse();
        response.context = "$metadata#NotificationTypePersonalizationSet";
        response.value = new Object[0];
        return response;
    }

    @RequestMapping(value={"/notification.svc/Channels(ChannelId='SAP_SMP')"})
    public String getMobileSupportSettings() {
        return "{ \"IsActive\": true }";
    }

    @RequestMapping(value={"/notification.svc/Channels('SAP_WEBSOCKET')"})
    public String validateWebsocketChannel() {
        return "{ \"IsActive\": true }";
    }
}

