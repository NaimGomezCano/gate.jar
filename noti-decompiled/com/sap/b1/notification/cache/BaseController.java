/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.cache.BaseController
 *  com.sap.b1.tcli.resources.MessageUtil
 *  gen.bean.BmoOWNOT
 *  org.springframework.beans.factory.annotation.Autowired
 */
package com.sap.b1.notification.cache;

import com.sap.b1.tcli.resources.MessageUtil;
import gen.bean.BmoOWNOT;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    MessageUtil message;
    protected final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    BaseController() {
    }

    protected Calendar getTomorrowCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.set(5, calendar.get(5) + 1);
        return calendar;
    }

    protected String toParentId(Calendar calendar) {
        return this.sdf.format(calendar.getTime());
    }

    protected String toId(String parentId, String TypeId, String uuid) {
        return parentId + "-" + TypeId + "-" + uuid;
    }

    protected Calendar toCalendar(String parentId) throws Exception {
        Date date = this.sdf.parse(parentId.split("-")[0]);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    protected Map<String, BmoOWNOT> mapNotification(List<BmoOWNOT> dtOWNOT) {
        HashMap<String, BmoOWNOT> map = new HashMap<String, BmoOWNOT>();
        for (BmoOWNOT bmoOWNOT : dtOWNOT) {
            map.put(bmoOWNOT.getGuid(), bmoOWNOT);
        }
        return map;
    }
}

