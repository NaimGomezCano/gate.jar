/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.cache.Parameter
 *  org.springframework.context.i18n.LocaleContextHolder
 */
package com.sap.b1.notification.cache;

import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;

public class Parameter {
    public Integer userId;
    public String orderby;
    public boolean desending;
    public boolean isGroupHeader;
    public String parentId;
    public Integer skip;
    public Integer top;
    public int showDays;
    public Locale locale;

    public Parameter(Integer userId) {
        this.userId = userId;
        this.locale = LocaleContextHolder.getLocale();
    }
}

