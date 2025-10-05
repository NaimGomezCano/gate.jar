/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.approval.ApprovalRequestInfo
 *  com.sap.b1.notification.base.FormatUtil
 *  com.sap.b1.notification.base.NotiEnv
 *  com.sap.b1.notification.cache.BaseController
 *  com.sap.b1.notification.cache.OWDDController
 *  com.sap.b1.notification.cache.Parameter
 *  com.sap.b1.notification.monitors.NotificationTypeEnum
 *  com.sap.b1.notification.server.NavigationTargetParam
 *  com.sap.b1.notification.server.NotificationItem
 *  com.sap.b1.tcli.resources.MessageId
 *  gen.bean.BmoODLN
 *  gen.bean.BmoODRF
 *  gen.bean.BmoOIGE
 *  gen.bean.BmoOIGN
 *  gen.bean.BmoOINC
 *  gen.bean.BmoOINV
 *  gen.bean.BmoOOAT
 *  gen.bean.BmoOPCH
 *  gen.bean.BmoOPDN
 *  gen.bean.BmoOPOR
 *  gen.bean.BmoOPQT
 *  gen.bean.BmoOPRQ
 *  gen.bean.BmoOPRR
 *  gen.bean.BmoOQUT
 *  gen.bean.BmoORDN
 *  gen.bean.BmoORDR
 *  gen.bean.BmoORIN
 *  gen.bean.BmoORPC
 *  gen.bean.BmoORPD
 *  gen.bean.BmoORRR
 *  gen.bean.BmoOVPM
 *  gen.bean.BmoOWNOT
 *  gen.str.Notifications
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.context.annotation.Scope
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.notification.cache;

