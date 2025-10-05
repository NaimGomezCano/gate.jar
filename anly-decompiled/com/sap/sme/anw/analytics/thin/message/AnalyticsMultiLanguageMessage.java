/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.message.impl.CoreAnalyticsMultiLanguageMessage
 *  com.sap.sme.anw.analytics.thin.message.AnalyticsMultiLanguageMessage
 */
package com.sap.sme.anw.analytics.thin.message;

import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.message.impl.CoreAnalyticsMultiLanguageMessage;

public class AnalyticsMultiLanguageMessage
extends CoreAnalyticsMultiLanguageMessage {
    public String getText(IContextData contextData, String languageKey, Object ... args) {
        return super.getText(contextData, languageKey, args);
    }
}

