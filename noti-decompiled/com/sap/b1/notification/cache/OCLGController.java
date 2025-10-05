/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.infra.engines.metadata.MetaColumns
 *  com.sap.b1.infra.engines.metadata.MetaValidValue
 *  com.sap.b1.infra.engines.metadata.MetaValidValues
 *  com.sap.b1.notification.base.FormatUtil
 *  com.sap.b1.notification.base.NotiEnv
 *  com.sap.b1.notification.cache.BaseController
 *  com.sap.b1.notification.cache.OCLGController
 *  com.sap.b1.notification.cache.OCLGController$1
 *  com.sap.b1.notification.cache.Parameter
 *  com.sap.b1.notification.monitors.NotificationTypeEnum
 *  com.sap.b1.notification.server.NavigationTargetParam
 *  com.sap.b1.notification.server.NotificationItem
 *  gen.bean.BmoOCLG
 *  gen.bean.BmoOWNOT
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Qualifier
 *  org.springframework.context.annotation.Scope
 *  org.springframework.stereotype.Component
 */
package com.sap.b1.notification.cache;

import com.sap.b1.infra.engines.metadata.MetaColumns;
import com.sap.b1.infra.engines.metadata.MetaValidValue;
import com.sap.b1.infra.engines.metadata.MetaValidValues;
import com.sap.b1.notification.base.FormatUtil;
import com.sap.b1.notification.base.NotiEnv;
import com.sap.b1.notification.cache.BaseController;
import com.sap.b1.notification.cache.OCLGController;
import com.sap.b1.notification.cache.Parameter;
import com.sap.b1.notification.monitors.NotificationTypeEnum;
import com.sap.b1.notification.server.NavigationTargetParam;
import com.sap.b1.notification.server.NotificationItem;
import gen.bean.BmoOCLG;
import gen.bean.BmoOWNOT;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
public class OCLGController
extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OCLGController.class);
    private static final int DAYS = 1;
    private static final int WEEKS = 2;
    private static final int MONTHS = 3;
    private static final int YEARS = 4;
    @Autowired
    @Qualifier(value="PollingEnv")
    private NotiEnv env;

    public List<NotificationItem> getNotifications(List<BmoOCLG> dtOCLG, List<BmoOWNOT> dtOWNOT, Parameter parameter) throws Exception {
        int size = dtOCLG.size();
        ArrayList<NotificationItem> items = new ArrayList<NotificationItem>(size * (parameter.showDays + 1));
        Map map = this.mapNotification(dtOWNOT);
        Calendar calendar = this.getTomorrowCalendar();
        ArrayList<NotificationItem> groupChildren = new ArrayList<NotificationItem>();
        boolean isRead = false;
        String priorityNumber = "0";
        Map actionValueMap = this.loadActionValueMap();
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
            Iterator<BmoOCLG> iterator = dtOCLG.iterator();
            while (iterator.hasNext()) {
                BmoOCLG oclg;
                BmoOCLG bmoOCLG = oclg = iterator.next();
                String id = this.toId(parentId, NotificationTypeEnum.ACTIVITY.getValue().toString(), bmoOCLG.getClgCode().toString());
                BmoOWNOT bmoOWNOT = (BmoOWNOT)map.get(id);
                if (bmoOWNOT != null && "Y".equals(bmoOWNOT.getDismissed()) || "Y".equals(bmoOCLG.getIsRemoved()) || "Y".equals(bmoOCLG.getInactive()) || "Y".equals(bmoOCLG.getClosed()) || !this.occur(bmoOCLG, calendar)) continue;
                try {
                    NotificationItem item = this.getNotificationItem(parentId, id, parameter, bmoOCLG, calendar, actionValueMap, bmoOWNOT);
                    if (parameter.isGroupHeader) {
                        if (priorityNumber.compareTo(bmoOCLG.getPriority()) < 0) {
                            priorityNumber = bmoOCLG.getPriority();
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

    private NotificationItem getNotificationItem(String parentId, String id, Parameter parameter, BmoOCLG bmoOCLG, Calendar calendar, Map<String, String> actionValueMap, BmoOWNOT bmoOWNOT) {
        NotificationItem item = new NotificationItem();
        item.ParentId = parentId;
        item.Id = id;
        item.Text = this.generateTitle(bmoOCLG, parameter, calendar.getTime(), actionValueMap);
        item.CreatedAt = FormatUtil.getTimeInMillis((Calendar)calendar, (int)bmoOCLG.getBeginTime());
        item.SubTitle = bmoOCLG.getDetails();
        item.StartDate = calendar.getTime();
        item.StartTime = FormatUtil.formatTime((Calendar)calendar, (int)bmoOCLG.getBeginTime());
        item.SortDate = item.StartDate;
        item.SortTime = item.StartTime;
        item.IsRead = bmoOWNOT != null && "Y".equals(bmoOWNOT.getRead());
        item.setPriority(bmoOCLG.getPriority());
        item.NavigationIntent = "webclient-OCLG";
        item.NavigationTargetObject = "webclient";
        item.NavigationTargetAction = "OCLG&/Objects/OCLG/Detail";
        item.NavigationTargetParams = new NavigationTargetParam[]{new NavigationTargetParam("id", "OCLG," + bmoOCLG.getClgCode())};
        item.Reminder = "Y".equals(bmoOCLG.getReminder());
        if (item.Reminder) {
            item.ReminderTime = FormatUtil.formatTime((Calendar)calendar, (int)bmoOCLG.getRemTime());
        }
        item.setPriorityOrderbyNumber(Integer.valueOf(4));
        return item;
    }

    private Map<String, String> loadActionValueMap() {
        HashMap<String, String> actionValueMap = new HashMap<String, String>();
        try {
            MetaColumns metaColumns = this.env.getMetaTable(BmoOCLG.class).getColumns();
            if (null != metaColumns) {
                MetaValidValues values = metaColumns.getColumn("Action").getValidValues();
                for (MetaValidValue value : values) {
                    actionValueMap.put(value.getValue().toString(), value.getDescription());
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return actionValueMap;
    }

    private boolean occur(BmoOCLG bmoOCLG, Calendar calendar) throws Exception {
        LocalDate startDate = FormatUtil.toLocal((Date)bmoOCLG.getRecontact());
        LocalDate testDate = FormatUtil.toLocal((Calendar)calendar);
        int compare = testDate.compareTo(startDate);
        if (compare < 0) {
            return false;
        }
        LocalDate endDate = FormatUtil.toLocal((Date)bmoOCLG.getSeEndDat());
        if (endDate != null && testDate.isAfter(endDate)) {
            return false;
        }
        String recurPat = bmoOCLG.getRecurPat();
        String subOption = bmoOCLG.getSubOption();
        int interval = bmoOCLG.getInterval();
        Integer month = bmoOCLG.getMonth();
        Integer dayOfMonth = bmoOCLG.getDayInMonth();
        Integer weekOfMonth = bmoOCLG.getWeek();
        Integer dayOfWeek = bmoOCLG.getDayOfWeek();
        switch (recurPat) {
            case "N": {
                return compare == 0;
            }
            case "D": {
                return this.occurDaily(subOption, startDate, testDate, interval);
            }
            case "W": {
                return this.occurWeekly(startDate, testDate, interval, bmoOCLG);
            }
            case "M": {
                return this.occurMonthly(subOption, startDate, testDate, interval, dayOfMonth, weekOfMonth, dayOfWeek);
            }
            case "A": {
                return this.occurAnnually(subOption, startDate, testDate, interval, month, dayOfMonth, weekOfMonth, dayOfWeek);
            }
        }
        throw new Exception("Unrecognizable activity recurrence type: " + recurPat);
    }

    private boolean occurDaily(String subOption, LocalDate startDate, LocalDate testDate, int interval) throws Exception {
        switch (subOption) {
            case "1": {
                return this.between(testDate, startDate, 1) % (long)interval == 0L;
            }
            case "2": {
                return !this.isWeekend(testDate.getDayOfWeek());
            }
        }
        throw new Exception("Unrecognizable activity recurrence subOption: " + subOption);
    }

    private boolean occurWeekly(LocalDate startDate, LocalDate testDate, int interval, BmoOCLG bmoOCLG) {
        long between = this.between(testDate, startDate, 2);
        if (between % (long)interval != 0L) {
            return false;
        }
        switch (1.$SwitchMap$java$time$DayOfWeek[testDate.getDayOfWeek().ordinal()]) {
            case 1: {
                return "Y".equals(bmoOCLG.getMonday());
            }
            case 2: {
                return "Y".equals(bmoOCLG.getTuesday());
            }
            case 3: {
                return "Y".equals(bmoOCLG.getWednesday());
            }
            case 4: {
                return "Y".equals(bmoOCLG.getThursday());
            }
            case 5: {
                return "Y".equals(bmoOCLG.getFriday());
            }
            case 6: {
                return "Y".equals(bmoOCLG.getSaturday());
            }
            case 7: {
                return "Y".equals(bmoOCLG.getSunday());
            }
        }
        return false;
    }

    private boolean occurMonthly(String subOption, LocalDate startDate, LocalDate testDate, int interval, Integer dayOfMonth, Integer weekOfMonth, Integer dayOfWeek) throws Exception {
        long between = this.between(testDate, startDate, 3);
        if (between % (long)interval != 0L) {
            return false;
        }
        switch (subOption) {
            case "1": {
                return dayOfMonth == null || testDate.getDayOfMonth() == dayOfMonth.intValue();
            }
            case "2": {
                return weekOfMonth != null && testDate.get(ChronoField.ALIGNED_WEEK_OF_MONTH) == weekOfMonth.intValue() && this.testDayOfWeek(testDate.getDayOfWeek(), dayOfWeek);
            }
        }
        throw new Exception("Unrecognizable activity recurrence subOption: " + subOption);
    }

    private boolean occurAnnually(String subOption, LocalDate startDate, LocalDate testDate, int interval, Integer month, Integer dayOfMonth, Integer weekOfMonth, Integer dayOfWeek) throws Exception {
        long between = this.between(testDate, startDate, 4);
        if (between % (long)interval != 0L) {
            return false;
        }
        if (month == null || testDate.getMonth().getValue() != month.intValue()) {
            return false;
        }
        switch (subOption) {
            case "1": {
                return dayOfMonth == null || testDate.getDayOfMonth() == dayOfMonth.intValue();
            }
            case "2": {
                return weekOfMonth != null && testDate.get(ChronoField.ALIGNED_WEEK_OF_MONTH) == weekOfMonth.intValue() && this.testDayOfWeek(testDate.getDayOfWeek(), dayOfWeek);
            }
        }
        return false;
    }

    private boolean testDayOfWeek(DayOfWeek value, Integer dayOfWeek) throws Exception {
        if (dayOfWeek == null) {
            throw new Exception("Invalid activity recurrence day of week: " + dayOfWeek);
        }
        switch (dayOfWeek) {
            case 0: {
                return this.isWeekend(value);
            }
            case 1: {
                return DayOfWeek.SUNDAY.equals(value);
            }
            case 2: {
                return DayOfWeek.MONDAY.equals(value);
            }
            case 3: {
                return DayOfWeek.TUESDAY.equals(value);
            }
            case 4: {
                return DayOfWeek.WEDNESDAY.equals(value);
            }
            case 5: {
                return DayOfWeek.THURSDAY.equals(value);
            }
            case 6: {
                return DayOfWeek.FRIDAY.equals(value);
            }
            case 7: {
                return DayOfWeek.SATURDAY.equals(value);
            }
            case 8: {
                return true;
            }
            case 9: {
                return !this.isWeekend(value);
            }
        }
        throw new Exception("Invalid activity recurrence day of week: " + dayOfWeek);
    }

    private long between(LocalDate date1, LocalDate date2, int field) {
        switch (field) {
            default: {
                return ChronoUnit.DAYS.between(date1, date2);
            }
            case 2: {
                return ChronoUnit.WEEKS.between(date1, date2);
            }
            case 3: {
                return ChronoUnit.MONTHS.between(date1, date2);
            }
            case 4: 
        }
        return ChronoUnit.YEARS.between(date1, date2);
    }

    private boolean isWeekend(DayOfWeek dayOfWeek) {
        return DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek);
    }

    private String generateTitle(BmoOCLG bmoOCLG, Parameter parameter, Date activityDate, Map<String, String> actionValueMap) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(actionValueMap.get(bmoOCLG.getAction()));
        String cardName = bmoOCLG.getVCardName();
        if (cardName != null) {
            buffer.append("\u3000\u3000");
            buffer.append(bmoOCLG.getVCardName());
        }
        buffer.append("\u3000\u3000");
        buffer.append(FormatUtil.formatTime((int)bmoOCLG.getBeginTime()));
        if (parameter.parentId == null) {
            buffer.append("\u3000\u3000");
            buffer.append(FormatUtil.formatDate((Date)activityDate, (Locale)parameter.locale));
        }
        return buffer.toString();
    }
}

