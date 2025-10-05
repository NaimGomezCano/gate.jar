/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.base.FormatUtil
 */
package com.sap.b1.notification.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
 * Exception performing whole class analysis ignored.
 */
public class FormatUtil {
    public static LocalDate toLocal(Calendar calendar) {
        return LocalDate.of(calendar.get(1), calendar.get(2) + 1, calendar.get(5));
    }

    public static LocalDate toLocal(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return FormatUtil.toLocal((Calendar)calendar);
    }

    public static String formatDate(Date date, Locale locale) {
        DateFormat format = DateFormat.getDateInstance(1, locale);
        return format.format(date);
    }

    public static String formatTime(int time) {
        int hour = time / 100;
        int minute = time % 100;
        return (hour < 10 ? "0" : "") + hour + ":" + (minute < 10 ? "0" : "") + minute;
    }

    public static long formatTime(Calendar calendar, int time) {
        return FormatUtil.formatTime((Calendar)calendar, (int)time, (int)0);
    }

    public static long formatTime(Calendar calendar, int time, int seconds) {
        calendar = (Calendar)calendar.clone();
        int hour = time / 100;
        int minute = time % 100;
        calendar.set(11, hour);
        calendar.set(12, minute);
        calendar.set(13, seconds);
        calendar.set(14, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return Long.parseLong(sdf.format(calendar.getTime()));
    }

    public static long getTimeInMillis(Calendar calendar, int time) {
        calendar = (Calendar)calendar.clone();
        int hour = time / 100;
        int minute = time % 100;
        calendar.set(11, hour);
        calendar.set(12, minute);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }
}

