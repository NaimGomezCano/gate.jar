/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.bo.base.BoBase
 *  com.sap.b1.bo.base.BoEnv
 *  com.sap.b1.bo.base.BoFinder
 *  com.sap.b1.notification.base.NotiEnv
 *  com.sap.b1.notification.base.NotificationManager
 *  com.sap.b1.notification.server.NotificationItem
 *  com.sap.b1.protocol.request.RequestData
 *  gen.bean.BmoOWNOT
 *  jakarta.persistence.EntityManager
 *  jakarta.persistence.PersistenceContext
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.notification.base;

import com.sap.b1.bo.base.BoBase;
import com.sap.b1.bo.base.BoEnv;
import com.sap.b1.bo.base.BoFinder;
import com.sap.b1.notification.base.NotiEnv;
import com.sap.b1.notification.server.NotificationItem;
import com.sap.b1.protocol.request.RequestData;
import gen.bean.BmoOWNOT;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationManager {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private BoFinder boFinder;
    @Autowired
    protected BoEnv env;

    public void markRead(NotiEnv env, String notificationId) throws Exception {
        boolean isNew;
        BmoOWNOT bmoOWNOT = (BmoOWNOT)this.entityManager.find(BmoOWNOT.class, (Object)notificationId);
        Integer userId = env.getUserId();
        boolean bl = isNew = bmoOWNOT == null;
        if (bmoOWNOT == null) {
            bmoOWNOT = new BmoOWNOT();
            bmoOWNOT.setGuid(notificationId);
            bmoOWNOT.setNotiType(this.parseNotificationType(notificationId));
            bmoOWNOT.setUserId(userId);
            bmoOWNOT.setRead("Y");
        } else {
            bmoOWNOT.setRead("Y");
        }
        this.updateNotification(isNew, bmoOWNOT);
    }

    public void dismissNotification(String notificationId, Integer userId) throws Exception {
        boolean isNew;
        BmoOWNOT bmoOWNOT = (BmoOWNOT)this.entityManager.find(BmoOWNOT.class, (Object)notificationId);
        boolean bl = isNew = bmoOWNOT == null;
        if (bmoOWNOT == null) {
            bmoOWNOT = new BmoOWNOT();
            bmoOWNOT.setGuid(notificationId);
            bmoOWNOT.setNotiType(this.parseNotificationType(notificationId));
            bmoOWNOT.setUserId(userId);
            bmoOWNOT.setDismissed("Y");
        } else {
            bmoOWNOT.setDismissed("Y");
        }
        this.updateNotification(isNew, bmoOWNOT);
    }

    public void dismissNotifications(NotiEnv env, List<NotificationItem> items) throws Exception {
        for (NotificationItem notificationItem : items) {
            this.dismissNotification(notificationItem.Id, env.getUserId());
        }
    }

    private void updateNotification(boolean isNew, BmoOWNOT bmoOWNOT) throws Exception {
        BoBase boOWNOT = this.boFinder.newBusinessObject("OWNOT");
        RequestData evt = new RequestData();
        bmoOWNOT.set__guid("OWNOT," + bmoOWNOT.getGuid());
        ArrayList<BmoOWNOT> dtOWNOT = new ArrayList<BmoOWNOT>(1);
        dtOWNOT.add(bmoOWNOT);
        evt.setDataList(dtOWNOT);
        evt.setObject("OWNOT");
        evt.init(this.env);
        if (isNew) {
            boOWNOT.onCreate(evt);
        } else {
            boOWNOT.onUpdate(evt);
        }
    }

    private Integer parseNotificationType(String notificationId) {
        Integer type = -1;
        String[] splits = notificationId.split("-");
        if (splits.length > 2) {
            type = Integer.valueOf(splits[1]);
        }
        return type;
    }
}

