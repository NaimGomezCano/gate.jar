/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.bo.base.JpaUtil
 *  com.sap.b1.notification.base.FormatUtil
 *  com.sap.b1.notification.cache.OSCLController
 *  com.sap.b1.notification.cache.Parameter
 *  com.sap.b1.notification.monitors.MonitorBase
 *  com.sap.b1.notification.monitors.NotificationParameter
 *  com.sap.b1.notification.monitors.NotificationTypeEnum
 *  com.sap.b1.notification.monitors.ServiceCallMonitor
 *  com.sap.b1.notification.server.NotificationItem
 *  com.sap.b1.notification.serviceCall.ServiceCallInfo
 *  gen.bean.BmoOHEM
 *  gen.bean.BmoOWNOT
 *  gen.dao.DaoOHEM
 *  gen.dao.DaoOWNOT
 *  jakarta.persistence.EntityManager
 *  jakarta.persistence.PersistenceContext
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Scope
 *  org.springframework.stereotype.Component
 *  org.springframework.util.CollectionUtils
 */
package com.sap.b1.notification.monitors;

import com.sap.b1.bo.base.JpaUtil;
import com.sap.b1.notification.base.FormatUtil;
import com.sap.b1.notification.cache.OSCLController;
import com.sap.b1.notification.cache.Parameter;
import com.sap.b1.notification.monitors.MonitorBase;
import com.sap.b1.notification.monitors.NotificationParameter;
import com.sap.b1.notification.monitors.NotificationTypeEnum;
import com.sap.b1.notification.server.NotificationItem;
import com.sap.b1.notification.serviceCall.ServiceCallInfo;
import gen.bean.BmoOHEM;
import gen.bean.BmoOWNOT;
import gen.dao.DaoOHEM;
import gen.dao.DaoOWNOT;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@Scope(value="prototype")
@Configuration
public class ServiceCallMonitor
extends MonitorBase {
    @Autowired
    private OSCLController osclController;
    private static final Logger logger = LoggerFactory.getLogger(ServiceCallMonitor.class);
    @PersistenceContext
    private EntityManager em;
    private List<BmoOWNOT> dtNOTI;
    private List<ServiceCallInfo> serviceCalls;
    @Autowired
    private DaoOWNOT daoOWNOT;
    @Autowired
    DaoOHEM daoOHEM;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public ServiceCallMonitor(NotificationParameter notificationParameter) {
        super(notificationParameter);
    }

    private Calendar getCalendar(Timestamp date) throws Exception {
        Date calendarDate = new Date(date.getTime());
        String formatter = this.sdf.format(calendarDate);
        return this.toCalendar(formatter);
    }

    private Calendar toCalendar(String parentId) throws Exception {
        Date date = this.sdf.parse(parentId.split("-")[0]);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public boolean refresh(Set<String> unseens) throws Exception {
        boolean changed;
        Calendar remDateCalendar;
        Integer userId = this.notificationParameter.getUserId();
        Boolean isMultiScheduling = this.notificationParameter.getIsMultiScheduling() != null ? this.notificationParameter.getIsMultiScheduling() : false;
        Calendar calendarNow = Calendar.getInstance();
        List serviceCalls = null;
        Integer technicianEmployId = null;
        List technicianEmployees = this.daoOHEM.getEmployeebyUserID(userId);
        if (!CollectionUtils.isEmpty((Collection)technicianEmployees)) {
            technicianEmployId = ((BmoOHEM)technicianEmployees.get(0)).getEmpID();
            if (technicianEmployId != null) {
                List singleAssignCalls;
                serviceCalls = JpaUtil.load((EntityManager)this.em, ServiceCallInfo.class, (String)this.getTechnicianOsclRelatedRecallsSql(), (Object[])new Object[]{technicianEmployId});
                if (!CollectionUtils.isEmpty((Collection)serviceCalls)) {
                    for (ServiceCallInfo serviceCallInfo : serviceCalls) {
                        long nowTime;
                        if (!"Y".equals(serviceCallInfo.getReminder())) continue;
                        remDateCalendar = this.getCalendar(serviceCallInfo.getRemDate());
                        long remTime = FormatUtil.formatTime((Calendar)remDateCalendar, (int)serviceCallInfo.getRemTime());
                        if (remTime > (nowTime = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmm").format(calendarNow.getTime()) + "00").longValue())) {
                            serviceCallInfo.setIsForReminder(Boolean.TRUE);
                            continue;
                        }
                        serviceCallInfo.setIsForReminder(Boolean.FALSE);
                    }
                }
                if (!CollectionUtils.isEmpty((Collection)(singleAssignCalls = JpaUtil.load((EntityManager)this.em, ServiceCallInfo.class, (String)this.getAssigneeOsclRelatedRecallsSql(), (Object[])new Object[]{userId})))) {
                    serviceCalls.addAll(singleAssignCalls);
                }
            }
        } else {
            serviceCalls = JpaUtil.load((EntityManager)this.em, ServiceCallInfo.class, (String)this.getAssigneeOsclRelatedRecallsSql(), (Object[])new Object[]{userId});
        }
        if (isMultiScheduling.booleanValue()) {
            Integer employId = null;
            List employees = this.daoOHEM.getEmployeebyUserID(userId);
            if (!CollectionUtils.isEmpty((Collection)employees)) {
                employId = ((BmoOHEM)employees.get(0)).getEmpID();
            }
            List serviceCallsFromScl6 = employId != null ? JpaUtil.load((EntityManager)this.em, ServiceCallInfo.class, (String)this.getOsclAndScl6RelatedRecallsSql(true), (Object[])new Object[]{userId, employId}) : JpaUtil.load((EntityManager)this.em, ServiceCallInfo.class, (String)this.getOsclAndScl6RelatedRecallsSql(false), (Object[])new Object[]{userId});
            for (ServiceCallInfo serviceCall : serviceCallsFromScl6) {
                long nowTime;
                long remTime;
                if ("Y".equals(serviceCall.getReminder()) && (remTime = FormatUtil.formatTime((Calendar)(remDateCalendar = this.getCalendar(serviceCall.getRemDate())), (int)serviceCall.getRemTime())) <= (nowTime = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmm").format(calendarNow.getTime()) + "00").longValue())) continue;
                serviceCall.setIsForReminder(Boolean.TRUE);
            }
            serviceCalls.addAll(serviceCallsFromScl6);
        }
        List newdtNOTI = this.daoOWNOT.loadByUserAndType(userId, NotificationTypeEnum.SERVICECALL.getValue());
        boolean bl = changed = !this.equals(this.serviceCalls, serviceCalls) || !this.equals(this.dtNOTI, newdtNOTI) || this.notificationParameter.isShowDayChanged();
        if (changed) {
            try {
                List oldItems = new ArrayList();
                Parameter parameter = new Parameter(userId);
                if (this.serviceCalls != null && this.dtNOTI != null) {
                    parameter.showDays = this.notificationParameter.getOldShowDays();
                    oldItems = this.osclController.getNotifications(this.refreshServiceCall(this.serviceCalls), this.dtNOTI, parameter, isMultiScheduling);
                }
                parameter.showDays = this.notificationParameter.getShowDays();
                List newItems = this.osclController.getNotifications(this.refreshServiceCall(serviceCalls), newdtNOTI, parameter, isMultiScheduling);
                this.calculateUnseen(unseens, oldItems, newItems);
                this.serviceCalls = serviceCalls;
                this.dtNOTI = newdtNOTI;
            }
            catch (Exception e) {
                logger.error(e.getMessage());
                changed = false;
            }
        }
        return changed;
    }

    private List<ServiceCallInfo> refreshServiceCall(List<ServiceCallInfo> serviceCalls) {
        return serviceCalls.stream().filter(info -> new Boolean(false).equals(info.getIsForReminder())).collect(Collectors.toList());
    }

    public List<NotificationItem> getNotifications(Parameter parameter) throws Exception {
        parameter.showDays = this.notificationParameter.getShowDays();
        List<Object> notis = new ArrayList<NotificationItem>();
        Boolean isForMultiScheduling = this.notificationParameter.getIsMultiScheduling() != null ? this.notificationParameter.getIsMultiScheduling() : false;
        try {
            notis = this.osclController.getNotifications(this.serviceCalls, this.dtNOTI, parameter, isForMultiScheduling);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return notis;
    }

    private String getAssigneeOsclRelatedRecallsSql() {
        return "select \n\"OSCL\".\"callID\",\"OSCL\".\"subject\",\"OSCL\".\"DocNum\",\"OSCL\".\"custmrName\",\"OSCL\".\"status\",\"OSCL\".\"priority\",\"OSCL\".\"AssignDate\",\"OSCL\".\"AssignTime\",\"OSCL\".\"StartDate\",\"OSCL\".\"StartTime\",\"OSCL\".\"EndDate\",\"OSCL\".\"EndTime\",\"OSCL\".\"Duration\",\"OSCL\".\"Reminder\",\"OSCL\".\"RemQty\",\"OSCL\".\"RemDate\",\"OSCL\".\"RemTime\",\"OSCL\".\"userSign\",'true' as isAssign\nfrom \"OSCL\"\n where \"OSCL\".\"assignee\" = ? ";
    }

    private String getTechnicianOsclRelatedRecallsSql() {
        return "select \n\"OSCL\".\"callID\",\"OSCL\".\"subject\",\"OSCL\".\"DocNum\",\"OSCL\".\"custmrName\",\"OSCL\".\"status\",\"OSCL\".\"priority\",\"OSCL\".\"AssignDate\",\"OSCL\".\"AssignTime\",\"OSCL\".\"StartDate\",\"OSCL\".\"StartTime\",\"OSCL\".\"EndDate\",\"OSCL\".\"EndTime\",\"OSCL\".\"Duration\",\"OSCL\".\"Reminder\",\"OSCL\".\"RemQty\",\"OSCL\".\"RemDate\",\"OSCL\".\"RemTime\",\"OSCL\".\"userSign\",'false' as isAssign\nfrom \"OSCL\"\n where \"OSCL\".\"technician\" = ? ";
    }

    private String getOsclAndScl6RelatedRecallsSql(boolean withEmployId) {
        if (!withEmployId) {
            return "select \n\"OSCL\".\"callID\",\"OSCL\".\"subject\",\"OSCL\".\"DocNum\",\"OSCL\".\"custmrName\",\"OSCL\".\"status\",\"OSCL\".\"priority\",\"OSCL\".\"AssignDate\",\"OSCL\".\"AssignTime\",\n\"SCL6\".\"StartDate\",\"SCL6\".\"StartTime\",\"SCL6\".\"EndDate\",\"SCL6\".\"EndTime\",\"SCL6\".\"Duration\",\"SCL6\".\"Reminder\",\"SCL6\".\"RemQty\",\"SCL6\".\"RemDate\",\"SCL6\".\"RemTime\",\"OSCL\".\"userSign\",'false' as isAssign\n from \"OSCL\"\nJOIN \"SCL6\" ON \"SCL6\".\"SrcvCallID\" = \"OSCL\".\"callID\" and (\"SCL6\".\"HandledBy\" = ?)";
        }
        return "select \n\"OSCL\".\"callID\",\"OSCL\".\"subject\",\"OSCL\".\"DocNum\",\"OSCL\".\"custmrName\",\"OSCL\".\"status\",\"OSCL\".\"priority\",\"OSCL\".\"AssignDate\",\"OSCL\".\"AssignTime\",\n\"SCL6\".\"StartDate\",\"SCL6\".\"StartTime\",\"SCL6\".\"EndDate\",\"SCL6\".\"EndTime\",\"SCL6\".\"Duration\",\"SCL6\".\"Reminder\",\"SCL6\".\"RemQty\",\"SCL6\".\"RemDate\",\"SCL6\".\"RemTime\",\"OSCL\".\"userSign\",'false' as isAssign\n from \"OSCL\"\nJOIN \"SCL6\" ON \"SCL6\".\"SrcvCallID\" = \"OSCL\".\"callID\" and (\"SCL6\".\"HandledBy\" = ? or \"SCL6\".\"Technician\" = ?)";
    }
}

