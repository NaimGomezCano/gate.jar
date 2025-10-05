/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.bo.base.JpaUtil
 *  com.sap.b1.notification.cache.OCLGController
 *  com.sap.b1.notification.cache.Parameter
 *  com.sap.b1.notification.monitors.ActivityMonitor
 *  com.sap.b1.notification.monitors.MonitorBase
 *  com.sap.b1.notification.monitors.NotificationParameter
 *  com.sap.b1.notification.monitors.NotificationTypeEnum
 *  com.sap.b1.notification.server.NotificationItem
 *  com.sap.b1.util.StringUtil
 *  gen.bean.BmoOCLG
 *  gen.bean.BmoOWNOT
 *  gen.dao.DaoOCRD
 *  gen.dao.DaoOWNOT
 *  jakarta.persistence.EntityManager
 *  jakarta.persistence.PersistenceContext
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.annotation.Scope
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.notification.monitors;

import com.sap.b1.bo.base.JpaUtil;
import com.sap.b1.notification.cache.OCLGController;
import com.sap.b1.notification.cache.Parameter;
import com.sap.b1.notification.monitors.MonitorBase;
import com.sap.b1.notification.monitors.NotificationParameter;
import com.sap.b1.notification.monitors.NotificationTypeEnum;
import com.sap.b1.notification.server.NotificationItem;
import com.sap.b1.util.StringUtil;
import gen.bean.BmoOCLG;
import gen.bean.BmoOWNOT;
import gen.dao.DaoOCRD;
import gen.dao.DaoOWNOT;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class ActivityMonitor
extends MonitorBase {
    @Autowired
    private OCLGController oclgController;
    private static final Logger logger = LoggerFactory.getLogger(ActivityMonitor.class);
    @PersistenceContext
    private EntityManager em;
    private List<BmoOCLG> dtOCLG;
    private List<BmoOWNOT> dtNOTI;
    @Autowired
    private DaoOWNOT daoOWNOT;
    @Autowired
    private DaoOCRD daoOCRD;

    public ActivityMonitor(NotificationParameter notificationParameter) {
        super(notificationParameter);
    }

    public boolean refresh(Set<String> unseens) {
        boolean changed;
        Integer userId = this.notificationParameter.getUserId();
        List newdtOCLG = JpaUtil.load((EntityManager)this.em, BmoOCLG.class, (String)this.getRelatedActivitiesSql(), (Object[])new Object[]{userId, userId, userId});
        List newdtNOTI = this.daoOWNOT.loadByUserAndType(userId, NotificationTypeEnum.ACTIVITY.getValue());
        this.updateCardName(newdtOCLG);
        boolean bl = changed = !this.equals(this.dtOCLG, newdtOCLG) || !this.equals(this.dtNOTI, newdtNOTI) || this.notificationParameter.isShowDayChanged();
        if (changed) {
            try {
                List oldItems = new ArrayList();
                Parameter parameter = new Parameter(userId);
                if (this.dtOCLG != null && this.dtNOTI != null) {
                    parameter.showDays = this.notificationParameter.getOldShowDays();
                    oldItems = this.oclgController.getNotifications(this.dtOCLG, this.dtNOTI, parameter);
                }
                parameter.showDays = this.notificationParameter.getShowDays();
                List newItems = this.oclgController.getNotifications(newdtOCLG, newdtNOTI, parameter);
                this.calculateUnseen(unseens, oldItems, newItems);
                this.dtOCLG = newdtOCLG;
                this.dtNOTI = newdtNOTI;
            }
            catch (Exception e) {
                logger.error(e.getMessage());
                changed = false;
            }
        }
        return changed;
    }

    private void updateCardName(List<BmoOCLG> newdtOCLG) {
        for (BmoOCLG bmoOCLG : newdtOCLG) {
            String cardCode = bmoOCLG.getCardCode();
            if (StringUtil.isEmpty((String)cardCode)) continue;
            Object[] rowNameType = (Object[])this.daoOCRD.getNameAndType(cardCode).get(0);
            bmoOCLG.setVCardName((String)rowNameType[0]);
            bmoOCLG.setVCardType((String)rowNameType[1]);
        }
    }

    public List<NotificationItem> getNotifications(Parameter parameter) {
        parameter.showDays = this.notificationParameter.getShowDays();
        List<Object> notis = new ArrayList<NotificationItem>();
        try {
            notis = this.oclgController.getNotifications(this.dtOCLG, this.dtNOTI, parameter);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return notis;
    }

    private String getRelatedActivitiesSql() {
        return "SELECT * FROM (\nSELECT * FROM \"OCLG\" WHERE \"OCLG\".\"AttendUser\" = ?\nUNION ALL\nSELECT \"OCLG\".* FROM \"OCLG\" INNER JOIN \"CLG2\" ON \"CLG2\".\"ClgCode\" = \"OCLG\".\"ClgCode\" AND \"CLG2\".\"ObjType\"=12 AND \"CLG2\".\"ObjCode\" = ?\nUNION ALL\nSELECT \"OCLG\".* FROM \"OCLG\" INNER JOIN \"RCI1\" ON \"OCLG\".\"AttendReci\" = \"RCI1\".\"Code\" AND \"RCI1\".\"ObjType\"=12 AND \"RCI1\".\"ObjCode\" = ?\n) AS \"TEMP\" ORDER BY \"ClgCode\"";
    }
}

