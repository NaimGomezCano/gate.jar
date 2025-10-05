/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.db.DBTypeConfig
 *  com.sap.b1.infra.jpa.TenantContext
 *  com.sap.b1.infra.jpa.TenantInfo
 *  com.sap.b1.infra.permission.PermissionManager
 *  com.sap.b1.infra.permission.PermissionTypeEnum
 *  com.sap.b1.infra.share.web.B1AuthenticationToken
 *  com.sap.b1.infra.share.web.B1UserDetails
 *  com.sap.b1.notification.base.NotiEnv
 *  com.sap.b1.notification.cache.CacheObject
 *  com.sap.b1.notification.cache.Parameter
 *  com.sap.b1.notification.monitors.MonitorCenter
 *  com.sap.b1.notification.server.NotificationItem
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.context.ApplicationContext
 *  org.springframework.scheduling.annotation.Scheduled
 *  org.springframework.stereotype.Component
 *  org.springframework.web.socket.WebSocketSession
 */
package com.sap.b1.notification.cache;

import com.sap.b1.db.DBTypeConfig;
import com.sap.b1.infra.jpa.TenantContext;
import com.sap.b1.infra.jpa.TenantInfo;
import com.sap.b1.infra.permission.PermissionManager;
import com.sap.b1.infra.permission.PermissionTypeEnum;
import com.sap.b1.infra.share.web.B1AuthenticationToken;
import com.sap.b1.infra.share.web.B1UserDetails;
import com.sap.b1.notification.base.NotiEnv;
import com.sap.b1.notification.cache.Parameter;
import com.sap.b1.notification.monitors.MonitorCenter;
import com.sap.b1.notification.server.NotificationItem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class CacheObject {
    private static final Logger logger = LoggerFactory.getLogger(CacheObject.class);
    @Autowired
    DBTypeConfig dbType;
    @Autowired
    @Qualifier(value="PollingEnv")
    private NotiEnv env;
    @Autowired
    @Qualifier(value="RequestEnv")
    private NotiEnv requestEnv;
    @Autowired
    private PermissionManager permissionManager;
    @Autowired
    ApplicationContext appContext;
    private Map<String, MonitorCenter> notificationCaches = new ConcurrentHashMap();
    private Map<String, Object> userCacheLocks = new ConcurrentHashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CacheObject ensureCache(B1UserDetails userDetails) {
        String cacheKey = this.getCacheKey(userDetails.getSchema(), userDetails.getUsername());
        if (!this.notificationCaches.containsKey(cacheKey)) {
            Object object = this.userCacheLocks.computeIfAbsent(cacheKey, k -> new Object());
            synchronized (object) {
                if (!this.notificationCaches.containsKey(cacheKey)) {
                    try {
                        this.env.setSchema(userDetails.getSchema());
                        this.env.setUserCode(userDetails.getUsername());
                        MonitorCenter cache = (MonitorCenter)this.appContext.getBean(MonitorCenter.class, new Object[]{this.env.getSchema(), this.env.getUserCode(), this.env.getUserId(), this.requestEnv.getIsMultiScheduling()});
                        if (this.updateCache(cache)) {
                            this.notificationCaches.put(cacheKey, cache);
                        }
                    }
                    catch (Exception e) {
                        logger.error("Fail to create notification cache with schema ", (Throwable)e);
                    }
                }
            }
        }
        return this;
    }

    public void addWebSocket(WebSocketSession session) {
        B1UserDetails userDetails = (B1UserDetails)((B1AuthenticationToken)Objects.requireNonNull(session.getPrincipal())).getPrincipal();
        String cacheKey = this.getCacheKey(userDetails.getSchema(), userDetails.getUsername());
        if (this.notificationCaches.containsKey(cacheKey)) {
            MonitorCenter notificationCache = (MonitorCenter)this.notificationCaches.get(cacheKey);
            notificationCache.addSession(session);
            notificationCache.setLastDisconnectTime(0L);
        }
    }

    public boolean removeWebSocket(WebSocketSession session) {
        MonitorCenter notificationCache;
        boolean isRemoved = false;
        B1UserDetails userDetails = (B1UserDetails)((B1AuthenticationToken)session.getPrincipal()).getPrincipal();
        String cacheKey = this.getCacheKey(userDetails.getSchema(), userDetails.getUsername());
        if (this.notificationCaches.containsKey(cacheKey) && (isRemoved = (notificationCache = (MonitorCenter)this.notificationCaches.get(cacheKey)).removeSession(session)) && 0 == notificationCache.getSessionCount()) {
            notificationCache.setLastDisconnectTime(System.currentTimeMillis());
        }
        return isRemoved;
    }

    @Scheduled(fixedDelay=5000L)
    protected void updateCache() {
        for (MonitorCenter cache : this.notificationCaches.values()) {
            String cacheKey = this.getCacheKey(cache.getSchema(), cache.getUserCode());
            if (cache.getLastDisconnectTime() > 0L && System.currentTimeMillis() - cache.getLastDisconnectTime() > 60000L) {
                this.notificationCaches.remove(cacheKey);
                continue;
            }
            String sql = this.dbType.get().sqlSwitchSchema(cache.getSchema());
            TenantContext.setTenantInfo((TenantInfo)TenantInfo.build((String)sql, (String)this.dbType.get().sqlResetTenant()));
            boolean isUpdated = this.updateCache(cache);
            if (isUpdated) continue;
            this.notificationCaches.remove(cacheKey);
        }
    }

    protected boolean updateCache(MonitorCenter cache) {
        boolean result = true;
        try {
            cache.refresh();
        }
        catch (Exception e) {
            result = false;
            logger.error("Fail to update notification cache with schema ", (Throwable)e);
        }
        return result;
    }

    public int getBadgeNumber(NotiEnv env) {
        PermissionTypeEnum permission = PermissionTypeEnum.PRM_NONE;
        try {
            permission = this.permissionManager.getPermission("OCLG");
        }
        catch (Exception e) {
            logger.info("Get permission failed!");
        }
        if (PermissionTypeEnum.PRM_NONE.equals((Object)permission)) {
            logger.info("No permission to get data from OCLG table!");
            return 0;
        }
        int result = 0;
        String cacheKey = this.getCacheKey(env.getSchema(), env.getUserCode());
        if (this.notificationCaches.containsKey(cacheKey)) {
            result = ((MonitorCenter)this.notificationCaches.get(cacheKey)).getBadgeNumber();
        }
        return result;
    }

    public NotificationItem[] getNotifications(NotiEnv env, Parameter parameter) throws Exception {
        NotificationItem[] result = null;
        PermissionTypeEnum permission = PermissionTypeEnum.PRM_NONE;
        try {
            permission = this.permissionManager.getPermission("OCLG");
        }
        catch (Exception e) {
            logger.info("Get permission failed!");
        }
        if (PermissionTypeEnum.PRM_NONE.equals((Object)permission)) {
            PermissionManager.logApplyPermissionFailure((Logger)logger, (String)"OCLG", (PermissionTypeEnum)PermissionTypeEnum.PRM_READ_ONLY, (String)"No permission to get data from OCLG table!");
            return new NotificationItem[0];
        }
        PermissionManager.logApplyPermissionSuccess((Logger)logger, (String)"OCLG", (PermissionTypeEnum)PermissionTypeEnum.PRM_READ_ONLY);
        String cacheKey = this.getCacheKey(env.getSchema(), env.getUserCode());
        if (!this.notificationCaches.containsKey(cacheKey)) {
            logger.info("No notification items available!");
            return new NotificationItem[0];
        }
        MonitorCenter cache = (MonitorCenter)this.notificationCaches.get(cacheKey);
        List items = cache.getNotifications(parameter);
        int startIndex = 0;
        int endIndex = items.size();
        if (parameter.skip != null && parameter.top != null && (endIndex = (startIndex = parameter.skip.intValue()) + parameter.top) > items.size()) {
            endIndex = items.size();
        }
        this.sortNotification(items, parameter);
        result = new NotificationItem[endIndex - startIndex];
        for (int i = startIndex; i < endIndex; ++i) {
            result[i - startIndex] = (NotificationItem)items.get(i);
        }
        return result;
    }

    public void resetUnseenCount(NotiEnv env) throws Exception {
        if (PermissionTypeEnum.PRM_NONE.equals((Object)this.permissionManager.getPermission("OCLG"))) {
            logger.info("No permission to get data from OCLG table!");
            return;
        }
        String cacheKey = this.getCacheKey(env.getSchema(), env.getUserCode());
        if (this.notificationCaches.containsKey(cacheKey)) {
            ((MonitorCenter)this.notificationCaches.get(cacheKey)).resetUnseen();
        }
    }

    public List<NotificationItem> getNotificationsByGroup(NotiEnv env, String parentId) throws Exception {
        List<Object> items = new ArrayList<NotificationItem>();
        Parameter parameter = new Parameter(env.getUserId());
        parameter.parentId = parentId;
        String cacheKey = this.getCacheKey(env.getSchema(), env.getUserCode());
        if (this.notificationCaches.containsKey(cacheKey)) {
            MonitorCenter cache = (MonitorCenter)this.notificationCaches.get(cacheKey);
            items = cache.getNotifications(parameter);
        }
        return items;
    }

    private void sortNotification(List<NotificationItem> items, Parameter parameter) {
        if (parameter.isGroupHeader || parameter.parentId != null || "CreatedAt".equals(parameter.orderby)) {
            NotificationItem.orderBy = "Date";
            items.sort(null);
        } else if ("Priority".equals(parameter.orderby)) {
            NotificationItem.orderBy = "Priority";
            items.sort(null);
            items.sort(Comparator.comparing(NotificationItem::getPriorityOrderbyNumber));
        }
    }

    private String getCacheKey(String schema, String userCode) {
        return String.format("%s%d%s", schema, schema.length(), userCode);
    }
}