import com.sap.b1.notification.approval.ApprovalRequestInfo;
import com.sap.b1.notification.base.FormatUtil;
import com.sap.b1.notification.base.NotiEnv;
import com.sap.b1.notification.cache.BaseController;
import com.sap.b1.notification.cache.Parameter;
import com.sap.b1.notification.monitors.NotificationTypeEnum;
import com.sap.b1.notification.server.NavigationTargetParam;
import com.sap.b1.notification.server.NotificationItem;
import com.sap.b1.tcli.resources.MessageId;
import gen.bean.BmoODLN;
import gen.bean.BmoODRF;
import gen.bean.BmoOIGE;
import gen.bean.BmoOIGN;
import gen.bean.BmoOINC;
import gen.bean.BmoOINV;
import gen.bean.BmoOOAT;
import gen.bean.BmoOPCH;
import gen.bean.BmoOPDN;
import gen.bean.BmoOPOR;
import gen.bean.BmoOPQT;
import gen.bean.BmoOPRQ;
import gen.bean.BmoOPRR;
import gen.bean.BmoOQUT;
import gen.bean.BmoORDN;
import gen.bean.BmoORDR;
import gen.bean.BmoORIN;
import gen.bean.BmoORPC;
import gen.bean.BmoORPD;
import gen.bean.BmoORRR;
import gen.bean.BmoOVPM;
import gen.bean.BmoOWNOT;
import gen.str.Notifications;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="singleton")
public class OWDDController
extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OWDDController.class);
    public static final String APPROVAL_NOTIFICATION_PRIORITY = "3";
    @Autowired
    @Qualifier(value="PollingEnv")
    private NotiEnv env;
    private Boolean isNewPath;
    private String boName;

    public List<NotificationItem> getNotifications(List<ApprovalRequestInfo> approvalRequests, List<BmoOWNOT> dtOWNOT, Parameter parameter) throws Exception {
        int size = approvalRequests.size();
        ArrayList<NotificationItem> items = new ArrayList<NotificationItem>(size * (parameter.showDays + 1));
        Map map = this.mapNotification(dtOWNOT);
        ArrayList<NotificationItem> groupChildren = new ArrayList<NotificationItem>();
        Calendar calendar = this.getTomorrowCalendar();
        for (int i = 0; i < parameter.showDays; ++i) {
            String parentId;
            if (parameter.parentId == null) {
                calendar.set(5, calendar.get(5) - 1);
                parentId = this.toParentId(calendar);
            } else {
                parentId = parameter.parentId;
                calendar = this.toCalendar(parentId);
            }
            if (parameter.isGroupHeader) {
                groupChildren = new ArrayList(size);
            }
            for (ApprovalRequestInfo approvalRequest : approvalRequests) {
                String id = this.toId(parentId, NotificationTypeEnum.APPROVAL.getValue().toString(), String.valueOf(approvalRequest.getAlertCode()));
                BmoOWNOT bmoOWNOT = (BmoOWNOT)map.get(id);
                if (bmoOWNOT != null && "Y".equals(bmoOWNOT.getDismissed())) continue;
                LocalDate operationDate = FormatUtil.toLocal((Date)approvalRequest.getRecDate());
                LocalDate theDay = FormatUtil.toLocal((Calendar)calendar);
                if (operationDate == null || !operationDate.equals(theDay)) continue;
                NotificationItem item = this.getNotificationItem(approvalRequest, parameter, parentId, id, calendar, bmoOWNOT, this.getSubTitleBySubject(approvalRequest, parameter), approvalRequest.getRecTime());
                if (parameter.isGroupHeader) {
                    groupChildren.add(item);
                    continue;
                }
                items.add(item);
            }
            if (parameter.parentId != null) break;
            if (!parameter.isGroupHeader || groupChildren.size() == 0) continue;
            NotificationItem groupItem = new NotificationItem();
            groupItem.Id = parentId;
            groupItem.IsGroupHeader = true;
            groupItem.StartDate = calendar.getTime();
            groupItem.SortDate = calendar.getTime();
            groupItem.IsRead = false;
            groupItem.setPriority(APPROVAL_NOTIFICATION_PRIORITY);
            groupItem.aNotifications = new NotificationItem[groupChildren.size()];
            groupChildren.toArray(groupItem.aNotifications);
            items.add(groupItem);
        }
        return items;
    }

    private NotificationItem getNotificationItem(ApprovalRequestInfo approvalRequest, Parameter parameter, String parentId, String id, Calendar calendar, BmoOWNOT bmoOWNOT, String subTitle, Integer time) {
        String draftBoName = "ODRF";
        this.isNewPath = false;
        NotificationItem item = new NotificationItem();
        item.ParentId = parentId;
        item.Id = id;
        item.Text = this.getTitle(approvalRequest, parameter);
        item.SubTitle = subTitle;
        item.StartDate = calendar.getTime();
        item.StartTime = FormatUtil.formatTime((Calendar)calendar, (int)time, (int)59);
        item.CreatedAt = FormatUtil.getTimeInMillis((Calendar)calendar, (int)time);
        item.SortDate = item.StartDate;
        item.SortTime = item.StartTime;
        boolean bl = item.IsRead = bmoOWNOT != null && "Y".equals(bmoOWNOT.getRead());
        if ("Y".equals(approvalRequest.getIsIns()) && (this.boName.equals("OINV") || this.boName.equals("OPCH"))) {
            this.boName = this.boName + "_RV";
        }
        if (approvalRequest.getIsDraft().equals("Y") || null != approvalRequest.getDraftEntry()) {
            if (this.isNewPath.booleanValue()) {
                item.NavigationIntent = "boOPDF-app";
                item.NavigationTargetObject = "boOPDF";
                item.NavigationTargetAction = "app&/detail/" + approvalRequest.getDraftEntry();
            } else {
                String viewName = this.boName;
                if (this.boName.equals("OIGE") || this.boName.equals("OIGN") || this.boName.equals("OWTR") || this.boName.equals("OWTQ")) {
                    draftBoName = "ODRF_IV";
                }
                if (this.boName.equals("OINC")) {
                    draftBoName = "OICD";
                    viewName = "OICD";
                }
                this.handleItemNavigation(item, approvalRequest, draftBoName, viewName);
            }
        } else if (null != approvalRequest.getDocEntry()) {
            if (this.isNewPath.booleanValue()) {
                item.NavigationIntent = "bo" + this.boName + "-app";
                item.NavigationTargetObject = "bo" + this.boName;
                item.NavigationTargetAction = "app&/detail/" + approvalRequest.getDocEntry();
            } else {
                item.NavigationIntent = "webclient-" + this.boName;
                item.NavigationTargetObject = "webclient";
                item.NavigationTargetAction = this.boName + "&/Objects/" + this.boName + "/Detail";
                item.NavigationTargetParams = new NavigationTargetParam[]{new NavigationTargetParam("view", this.boName + ".detailView"), new NavigationTargetParam("id", this.boName + "," + approvalRequest.getDocEntry())};
            }
        } else {
            this.handleItemNavigation(item, approvalRequest, draftBoName, this.boName);
        }
        item.Reminder = true;
        item.ReminderTime = 0L;
        item.setPriority(APPROVAL_NOTIFICATION_PRIORITY);
        item.priorityOrderbyNumber = approvalRequest.getDataCols();
        return item;
    }

    private NotificationItem handleItemNavigation(NotificationItem item, ApprovalRequestInfo approvalRequest, String draftBoName, String viewName) {
        if (this.boName.equalsIgnoreCase("OOAT")) {
            draftBoName = "VOWDDOAT";
            item.NavigationIntent = "webclient-" + draftBoName;
            item.NavigationTargetObject = "webclient";
            item.NavigationTargetAction = draftBoName + "&/Objects/" + draftBoName + "/List";
            return item;
        }
        item.NavigationIntent = "webclient-" + draftBoName;
        item.NavigationTargetObject = "webclient";
        item.NavigationTargetAction = draftBoName + "&/Objects/" + draftBoName + "/Detail";
        item.NavigationTargetParams = new NavigationTargetParam[]{new NavigationTargetParam("view", viewName + ".detailView"), new NavigationTargetParam("id", draftBoName + "," + approvalRequest.getDraftEntry())};
        return item;
    }

    private String getTitle(ApprovalRequestInfo approvalRequest, Parameter parameter) {
        Object title;
        try {
            String ObjType = approvalRequest.getObjType();
            title = this.env.getMetaTable(this.getMetaTable(ObjType)).getDescription();
            this.boName = this.env.getMetaTable(this.getMetaTable(ObjType)).getName();
        }
        catch (Exception e) {
            title = "Drafts";
        }
        title = approvalRequest.getUserID() != null ? this.message.getMessage((MessageId)Notifications.waitingforYourApproval, new Object[]{title}) : (String)title + "\u3000\u3000" + approvalRequest.getDraftEntry() + "\u3000\u3000" + FormatUtil.formatTime((int)approvalRequest.getRecTime()) + "\u3000\u3000" + FormatUtil.formatDate((Date)approvalRequest.getRecDate(), (Locale)parameter.locale) + "\u3000\u3000" + approvalRequest.getAuthorizer();
        return title;
    }

    private Class getMetaTable(String ObjType) {
        switch (Integer.valueOf(ObjType)) {
            case 17: {
                return BmoORDR.class;
            }
            case 23: {
                return BmoOQUT.class;
            }
            case 15: {
                return BmoODLN.class;
            }
            case 13: {
                return BmoOINV.class;
            }
            case 16: {
                return BmoORDN.class;
            }
            case 14: {
                return BmoORIN.class;
            }
            case 1470000113: {
                return BmoOPRQ.class;
            }
            case 540000006: {
                return BmoOPQT.class;
            }
            case 22: {
                return BmoOPOR.class;
            }
            case 21: {
                return BmoORPD.class;
            }
            case 19: {
                return BmoORPC.class;
            }
            case 234000031: {
                return BmoORRR.class;
            }
            case 234000032: {
                return BmoOPRR.class;
            }
            case 20: {
                return BmoOPDN.class;
            }
            case 18: {
                return BmoOPCH.class;
            }
            case 46: {
                this.isNewPath = true;
                return BmoOVPM.class;
            }
            case 60: {
                return BmoOIGE.class;
            }
            case 59: {
                return BmoOIGN.class;
            }
            case 1470000065: {
                return BmoOINC.class;
            }
            case 1250000026: 
            case 1250000027: {
                return BmoOOAT.class;
            }
        }
        return BmoODRF.class;
    }

    private String getSubTitleBySubject(ApprovalRequestInfo approvalRequest, Parameter parameter) {
        if (approvalRequest.getUserID() != null) {
            String requestedBy = this.notEmpty(approvalRequest.getOriginator()) ? "\n" + this.message.getMessage((MessageId)Notifications.requestedByPrefix, new Object[]{approvalRequest.getOriginator()}) : "";
            String remark = this.notEmpty(approvalRequest.getRemarks()) ? "\n" + this.message.getMessage((MessageId)Notifications.remarkPrefix, new Object[]{approvalRequest.getRemarks()}) : "";
            String requestedOn = "\n" + this.message.getMessage((MessageId)Notifications.requestedOnPrefix, new Object[]{FormatUtil.formatDate((Date)approvalRequest.getRecDate(), (Locale)parameter.locale)}) + "\u3000\u3000" + FormatUtil.formatTime((int)approvalRequest.getRecTime());
            String template = this.notEmpty(approvalRequest.getWtmName()) ? "\n" + this.message.getMessage((MessageId)Notifications.templatePrefix, new Object[]{approvalRequest.getWtmName()}) : "";
            String stage = this.notEmpty(approvalRequest.getStage()) ? "\u3000\u3000" + this.message.getMessage((MessageId)Notifications.stagePrefix, new Object[]{approvalRequest.getStage()}) : "";
            return requestedBy + remark + requestedOn + template + stage;
        }
        String remark = this.notEmpty(approvalRequest.getRemarks()) ? "\n" + this.message.getMessage((MessageId)Notifications.remarkPrefix, new Object[]{approvalRequest.getRemarks()}) : "";
        return this.message.getMessage((MessageId)Notifications.RTLMessageControl, new Object[]{approvalRequest.getSubject(), remark});
    }

    private boolean notEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}

