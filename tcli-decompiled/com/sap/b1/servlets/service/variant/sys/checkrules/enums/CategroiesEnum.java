/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.servlets.service.variant.sys.checkrules.enums.CategroiesEnum
 *  com.sap.b1.svcl.client.entitytype.WebClientVariant
 */
package com.sap.b1.servlets.service.variant.sys.checkrules.enums;

import com.sap.b1.svcl.client.entitytype.WebClientVariant;

public enum CategroiesEnum {
    GENERAL_OVERVIEW,
    OVERVIEW,
    CHART,
    CHART_VIEW,
    CARD_VIEW,
    NA;


    public static CategroiesEnum getVariantCategroies(WebClientVariant variant) {
        String GENERAL_OVERVIEW_ID = "MDO10001";
        if (GENERAL_OVERVIEW_ID.equalsIgnoreCase(variant.ViewId)) {
            return GENERAL_OVERVIEW;
        }
        if ("Dashboard".equalsIgnoreCase(variant.ViewType)) {
            return OVERVIEW;
        }
        if ("ChartContainer".equalsIgnoreCase(variant.ViewType)) {
            return CHART;
        }
        if ("List".equalsIgnoreCase(variant.ViewType) && "Chart".equalsIgnoreCase(variant.SubViewType)) {
            return CHART_VIEW;
        }
        if ("List".equalsIgnoreCase(variant.ViewType) && "Card".equalsIgnoreCase(variant.SubViewType)) {
            return CARD_VIEW;
        }
        return NA;
    }
}

