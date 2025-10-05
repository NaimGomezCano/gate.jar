/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.placeholder.types.IPlaceholderType
 *  com.sap.sme.anw.analytics.thin.placeholder.ThinFilterPlaceholderType
 */
package com.sap.sme.anw.analytics.thin.placeholder;

import com.sap.sme.anw.analytics.core.placeholder.types.IPlaceholderType;

public enum ThinFilterPlaceholderType implements IPlaceholderType
{
    DATE_FORMULA;


    public boolean isTodayRelated() {
        return true;
    }
}

