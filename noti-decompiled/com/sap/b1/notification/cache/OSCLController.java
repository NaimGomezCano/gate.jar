/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.base.FormatUtil
 *  com.sap.b1.notification.base.NotiEnv
 *  com.sap.b1.notification.cache.BaseController
 *  com.sap.b1.notification.cache.OSCLController
 *  com.sap.b1.notification.cache.Parameter
 *  com.sap.b1.notification.monitors.NotificationTypeEnum
 *  com.sap.b1.notification.server.NotificationItem
 *  com.sap.b1.notification.service.OSCLService
 *  com.sap.b1.notification.serviceCall.ServiceCallInfo
 *  com.sap.b1.tcli.resources.MessageId
 *  gen.bean.BmoOSCL
 *  gen.bean.BmoOSCLHist
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

import com.sap.b1.notification.base.FormatUtil;
import com.sap.b1.notification.base.NotiEnv;
import com.sap.b1.notification.cache.BaseController;
import com.sap.b1.notification.cache.Parameter;
import com.sap.b1.notification.monitors.NotificationTypeEnum;
import com.sap.b1.notification.server.NotificationItem;
import com.sap.b1.notification.service.OSCLService;
import com.sap.b1.notification.serviceCall.ServiceCallInfo;
import com.sap.b1.tcli.resources.MessageId;
import gen.bean.BmoOSCL;
import gen.bean.BmoOSCLHist;
import gen.bean.BmoOWNOT;
import gen.str.Notifications;
import java.sql.Timestamp;
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
public class OSCLController
extends BaseController {
    @Autowired
    @Qualifier(value="PollingEnv")
    private NotiEnv env;
    @Autowired
    @Qualifier(value="OSCLService")
    private OSCLService osclService;
    private static final Logger logger = LoggerFactory.getLogger(OSCLController.class);

    public List<NotificationItem> getNotifications(List<ServiceCallInfo> serviceCalls, List<BmoOWNOT> dtOWNOT, Parameter parameter, Boolean isMultiScheduling) throws Exception {
        int size = serviceCalls.size();
        ArrayList<NotificationItem> items = new ArrayList<NotificationItem>(size * (parameter.showDays + 1));
        Map map = this.mapNotification(dtOWNOT);
        Calendar calendar = this.getTomorrowCalendar();
        ArrayList<NotificationItem> groupChildren = new ArrayList<NotificationItem>();
        boolean isRead = false;
        String priorityNumber = "0";
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
                isRead = false;
                priorityNumber = "0";
            }
            for (ServiceCallInfo serviceCall : serviceCalls) {
                String id = this.toId(parentId, NotificationTypeEnum.SERVICECALL.getValue().toString(), serviceCall.getCallID().toString());
                BmoOWNOT bmoOWNOT = (BmoOWNOT)map.get(id);
                if (bmoOWNOT != null && "Y".equals(bmoOWNOT.getDismissed()) || serviceCall.getStatus() == -1 || !this.occur(serviceCall, calendar)) continue;
                try {
                    if (isMultiScheduling.booleanValue()) {
                        BmoOSCLHist reassignHist;
                        if (parameter.userId.equals(serviceCall.getUserSign()) && !serviceCall.getIsForReminder().booleanValue()) {
                            serviceCall.setValid(Boolean.valueOf(false));
                        }
                        if ((reassignHist = this.getReassignHist(serviceCall, parameter)) != null) {
                            serviceCall.setAssignDate(reassignHist.getUPDATED_DATE());
                            serviceCall.setAssignTime(reassignHist.getUPDATED_TIME());
                        }
                    } else if (parameter.userId.equals(serviceCall.getUserSign())) {
                        serviceCall.setValid(Boolean.valueOf(false));
                        this.getReassignHist(serviceCall, parameter);
                    }
                    if (!serviceCall.getValid().booleanValue()) continue;
                    NotificationItem item = this.getNotificationItem(parentId, id, parameter, serviceCall, calendar, bmoOWNOT);
                    if (parameter.isGroupHeader) {
                        if (priorityNumber.compareTo(serviceCall.getPriority()) < 0) {
                            priorityNumber = serviceCall.getPriority();
                        }
                        groupChildren.add(item);
                        continue;
                    }
                    items.add(item);
                }
                catch (Exception e) {
                    logger.error("Error in getting notification item.");
                    logger.error(e.getMessage());
                }
            }
            if (parameter.parentId != null) break;
            if (!parameter.isGroupHeader || groupChildren.size() == 0) continue;
            NotificationItem groupItem = new NotificationItem();
            groupItem.Id = parentId;
            groupItem.IsGroupHeader = true;
            groupItem.StartDate = calendar.getTime();
            groupItem.SortDate = calendar.getTime();
            groupItem.IsRead = isRead;
            groupItem.setPriority(priorityNumber);
            groupItem.aNotifications = new NotificationItem[groupChildren.size()];
            groupChildren.toArray(groupItem.aNotifications);
            items.add(groupItem);
        }
        return items;
    }

    private NotificationItem getNotificationItem(String parentId, String id, Parameter parameter, ServiceCallInfo serviceCall, Calendar calendar, BmoOWNOT bmoOWNOT) throws Exception {
        NotificationItem item = new NotificationItem();
        item.ParentId = parentId;
        item.Id = id;
        item.Text = this.generateTitle(serviceCall, parameter, calendar.getTime());
        item.IsForReminder = serviceCall.getIsForReminder();
        item.SubTitle = serviceCall.getSubject();
        Calendar startDateCalendar = serviceCall.getStartDate() != null ? this.getCalendar(serviceCall.getStartDate()) : calendar;
        item.StartDate = startDateCalendar.getTime();
        if (serviceCall.getStartTime() != null) {
            item.StartTime = FormatUtil.formatTime((Calendar)startDateCalendar, (int)serviceCall.getStartTime());
            item.CreatedAt = FormatUtil.getTimeInMillis((Calendar)calendar, (int)serviceCall.getStartTime());
        } else {
            item.CreatedAt = FormatUtil.getTimeInMillis((Calendar)calendar, (int)serviceCall.getAssignTime());
        }
        item.SortDate = calendar.getTime();
        item.SortTime = FormatUtil.formatTime((Calendar)calendar, (int)serviceCall.getAssignTime());
        item.IsRead = bmoOWNOT != null && "Y".equals(bmoOWNOT.getRead());
        item.setPriority(this.ChangePriorityToNumString(serviceCall.getPriority()));
        item.NavigationIntent = "boOSCL-app";
        item.NavigationTargetObject = "boOSCL";
        item.NavigationTargetAction = "app&/detail/" + serviceCall.getCallID();
        item.Reminder = "Y".equals(serviceCall.getReminder());
        Calendar reminderDateCalendar = serviceCall.getRemDate() != null ? this.getCalendar(serviceCall.getRemDate()) : calendar;
        if (item.Reminder) {
            item.ReminderTime = FormatUtil.formatTime((Calendar)reminderDateCalendar, (int)serviceCall.getRemTime());
            item.ReminderText = this.reminderText(serviceCall, parameter, reminderDateCalendar.getTime());
        }
        if (!new Boolean(serviceCall.getIsAssign()).booleanValue()) {
            item.Text = this.reminderText(serviceCall, parameter, reminderDateCalendar.getTime());
        }
        item.setPriorityOrderbyNumber(Integer.valueOf(4));
        return item;
    }

    private Calendar getCalendar(Timestamp date) throws Exception {
        Date calendarDate = new Date(date.getTime());
        String formatter = this.sdf.format(calendarDate);
        return this.toCalendar(formatter);
    }

    private boolean occur(ServiceCallInfo serviceCall, Calendar calendar) throws Exception {
        LocalDate startDate = serviceCall.getIsForReminder() != null && serviceCall.getIsForReminder() != false ? FormatUtil.toLocal((Date)serviceCall.getStartDate()) : FormatUtil.toLocal((Date)serviceCall.getAssignDate());
        LocalDate testDate = FormatUtil.toLocal((Calendar)calendar);
        int compare = testDate.compareTo(startDate);
        if (compare < 0) {
            return false;
        }
        return compare == 0;
    }

    private String generateTitle(ServiceCallInfo serviceCall, Parameter parameter, Date activityDate) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.message.getMessage((MessageId)Notifications.serviceCallhasbeenAssignedtoYou, new Object[]{serviceCall.getDocNum()}));
        buffer.append("\u3000\u3000");
        buffer.append(FormatUtil.formatTime((int)serviceCall.getAssignTime()));
        if (parameter.parentId == null) {
            buffer.append("\u3000\u3000");
            buffer.append(FormatUtil.formatDate((Date)serviceCall.getAssignDate(), (Locale)parameter.locale));
        }
        return buffer.toString();
    }

    private String reminderText(ServiceCallInfo serviceCall, Parameter parameter, Date activityDate) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.message.getMessage((MessageId)Notifications.serviceCall, new Object[0]));
        String cardName = serviceCall.getCustmrName();
        if (cardName != null) {
            buffer.append("\u3000\u3000");
            buffer.append(serviceCall.getCustmrName());
        }
        buffer.append("\u3000\u3000");
        if (serviceCall.getStartTime() != null) {
            buffer.append(FormatUtil.formatTime((int)serviceCall.getStartTime()));
        }
        if (parameter.parentId == null) {
            buffer.append("\u3000\u3000");
            buffer.append(FormatUtil.formatDate((Date)activityDate, (Locale)parameter.locale));
        }
        return buffer.toString();
    }

    private String ChangePriorityToNumString(String Priority) {
        switch (Priority) {
            case "L": {
                return "0";
            }
            case "M": {
                return "1";
            }
            case "H": {
                return "2";
            }
        }
        return "0";
    }

    private BmoOSCLHist getReassignHist(ServiceCallInfo serviceCall, Parameter parameter) throws Exception {
        BmoOSCLHist userReassignHist;
        block4: {
            userReassignHist = null;
            try {
                BmoOSCL drOSCL = this.osclService.findById(serviceCall.getCallID());
                if (drOSCL == null) break block4;
                List hists = drOSCL.getOSCLHist();
                for (int i = hists.size() - 1; i >= 0; --i) {
                    if (!((BmoOSCLHist)hists.get(i)).getCHANGED_FIELD().equals("Reassigned") || !((BmoOSCLHist)hists.get(i)).getNEW_VALUE().equals(this.env.getUserFullName())) continue;
                    userReassignHist = (BmoOSCLHist)hists.get(i);
                    if (!((BmoOSCLHist)hists.get(i)).getCHANGED_BY().equals(parameter.userId)) {
                        serviceCall.setValid(Boolean.valueOf(true));
                    }
                    break;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return userReassignHist;
    }
}

